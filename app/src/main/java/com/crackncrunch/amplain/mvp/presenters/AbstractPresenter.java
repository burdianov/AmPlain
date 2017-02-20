package com.crackncrunch.amplain.mvp.presenters;

import android.support.annotation.Nullable;

import com.crackncrunch.amplain.mvp.views.IView;

/**
 * Created by Lilian on 20-Feb-17.
 */

public abstract class AbstractPresenter<T extends IView> {
    private T mView;

    public void takeView(T view) {
        mView = view;
    }

    public void dropView() {
        mView = null;
    }

    public abstract void initView();

    @Nullable
    public T getView() {
        return mView;
    }
}