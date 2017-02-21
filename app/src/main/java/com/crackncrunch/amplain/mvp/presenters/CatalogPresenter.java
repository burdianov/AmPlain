package com.crackncrunch.amplain.mvp.presenters;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.CatalogScope;
import com.crackncrunch.amplain.mvp.models.CatalogModel;
import com.crackncrunch.amplain.mvp.views.ICatalogView;
import com.crackncrunch.amplain.mvp.views.IRootView;
import com.crackncrunch.amplain.ui.activities.RootActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;

public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements
        ICatalogPresenter {

    @Inject
    RootPresenter mRootPresenter;

    @Inject
    CatalogModel mCatalogModel;
    private List<ProductDto> mProductDtoList;

    public CatalogPresenter() {
        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);
    }

    @Override
    public void initView() {
        if (mProductDtoList == null) {
            mProductDtoList = mCatalogModel.getProductList();
        }
        if (getView() != null) {
            getView().showCatalogView(mProductDtoList);
        }
    }

    @Override
    public void clickOnBuyButton(int position) {
        if (getView() != null) {
            if (checkUserAuth()) {
                getRootView().showMessage("Item " +
                        mProductDtoList.get(position).getProductName() +
                        " added successfully to the Cart");
            } else {
                getView().showAuthScreen();
            }
        }
    }

    private IRootView getRootView() {
        return mRootPresenter.getView();
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAuth();
    }

    //region ==================== DI ===================

    private Component createDaggerComponent() {
        return DaggerCatalogPresenter_Component.builder()
                .component(DaggerService.getComponent(RootActivity.Component.class))
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {

        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class,
            modules = Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogPresenter presenter);
    }

    //endregion
}
