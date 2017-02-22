package com.crackncrunch.amplain.ui.screens.address;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.AddressScope;
import com.crackncrunch.amplain.flow.AbstractScreen;
import com.crackncrunch.amplain.flow.Screen;
import com.crackncrunch.amplain.mvp.models.AccountModel;
import com.crackncrunch.amplain.mvp.presenters.IAddressPresenter;
import com.crackncrunch.amplain.ui.screens.account.AccountScreen;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;
import mortar.ViewPresenter;

@Screen(R.layout.screen_add_address)
public class AddressScreen extends AbstractScreen<AccountScreen.Component>
        implements TreeKey {

    @Override
    public Object createScreenComponent(AccountScreen.Component parentComponent) {
        return DaggerAddressScreen_Component.builder().component(parentComponent)
                .module(new Module())
                .build();
    }

    @Override
    public Object getParentKey() {
        return new AccountScreen();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @AddressScope
        AddressPresenter provideAddressPresenter() {
            return new AddressPresenter();
        }
    }

    @dagger.Component(dependencies = AccountScreen.Component.class,
            modules = Module.class)
    @AddressScope
    public interface Component {
        void inject(AddressPresenter presenter);

        void inject(AddressView view);

    }
    //endregion


    //region ==================== Presenter ===================

    public class AddressPresenter extends ViewPresenter<AddressView> implements
            IAddressPresenter {

        @Inject
        AccountModel mAccountModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        public void clickOnAddAddress() {
            // TODO: 29-Nov-16 save address in model
            if (getView() != null) {
                mAccountModel.addAddress(getView().getUserAddress());
                Flow.get(getView()).goBack();
            }
        }
    }

    //endregion
}
