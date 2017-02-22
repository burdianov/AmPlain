package com.crackncrunch.amplain.ui.screens.account;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.AccountScope;
import com.crackncrunch.amplain.flow.AbstractScreen;
import com.crackncrunch.amplain.flow.Screen;
import com.crackncrunch.amplain.mvp.models.AccountModel;
import com.crackncrunch.amplain.mvp.presenters.IAccountPresenter;
import com.crackncrunch.amplain.mvp.presenters.RootPresenter;
import com.crackncrunch.amplain.mvp.views.IRootView;
import com.crackncrunch.amplain.ui.activities.RootActivity;
import com.crackncrunch.amplain.ui.screens.address.AddressScreen;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.ViewPresenter;

@Screen(R.layout.screen_account)
public class AccountScreen extends AbstractScreen<RootActivity.RootComponent> {

    private int mCustomState = 1;

    public int getCustomState() {
        return mCustomState;
    }

    public void setCustomState(int customState) {
        mCustomState = customState;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerAccountScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @AccountScope
        AccountModel provideAccountModel() {
            return new AccountModel();
        }

        @Provides
        @AccountScope
        AccountPresenter provideAccountPresenter() {
            return new AccountPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class,
            modules = Module.class)
    @AccountScope
    public interface Component {
        void inject(AccountPresenter presenter);
        void inject(AccountView view);

        RootPresenter getRootPresenter();
        AccountModel getAccountModel();
    }

    //endregion

    //region ==================== Presenter ===================

    public class AccountPresenter extends ViewPresenter<AccountView> implements
            IAccountPresenter {

        @Inject
        RootPresenter mRootPresenter;
        @Inject
        AccountModel mAccountModel;

        private Uri mAvatarUri;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {

                getView().initView(mAccountModel.getUserDto());
            }
        }

        @Override
        public void onClickAddress() {
            Flow.get(getView()).set(new AddressScreen());
        }

        @Override
        public void switchViewState() {
            if (getCustomState() == AccountView.EDIT_STATE && getView() != null) {
                mAccountModel.saveProfileInfo(getView().getUserName(), getView()
                        .getUserPhone(), String.valueOf(mAvatarUri));
            }
            if (getView() != null) {
                getView().changeState();
            }
        }

        @Override
        public void switchOrder(boolean isChecked) {
            mAccountModel.saveOrderNotification(isChecked);
        }

        @Override
        public void switchPromo(boolean isChecked) {
            mAccountModel.savePromoNotification(isChecked);
        }

        @Override
        public void takePhoto() {
            if (getView() != null) {
                getView().showPhotoSourceDialog();
            }
        }

        @Override
        public void chooseCamera() {
            if (getRootView() != null) {
                getRootView().showMessage("chooseCamera");
            }
            // TODO: 29-Nov-16 choose camera
        }

        @Override
        public void chooseGallery() {
            if (getRootView() != null) {
                getRootView().showMessage("chooseGallery");
            }
            // TODO: 29-Nov-16 choose gallery
        }

        @Nullable
        private IRootView getRootView() {
            return mRootPresenter.getView();
        }
    }

    //endregion
}
