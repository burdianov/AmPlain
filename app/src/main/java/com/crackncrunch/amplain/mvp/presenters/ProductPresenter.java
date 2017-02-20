package com.crackncrunch.amplain.mvp.presenters;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.mvp.models.ProductModel;
import com.crackncrunch.amplain.mvp.views.IProductView;

/**
 * Created by Lilian on 20-Feb-17.
 */
public class ProductPresenter extends AbstractPresenter<IProductView>
        implements IProductPresenter {
    public static final String TAG = "ProductPresenter";

    private ProductModel mProductModel;
    private ProductDto mProduct;

    public static ProductPresenter newInstance(ProductDto product) {
        return new ProductPresenter(product);
    }

    private ProductPresenter(ProductDto product) {
        mProductModel = new ProductModel();
        mProduct = product;
    }

    @Override
    public void initView() {
        if (getView() != null) {
            getView().showProductView(mProduct);
        }
    }

    @Override
    public void clickOnPlus() {
        mProduct.addProduct();
        mProductModel.updateProduct(mProduct);
        if (getView() != null) {
            getView().updateProductCountView(mProduct);
        }
    }

    @Override
    public void clickOnMinus() {
        if (mProduct.getCount() > 0) {
            mProduct.deleteProduct();
            mProductModel.updateProduct(mProduct);
            if (getView() != null) {
                getView().updateProductCountView(mProduct);
            }
        }
    }
}
