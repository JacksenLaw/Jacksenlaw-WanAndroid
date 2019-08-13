package com.base.lib.manager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * 作者： LuoM
 * 时间： 2019/3/29 0029
 * 描述： 权限管理manager
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class PermissionManager {

    public String[] permission = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA};

    public String[] permissionTip = new String[]{
            "允许程序使用存储权限",
            "允许程序使用读取手机信息权限",
            "允许程序使用相机权限",};

    @SuppressLint("StaticFieldLeak")
    private static PermissionManager ourInstance;
    private Activity mActivity;
    private String[] permissions;
    private String[] permissionTips;
    private int mRequestCode = -1;
    private RequestPermissionCallBack mRequestPermissionCallBack;

    public static PermissionManager getInstance() {
        if (ourInstance == null) {
            synchronized (PermissionManager.class) {
                ourInstance = new PermissionManager();
            }
        }
        return ourInstance;
    }

    private PermissionManager() {

    }

    public PermissionManager get(Activity activity) {
        this.mActivity = activity;
        return this;
    }

    public void cancel() {
        if (null != ourInstance) {
            ourInstance = null;
        }
    }

    /**
     * 判断是不是M及以上版本
     */

    private boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public PermissionManager requestPermissions(String[] permissions, String[] permissionTips) {
        this.permissions = permissions;
        this.permissionTips = permissionTips;
        return this;
    }

    public PermissionManager requestCodes(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public void request(RequestPermissionCallBack callback) {
        if (mRequestCode == -1) {
            try {
                throw new NullPointerException("requestCode is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mRequestPermissionCallBack = callback;
        if (isM()) {
            StringBuilder permissionNames = new StringBuilder();
            for (int i = 0; i < permissionTips.length; i++) {
                permissionNames = permissionNames.append(i + 1).append("、").append(permissionTips[i]).append("\n\n");
            }

            //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
            boolean isAllGranted = true;

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED) {
                    isAllGranted = false;

                    if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                        ActivityCompat.requestPermissions((mActivity), permissions, mRequestCode);
//                        new AlertDialog.Builder(mActivity)
//                                .setMessage("系统需要以下权限：\n\n" +
//                                        permissionNames +
//                                        "\n请授权,否则将影响系统部分功能的正常使用。")
//                                .setPositiveButton("确定", (dialog, which) -> {
//                                    dialog.dismiss();
//                                    ActivityCompat.requestPermissions((mActivity), permissions, mRequestCode);
//                                }).show();
                    } else {
                        ActivityCompat.requestPermissions((mActivity), permissions, mRequestCode);
//                        showPermissionDialog(mActivity, permissionNames.toString(), (dialog, which) ->
//                                ActivityCompat.requestPermissions((mActivity), permissions, mRequestCode));
                    }
                    break;
                }
            }
            if (isAllGranted) {
                callback.granted();
            }

        } else {
            callback.noM();
        }
    }

    public void onRequestPermissionsResult(final Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        //所有权限是否全部授权
        boolean allPermissionGranted = true;
        //是否点击"不再提示"
        boolean isShouldShow = false;
        //是否只是禁止权限
        boolean isOnlyShow = false;
        StringBuilder permissionName = new StringBuilder();
        if (requestCode == mRequestCode && grantResults != null && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; ++i) {
                if (!isPermissionGranted(activity, permissions[i])) {
                    allPermissionGranted = false;
                    //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                    // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                    if (!isShouldShowRequestPermissionRationale(activity, permissions[i])) {
                        isShouldShow = true;
                        permissionName = permissionName.append("\t\t").append(permissionTips[i]).append("\n\n");
                    } else {
                        //用户拒绝权限请求，但未选中“不再提示”选项
                        isOnlyShow = true;
                        permissionName = permissionName.append("\t\t").append(permissionTips[i]).append("\n\n");
                    }
                }
            }
            if (isShouldShow) {
                showShouldShowRequestDialog(activity, permissionName);
            }
            if (isOnlyShow) {
                showDeniedDialog(activity, permissionName);
            }
            if (allPermissionGranted) {
                mRequestPermissionCallBack.granted();
            }
        }
    }

    /**
     * 显示授权失败提示框
     *
     * @param activity       活动
     * @param permissionName 权限
     */
    private void showDeniedDialog(final Activity activity, StringBuilder permissionName) {
        new AlertDialog.Builder(activity)
                .setMessage("使用本应用须打开:\n\n" +
                        permissionName + "\n等" + permissionTips.length + "项权限")
                .setPositiveButton("转至\"设置\"", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                    activity.finish();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                    mRequestPermissionCallBack.denied();
                }).setCancelable(false)
                .setOnCancelListener(dialog -> {
                    dialog.dismiss();
                    mRequestPermissionCallBack.denied();
                }).show();
    }

    /**
     * 用户点击“不在授权”后显示提示框
     *
     * @param activity       活动
     * @param permissionName 权限
     */
    private void showShouldShowRequestDialog(final Activity activity, StringBuilder permissionName) {
        new AlertDialog.Builder(activity)
                .setMessage("系统获取相关权限失败:\n\n" +
                        permissionName +
                        "\n授权失败将导致部分功能无法正常使用，需要到设置页面手动授权")
                .setPositiveButton("去授权", (dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                    intent.setData(uri);
                    activity.startActivity(intent);
                    activity.finish();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                    mRequestPermissionCallBack.denied();
                }).setCancelable(false)
                .setOnCancelListener(dialog -> {
                    dialog.dismiss();
                    mRequestPermissionCallBack.denied();
                }).show();
    }

    /**
     * 授权
     *
     * @param activity 活动
     * @param message  权限名称
     * @param listener 回调
     */
    private void showPermissionDialog(Activity activity, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(activity)
                .setTitle("权限请求")
                .setMessage("\n" + message)
                .setCancelable(false)
                .setPositiveButton("确定", listener).create().show();
    }

    /**
     * 判断是否授权
     */
    private boolean isPermissionGranted(Activity activity, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断用户是否勾选不再提示 返回false
     */
    private boolean isShouldShowRequestPermissionRationale(Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 不是M以上版本
         */
        void noM();

        /**
         * 授权成功
         */
        void granted();

        /**
         * 授权失败
         */
        void denied();
    }
}
