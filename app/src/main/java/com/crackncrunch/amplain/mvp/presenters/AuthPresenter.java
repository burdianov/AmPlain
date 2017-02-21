package com.crackncrunch.amplain.mvp.presenters;

import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.AuthScope;
import com.crackncrunch.amplain.mvp.models.AuthModel;
import com.crackncrunch.amplain.mvp.views.IAuthView;
import com.crackncrunch.amplain.ui.custom_views.AuthPanel;

import javax.inject.Inject;

import dagger.Provides;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements
        IAuthPresenter {

    @Inject
    AuthModel mAuthModel;

    public AuthPresenter() {
        mAuthModel = new AuthModel();

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);
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
            // TODO: 20-Feb-17 If update data complete, start Catalog Screen
            getView().showCatalogScreen();
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(modules = Module.class)
    @AuthScope
    interface Component {
        void inject(AuthPresenter presenter);
    }

    private Component createDaggerComponent() {
        return DaggerAuthPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    //endregion
}
