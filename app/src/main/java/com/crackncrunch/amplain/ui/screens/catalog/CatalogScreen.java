package com.crackncrunch.amplain.ui.screens.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.CatalogScope;
import com.crackncrunch.amplain.flow.AbstractScreen;
import com.crackncrunch.amplain.flow.Screen;
import com.crackncrunch.amplain.mvp.models.CatalogModel;
import com.crackncrunch.amplain.mvp.presenters.ICatalogPresenter;
import com.crackncrunch.amplain.mvp.presenters.RootPresenter;
import com.crackncrunch.amplain.mvp.views.IRootView;
import com.crackncrunch.amplain.ui.activities.RootActivity;
import com.crackncrunch.amplain.ui.screens.auth.AuthScreen;
import com.crackncrunch.amplain.ui.screens.product.ProductScreen;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;
import mortar.ViewPresenter;

@Screen(R.layout.screen_catalog)
public class CatalogScreen extends AbstractScreen<RootActivity.RootComponent> {

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerCatalogScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }

        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class,
            modules = Module.class)
    @CatalogScope
    public interface Component {
        void inject(CatalogPresenter presenter);
        void inject(CatalogView view);

        CatalogModel getCatalogModel();
        Picasso getPicasso();
    }

    //endregion

    //region ==================== Presenter ===================

    public class CatalogPresenter extends ViewPresenter<CatalogView> implements
            ICatalogPresenter {

        @Inject
        RootPresenter mRootPresenter;
        @Inject
        CatalogModel mCatalogModel;

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() != null) {
                getView().showCatalogView(mCatalogModel.getProductList());
            }
        }

        @Override
        public void clickOnBuyButton(int position) {
            if (getView() != null) {
                if (checkUserAuth() && getRootView() != null) {
                    getRootView().showMessage("Item " + mCatalogModel.getProductList()
                            .get(position).getProductName() +
                            " added successfully to the Cart");
                } else {
                    Flow.get(getView()).set(new AuthScreen());
                }
            }
        }

        @Nullable
        public IRootView getRootView() {
            return mRootPresenter.getView();
        }

        @Override
        public boolean checkUserAuth() {
            return mCatalogModel.isUserAuth();
        }
    }

    //endregion

    public static class Factory {
        public static Context createProductContext(ProductDto product, Context
                parentContext) {
            MortarScope parentScope = MortarScope.getScope(parentContext);
            MortarScope childScope;
            ProductScreen screen = new ProductScreen(product);
            String scopeName = String.format("%s_%d", screen.getScopeName(),
                    product.getId());

            if (parentScope.findChild(scopeName) == null) {
                childScope = parentScope.buildChild()
                        .withService(DaggerService.SERVICE_NAME, screen
                                .createScreenComponent(DaggerService
                                        .<Component>getDaggerComponent
                                                (parentContext)))
                        .build(scopeName);
            } else {
                childScope = parentScope.findChild(scopeName);
            }
            return childScope.createContext(parentContext);
        }
    }
}
