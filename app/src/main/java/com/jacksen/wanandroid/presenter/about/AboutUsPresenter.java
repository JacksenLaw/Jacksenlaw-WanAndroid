package com.jacksen.wanandroid.presenter.about;

import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class AboutUsPresenter extends BasePresenter<AboutUsContract.View> implements AboutUsContract.Presenter {

    @Inject
    public AboutUsPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
