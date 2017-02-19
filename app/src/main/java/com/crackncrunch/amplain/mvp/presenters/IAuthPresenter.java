package com.crackncrunch.amplain.mvp.presenters;

import android.support.annotation.Nullable;

import com.crackncrunch.amplain.mvp.views.IAuthView;

/**
 * Created by Lilian on 19-Feb-17.
 */

public interface IAuthPresenter {
    void takeView(IAuthView authView);
    void dropView();
    void initView();

    @Nullable
    IAuthView getView();

    void clickOnLogin();
    void clickOnFb();
    void clickOnVk();
    void clickOnTwitter();
    void clickOnShowCatalog();

    boolean checkUserAuth();
}
