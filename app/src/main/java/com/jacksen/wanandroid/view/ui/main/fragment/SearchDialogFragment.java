package com.jacksen.wanandroid.view.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.KeyboardUtils;
import com.jacksen.wanandroid.R;
import com.jacksen.wanandroid.base.fragment.BaseDialogFragment;
import com.jacksen.wanandroid.model.bean.db.HistorySearchData;
import com.jacksen.wanandroid.presenter.search.SearchContract;
import com.jacksen.wanandroid.presenter.search.SearchPresenter;
import com.jacksen.wanandroid.util.CommonUtils;
import com.jacksen.wanandroid.util.JudgeUtils;
import com.jacksen.wanandroid.util.KLog;
import com.jacksen.wanandroid.view.bean.useful_search.ViewTextBean;
import com.jacksen.wanandroid.view.ui.main.adapter.HistorySearchAdapter;
import com.jacksen.wanandroid.widget.CircularRevealAnim;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import butterknife.BindView;

/**
 * 作者： LuoM
 * 时间： 2019/3/30 0030
 * 描述：
 * 版本： v1.0.0
 * 更新： 本次修改内容
 */
public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.search_tint_tv)
    TextView mTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_history_clear_all_tv)
    TextView mClearAllHistoryTv;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.search_history_null_tint_tv)
    TextView mHistoryNullTintTv;
    @BindView(R.id.search_history_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;

    //Dialog出现动画
    private CircularRevealAnim mCircularRevealAnim;
    private HistorySearchAdapter mAdapter;

    private boolean isKeyboardShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void initListener() {
        mSearchTv.setOnClickListener(v ->
                mPresenter.doSearchClick(mSearchEdit.getText().toString().trim())
        );
        mClearAllHistoryTv.setOnClickListener(v -> mPresenter.doClearHistoryClick());
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                    mTintTv.setText(R.string.search_tint);
                } else {
                    mTintTv.setText("");
                }
            }
        });

        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            KLog.i("dialog is onKey = " + keyCode);
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!isKeyboardShow) {
                    mCircularRevealAnim.hide(mSearchEdit, mRootView);
                }
                if (isKeyboardShow) {
                    isKeyboardShow = false;
                    KeyboardUtils.hideSoftInput(_mActivity);
                }
            }
            return false;
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                KeyboardUtils.hideSoftInput(_mActivity);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
    }

    private void initDialog() {
        Window window = getDialog().getWindow();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (metrics.widthPixels * 0.98);
        assert window != null;
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过渡动画 , 使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    @Override
    protected void initCreateView() {

    }

    @Override
    protected void initEventAndData() {
        setCircleAnimation();
        initRecyclerView();
        mPresenter.loadAllHistoryData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSearchEdit.setText("");
    }

    private void setCircleAnimation() {
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(new CircularRevealAnim.AnimListener() {
            @Override
            public void onHideAnimationEnd() {
                KLog.i("onHideAnimationEnd");
                dismissAllowingStateLoss();
            }

            @Override
            public void onShowAnimationEnd() {
                KLog.i("onShowAnimationEnd");
                KeyboardUtils.showSoftInput(mSearchEdit);
                isKeyboardShow = true;
            }
        });
        mSearchEdit.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mSearchEdit.getViewTreeObserver().removeOnPreDrawListener(this);
                mCircularRevealAnim.show(mSearchEdit, mRootView);
                return true;
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new HistorySearchAdapter(null);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mSearchEdit.setText(((List<HistorySearchData>) adapter.getData()).get(position).getData());
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showHistoryData(List<HistorySearchData> historyDataList) {
        if (historyDataList != null && historyDataList.size() > 0) {
            setHistoryTvStatus(false);
            mAdapter.setNewData(historyDataList);
        } else {
            setHistoryTvStatus(true);
            mAdapter.setNewData(null);
        }
    }

    @Override
    public void showTopSearchData(ViewTextBean items) {
        mTopSearchFlowLayout.setAdapter(new TagAdapter<ViewTextBean.ViewTextItem>(items.getItems()) {
            @Override
            public View getView(FlowLayout parent, int position, ViewTextBean.ViewTextItem item) {
                TextView tv = (TextView) LayoutInflater.from(_mActivity).inflate(R.layout.flow_layout_tv,
                        parent, false);
                assert item != null;
                String name = item.getText();
                tv.setText(name);
                tv.setBackgroundColor(CommonUtils.randomTagColor());
                tv.setTextColor(ContextCompat.getColor(_mActivity, R.color.white));
                mTopSearchFlowLayout.setOnTagClickListener((view, position1, parent1) -> {
                    mSearchEdit.setText(items.getItems().get(position1).getText().trim());
                    mSearchEdit.setSelection(mSearchEdit.getText().length());
                    return true;
                });
                return tv;
            }
        });
    }

    private void setHistoryTvStatus(boolean isClearAll) {
        mClearAllHistoryTv.setEnabled(!isClearAll);
        if (isClearAll) {
            setHistoryTvStatus(View.VISIBLE, R.color.search_grey_gone, R.drawable.ic_clear_all_gone);
        } else {
            setHistoryTvStatus(View.GONE, R.color.search_grey, R.drawable.ic_clear_all);
        }
    }

    private void setHistoryTvStatus(int visibility, @ColorRes int textColor, @DrawableRes int clearDrawable) {
        Drawable drawable;
        mHistoryNullTintTv.setVisibility(visibility);
        mClearAllHistoryTv.setTextColor(ContextCompat.getColor(_mActivity, textColor));
        drawable = ContextCompat.getDrawable(_mActivity, clearDrawable);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mClearAllHistoryTv.setCompoundDrawables(drawable, null, null, null);
        mClearAllHistoryTv.setCompoundDrawablePadding(CommonUtils.dp2px(6));
    }

    @Override
    public void judgeToTheSearchListActivity() {
        KeyboardUtils.hideSoftInput(_mActivity);
        mCircularRevealAnim.hide(mSearchEdit, mRootView);
        JudgeUtils.startSearchListActivity(_mActivity, mSearchEdit.getText().toString().trim());
    }
}
