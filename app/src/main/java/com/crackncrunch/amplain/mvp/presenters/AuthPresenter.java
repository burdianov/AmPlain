package com.crackncrunch.amplain.mvp.presenters;

import android.support.annotation.Nullable;

import com.crackncrunch.amplain.mvp.models.AuthModel;
import com.crackncrunch.amplain.mvp.views.IAuthView;
import com.crackncrunch.amplain.ui.custom_views.AuthPanel;

public class AuthPresenter implements IAuthPresenter {

    private static AuthPresenter sInstance = new AuthPresenter();
    private AuthModel mAuthModel;
    private IAuthView mAuthView;

    private AuthPresenter() {
        mAuthModel = new AuthModel();
    }

    public static AuthPresenter getInstance() {
        return sInstance;
    }

    @Override
    public void takeView(IAuthView authView) {
        mAuthView = authView;
    }

    @Override
    public void dropView() {
        mAuthView = null;
    }

    @Override
    public void initView() {
        if (getView() != null) {
            if (checkUserAuth()) {
                getView().hideLoginBtn();
            } else {
                getView().showLoginBtn();
            }
        }
    }

    @Nullable
    @Override
    public IAuthView getView() {
        return mAuthView;
    }

    @Override
    public void clickOnLogin() {

        if (getView() != null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(AuthPanel.LOGIN_STATE);
            } else {
                // TODO: 21-Oct-16 auth user
                mAuthModel.loginUser(getView().getAuthPanel().getUserEmail(), getView
                        ().getAuthPanel().getUserPassword());
                getView().showMessage("request for user request");
            }
        }
    }

    @Override
    public void clickOnFb() {
        if (getView() != null) {
            getView().showMessage("clickOnFb");
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null) {
            getView().showMessage("clickOnVk");
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null) {
            getView().showMessage("clickOnTwitter");
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            getView().showMessage("Show the catalog");
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }
}
