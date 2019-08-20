package com.jacksen.wanandroid.presenter.todo.activity.create;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.app.Constants;
import com.jacksen.wanandroid.base.presenter.BasePresenter;
import com.jacksen.wanandroid.model.DataManager;
import com.jacksen.wanandroid.model.bean.todo.NewTodoBean;
import com.jacksen.wanandroid.model.bean.todo.TodoBean;
import com.jacksen.wanandroid.model.bus.BusConstant;
import com.jacksen.wanandroid.model.bus.LiveDataBus;
import com.jacksen.wanandroid.model.http.RxUtils;
import com.jacksen.wanandroid.model.http.base.BaseObserver;
import com.jacksen.wanandroid.util.MaterialDialogUtils;
import com.jacksen.wanandroid.view.bean.todo.ViewTodoData;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

/**
 * 作者： LuoM
 * 时间： 2019/5/1 0001
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
@SuppressWarnings("unused")
public class TodoCreatePresenter extends BasePresenter<TodoCreateContract.View> implements TodoCreateContract.Presenter {

    private boolean isNewCreateTodo;
    private String planDate;
    private String completedDate = SimpleDateFormat.getDateInstance(3, Locale.CHINA).format(System.currentTimeMillis()).replace("/", "-");
    private int id;
    private String type = "1";
    private String priority = "1";
    private String status = "1";
    private Dialog dialog;

    @Inject
    public TodoCreatePresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void showLoadingView() {
        super.showLoadingView();
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            isNewCreateTodo = bundle.getBoolean(Constants.ARG_PARAM1);
            if (!isNewCreateTodo) {
                ViewTodoData.ViewTodoItem bean = (ViewTodoData.ViewTodoItem) bundle.getSerializable(Constants.ARG_PARAM2);
                if (bean != null) {
                    id = bean.getId();
                    status = String.valueOf(bean.getStatus());
                    type = String.valueOf(bean.getType());
                    priority = String.valueOf(bean.getPriority());
                    planDate = bean.getPlanDate();
                    getView().setTitleText(bean.getTitle());
                    getView().setContentText(bean.getContent());
                    getView().setDateText(bean.getPlanDate());
                    getView().setTypeTextColor(bean.getTypeText(), castTypeColor(bean.getType()));
                    getView().setPriorityTextColor(bean.getPriorityText(), castPriorityColor(bean.getPriority()));
                    getView().setStatusTextColor(bean.getStatusText(), castStatusColor(bean.getStatus()));
                    getView().setToolbarTitleText(getActivity().getString(R.string.todo_edit_toolbar_title));
                    getView().setBtnText(getActivity().getString(R.string.todo_update));
                }
            }
        }
    }

    @Override
    public void doSwitchDateClick(String date) {
        String[] births = date.split("-");
        String oldYear = births[0];
        String oldMonth = births[1];
        String oldDay = births[2];
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), 0, (view, selectYear, selectMonth, selectDayOfMonth) -> {
            if (selectYear < Calendar.getInstance().get(Calendar.YEAR)
                    || selectMonth < Calendar.getInstance().get(Calendar.MONTH)
                    || selectDayOfMonth < Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                getView().showToast("只能选择今天以后的日期！");
                return;
            }
            planDate = selectYear + "-" + (selectMonth + 1) + "-" + selectDayOfMonth;
            getView().setDateText(planDate);
        }, Integer.parseInt(oldYear), Integer.parseInt(oldMonth) - 1, Integer.parseInt(oldDay));
        datePickerDialog.show();
    }

    @Override
    public void doSwitchTypeClick() {
        String[] contents = getActivity().getResources().getStringArray(R.array.filter_type);
        MaterialDialogUtils.showListDialog(getActivity(), getActivity().getString(R.string.todo_create_type), Arrays.asList(contents))
                .itemsCallback((dialog, itemView, position, text) -> {
                    switch (position) {
                        case 0:
                            type = "1";
                            getView().setTypeTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_type_work));
                            break;
                        case 1:
                            type = "2";
                            getView().setTypeTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_type_study));
                            break;
                        case 2:
                            type = "3";
                            getView().setTypeTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_type_life));
                            break;
                        default:
                            break;
                    }
                }).show();
    }

    @Override
    public void doSwitchPriorityClick() {
        String[] contents = getActivity().getResources().getStringArray(R.array.filter_priority);
        MaterialDialogUtils.showListDialog(getActivity(), getActivity().getString(R.string.todo_create_priority), Arrays.asList(contents))
                .itemsCallback((dialog, itemView, position, text) -> {
                    switch (position) {
                        case 0:
                            priority = "1";
                            getView().setPriorityTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_priority_important));
                            break;
                        case 1:
                            priority = "2";
                            getView().setPriorityTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_priority_general));
                            break;
                        case 2:
                            priority = "3";
                            getView().setPriorityTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_priority_ordinary));
                            break;
                        default:
                            break;
                    }
                }).show();
    }

    @Override
    public void doSwitchStatusClick() {
        String[] contents = getActivity().getResources().getStringArray(R.array.filter_status);
        MaterialDialogUtils.showListDialog(getActivity(), getActivity().getString(R.string.todo_create_status), Arrays.asList(contents))
                .itemsCallback((dialog, itemView, position, text) -> {
                    switch (position) {
                        case 0:
                            status = "1";
                            getView().setStatusTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_status_done));
                            break;
                        case 1:
                            status = "0";
                            getView().setStatusTextColor((String) text, ContextCompat.getColor(getActivity(), R.color.filter_status_undone));
                            break;
                        default:
                            break;
                    }
                }).show();
    }

    @Override
    public void doCreateClick(String title, String content) {

        if (isNewCreateTodo) {
            //新建todo
            addSubscribe(dataManager.addNewTodo(title, content, planDate, type, priority)
//                .filter(new Predicate<BaseResponse<NewTodoBean>>() {
//                    @Override
//                    public boolean test(BaseResponse<NewTodoBean> newTodoBeanBaseResponse) {
//                        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(content);
//                    }
//                })
                    .compose(RxUtils.rxSchedulerHelper())
                    .compose(RxUtils.handleResult())
                    .doOnSubscribe(disposable -> dialog = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), "请稍等...", true).show())
                    .subscribeWith(new BaseObserver<NewTodoBean>(getView()) {
                        @Override
                        public void onNext(NewTodoBean newTodoBean) {
                            //创建成功
                            dialog.dismiss();
                            getView().showToast("Todo创建成功！");
                            LiveDataBus.get().with(BusConstant.TODO_CREATED).postValue(null);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            dialog.dismiss();
                        }
                    }));
        } else {
            //更新todo
            addSubscribe(dataManager.updateTodo(id, title, content, "1".equals(status) ? completedDate : planDate, status, type, priority)
                    .compose(RxUtils.rxSchedulerHelper())
                    .compose(RxUtils.handleResult())
                    .doOnSubscribe(disposable -> dialog = MaterialDialogUtils.showIndeterminateProgressDialog(getActivity(), "请稍等...", true).show())
                    .subscribeWith(new BaseObserver<TodoBean>(getView()) {
                        @Override
                        public void onNext(TodoBean responseBody) {
                            //创建成功
                            dialog.dismiss();
                            getView().showToast("Todo更新成功！");
                            LiveDataBus.get().with(BusConstant.TODO_CREATED).postValue(null);
                            LiveDataBus.get().with(BusConstant.TODO_STATUS_DONE).postValue(null);
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            dialog.dismiss();
                        }
                    }));
        }
    }

    private int castTypeColor(int type) {
        if (type == 1) {
            return ContextCompat.getColor(getActivity(), R.color.filter_type_work);
        }
        if (type == 2) {
            return ContextCompat.getColor(getActivity(), R.color.filter_type_study);
        }
        if (type == 3) {
            return ContextCompat.getColor(getActivity(), R.color.filter_type_life);
        }
        return -1;
    }

    private int castPriorityColor(int priority) {
        if (priority == 1) {
            return ContextCompat.getColor(getActivity(), R.color.filter_priority_important);
        }
        if (priority == 2) {
            return ContextCompat.getColor(getActivity(), R.color.filter_priority_general);
        }
        if (priority == 3) {
            return ContextCompat.getColor(getActivity(), R.color.filter_priority_ordinary);
        }
        return -1;
    }

    private int castStatusColor(int status) {
        if (status == 1) {
            return ContextCompat.getColor(getActivity(), R.color.filter_status_done);
        }
        if (status == 0) {
            return ContextCompat.getColor(getActivity(), R.color.filter_status_undone);
        }
        return -1;
    }
}
