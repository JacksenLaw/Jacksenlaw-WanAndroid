package com.jacksen.wanandroid.presenter.setting;

import android.widget.CompoundButton;

import com.jacksen.wanandroid.base.presenter.AbstractPresenter;
import com.jacksen.wanandroid.base.view.AbstractView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SettingContract {

    public interface View extends AbstractView {

    }

    interface Presenter extends AbstractPresenter<View> {

        /**
         * 设置夜间模式
         */
        void doNightClick(SettingPresenter.Callback callback, boolean isChecked);

    }

}
