package com.jacksen.wanandroid.presenter.setting;

import android.Manifest;

import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.core.manager.PermissionManager;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.util.KLog;

import javax.inject.Inject;

import solid.ren.skinlibrary.SkinLoaderListener;
import solid.ren.skinlibrary.loader.SkinManager;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SettingPresenter extends BasePresenter<SettingContract.View> implements SettingContract.Presenter {

    private boolean shouldRequestPermission = true;

    @Inject
    public SettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void doNightClick(Callback callback, boolean isChecked) {
        if (!shouldRequestPermission) {
            shouldRequestPermission = true;
            return;
        }
        PermissionManager.getInstance().get(getActivity())
                .requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new String[]{"允许程序使用存储权限", "允许程序使用存储权限"})
                .requestCodes(1001)
                .request(new PermissionManager.RequestPermissionCallBack() {
                    @Override
                    public void noM() {
                        KLog.i("无需授权");
                        if (isChecked) {
                            loadSkin();
                            setNightMode(true);
                        } else {
                            setNightMode(false);
                            skinDefault();
                        }
                    }

                    @Override
                    public void granted() {
                        KLog.i("授权成功");
                        if (isChecked) {
                            loadSkin();
                            setNightMode(true);
                        } else {
                            setNightMode(false);
                            skinDefault();
                        }
                    }

                    @Override
                    public void denied() {
                        KLog.e("授权失败");
                        shouldRequestPermission = false;
                        callback.authorizationDenied();
                    }
                });
    }

    private void skinDefault() {
        SkinManager.getInstance().restoreDefaultTheme();
        LiveDataBus.get().with(BusConstant.NIGHT_MODEL).setValue(false);
    }

    private void loadSkin() {
        SkinManager.getInstance().loadSkin("theme-20190817.skin",
                new SkinLoaderListener() {
                    @Override
                    public void onStart() {
                        KLog.i("正在切换中");
                        //dialog.show();
                    }

                    @Override
                    public void onSuccess() {
                        KLog.i("切换成功");

                        LiveDataBus.get().with(BusConstant.NIGHT_MODEL).setValue(true);
                    }

                    @Override
                    public void onFailed(String errMsg) {
                        KLog.i("切换失败:" + errMsg);
                    }

                    @Override
                    public void onProgress(int progress) {
                        KLog.i("皮肤文件下载中:" + progress);

                    }
                }

        );
    }

    public interface Callback {
        //授权失败
        void authorizationDenied();
    }

}
