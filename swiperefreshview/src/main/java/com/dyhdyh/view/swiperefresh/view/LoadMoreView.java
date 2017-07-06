package com.dyhdyh.view.swiperefresh.view;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;

import com.dyhdyh.view.swiperefresh.R;
import com.dyhdyh.view.swiperefresh.loadmore.LoadMoreFooter;

/**
 * author  dengyuhan
 * created 2017/7/6 15:00
 */
public class LoadMoreView extends RelativeLayout implements LoadMoreFooter {
    private View mLoadingView;
    private View mErrorView;
    private View mTheEndView;
    private State mState;


    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.swipe_refresh_footer, this);
        setState(State.IDLE);
    }

    @Override
    public View getView() {
        return this;
    }

    /**
     * 设置状态
     *
     * @param state
     */
    @Override
    public void setState(State state) {
        this.mState = state;
        switch (state) {
            case IDLE:
                setVisibility(View.INVISIBLE);
                break;
            case LOADING:
                setOnClickListener(null);
                mLoadingView = setViewStateByInflate(mLoadingView, R.id.viewstub_load_footer_loading, mTheEndView, mErrorView);
                break;
            case THE_END:
                setOnClickListener(null);
                mTheEndView = setViewStateByInflate(mTheEndView, R.id.viewstub_load_footer_end, mLoadingView, mErrorView);
                break;
            case ERROR:
                mErrorView = setViewStateByInflate(mErrorView, R.id.viewstub_load_footer_error, mLoadingView, mTheEndView);
                break;
            default:
                break;
        }
    }

    @Override
    public State getState() {
        return this.mState;
    }

    private View setViewStateByInflate(View showView, @IdRes int inflateLayoutId, View... goneViews) {
        setVisibility(View.VISIBLE);
        for (View v : goneViews) {
            if (v != null) {
                v.setVisibility(View.GONE);
            }
        }

        if (showView == null) {
            ViewStub viewStub = (ViewStub) findViewById(inflateLayoutId);
            showView = viewStub.inflate();
        }
        showView.setVisibility(View.VISIBLE);
        return showView;
    }
}
