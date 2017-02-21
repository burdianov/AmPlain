package com.crackncrunch.amplain.mvp.views;

import android.support.annotation.Nullable;

import com.crackncrunch.amplain.mvp.presenters.IAuthPresenter;
import com.crackncrunch.amplain.ui.custom_views.AuthPanel;

/**
 * Created by Lilian on 19-Feb-17.
 */

public interface IAuthView extends IRootView {

    IAuthPresenter getPresenter();

    void showLoginBtn();
    void hideLoginBtn();

    @Nullable
    AuthPanel getAuthPanel();

    void showCatalogScreen();
}
