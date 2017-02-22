package com.crackncrunch.amplain.ui.screens.product;

import android.os.Bundle;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.ProductScope;
import com.crackncrunch.amplain.flow.AbstractScreen;
import com.crackncrunch.amplain.flow.Screen;
import com.crackncrunch.amplain.mvp.models.CatalogModel;
import com.crackncrunch.amplain.mvp.presenters.IProductPresenter;
import com.crackncrunch.amplain.ui.screens.catalog.CatalogScreen;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import mortar.ViewPresenter;

@Screen(R.layout.screen_product)
public class ProductScreen extends AbstractScreen<CatalogScreen.Component> {

    @Inject
    CatalogModel mCatalogModel;

    private ProductDto mProductDto;

    public ProductScreen(ProductDto product) {
        mProductDto = product;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProductScreen && mProductDto.equals(
                ((ProductScreen) o).mProductDto);
    }

    @Override
    public int hashCode() {
        return mProductDto.hashCode();
    }

    @Override
    public Object createScreenComponent(CatalogScreen.Component parentComponent) {
        return DaggerProductScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @ProductScope
        ProductPresenter provideProductPresenter() {
            return new ProductPresenter(mProductDto);
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class,
            modules = Module.class)
    @ProductScope
    public interface Component {
        void inject(ProductPresenter presenter);
        void inject(ProductView view);
    }

    //endregion

    //region ==================== Presenter ===================

    public class ProductPresenter extends ViewPresenter<ProductView>
            implements IProductPresenter {

        private ProductDto mProduct;

        @Inject
        CatalogModel mCatalogModel;

        public ProductPresenter(ProductDto productDto) {
            mProduct = productDto;
        }

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME))
                    .inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().showProductView(mProduct);
            }
        }

        @Override
        public void clickOnPlus() {
            mProduct.addProduct();
            mCatalogModel.updateProduct(mProduct);
            if (getView() != null) {
                getView().updateProductCountView(mProduct);
            }
        }

        @Override
        public void clickOnMinus() {
            if (mProduct.getCount() > 0) {
                mProduct.deleteProduct();
                mCatalogModel.updateProduct(mProduct);
                if (getView() != null) {
                    getView().updateProductCountView(mProduct);
                }
            }
        }
    }

    //endregion
}
