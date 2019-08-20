package com.jacksen.wanandroid.core.prefs;

import android.text.TextUtils;

import com.blankj.utilcode.utils.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.core.factory.GsonFactory;

/**
 * 作者： LuoM
 * 创建时间：2019/8/20/0020
 * 描述：
 * 版本号： v1.0.0
 * 更新时间：2019/8/20/0020
 * 更新内容：
 */
public class PreferenceUtils {

    //保存时间单位
    public static final int TIME_SECOND = 1;
    public static final int TIME_MINUTES = 60 * TIME_SECOND;
    public static final int TIME_HOUR = 60 * TIME_MINUTES;
    public static final int TIME_DAY = TIME_HOUR * 24;
    public static final int TIME_MAX = Integer.MAX_VALUE; // 不限制存放数据的数量
    public static final int DURATION_UNIT = 1000;

    private static PreferenceUtils instance;
    private SPUtils spUtils;

    public static PreferenceUtils getInstance() {
        if (instance == null) {
            synchronized (PreferenceUtils.class) {
                if (instance == null) {
                    instance = new PreferenceUtils();
                }
            }
        }
        return instance;
    }

    public PreferenceUtils() {
        spUtils = new SPUtils(Constants.MY_SHARED_PREFERENCE);
    }

    public SPUtils getSpUtils() {
        return spUtils;
    }

    public void putString(String e, String value) {
        PreferenceModel<String> spSaveModel = new PreferenceModel<>(TIME_MAX, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    /**
     * @param e        存放的key
     * @param value    存放的value
     * @param saveTime 缓存时间
     */
    public void putString(String e, String value, int saveTime) {
        PreferenceModel<String> spSaveModel = new PreferenceModel<>(saveTime, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    /**
     * @param e        存放的key
     * @param defValue 该key不存在或者过期时，返回的默认值
     * @return
     */
    public String getString(String e, String defValue) {
        String json = spUtils.getString(e, "");
        if (!TextUtils.isEmpty(json)) {
            PreferenceModel<String> spSaveModel = GsonFactory.provideGson().fromJson(json, new TypeToken<PreferenceModel<String>>() {
            }.getType());
            if (isTimeOut(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            }
        }
        return defValue;
    }

    public void putInt(String e, int value) {
        PreferenceModel<Integer> spSaveModel = new PreferenceModel<>(TIME_MAX, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public void putInt(String e, int value, int saveTime) {
        PreferenceModel<Integer> spSaveModel = new PreferenceModel<>(saveTime, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public Integer getInt(String e, int defValue) {
        String json = spUtils.getString(e, "");
        if (!TextUtils.isEmpty(json)) {
            PreferenceModel<Integer> spSaveModel = GsonFactory.provideGson().fromJson(json, new TypeToken<PreferenceModel<Integer>>() {
            }.getType());
            if (isTimeOut(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            }
        }
        return defValue;
    }

    public void putBoolean(String e, boolean value) {
        PreferenceModel<Boolean> spSaveModel = new PreferenceModel<>(TIME_MAX, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public void putBoolean(String e, boolean value, int saveTime) {
        PreferenceModel<Boolean> spSaveModel = new PreferenceModel<>(saveTime, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public boolean getBoolean(String e, boolean defValue) {
        String json = spUtils.getString(e, "");
        if (!TextUtils.isEmpty(json)) {
            PreferenceModel<Boolean> spSaveModel = GsonFactory.provideGson().fromJson(json, new TypeToken<PreferenceModel<Boolean>>() {
            }.getType());
            if (isTimeOut(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            }
        }
        return defValue;
    }

    public void putLong(String e, long value) {
        PreferenceModel<Long> spSaveModel = new PreferenceModel<>(TIME_MAX, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public void putLong(String e, long value, int saveTime) {
        PreferenceModel<Long> spSaveModel = new PreferenceModel<>(saveTime, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public long getLong(String e, long defValue) {
        String json = spUtils.getString(e, "");
        if (!TextUtils.isEmpty(json)) {
            PreferenceModel<Long> spSaveModel = GsonFactory.provideGson().fromJson(json, new TypeToken<PreferenceModel<Long>>() {
            }.getType());
            if (isTimeOut(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            }
        }
        return defValue;
    }

    public boolean isTimeOut(long saveCurrentTime, int saveTime) {
        return (System.currentTimeMillis() - saveCurrentTime) / DURATION_UNIT < saveTime;
    }

    public void set(String e, Object value, int saveTime) {
        PreferenceModel<Object> spSaveModel = new PreferenceModel<>(saveTime, value, System.currentTimeMillis());
        String json = GsonFactory.provideGson().toJson(spSaveModel);
        spUtils.putString(e, json);
    }

    public void clear() {
        spUtils.clear();
    }

}
