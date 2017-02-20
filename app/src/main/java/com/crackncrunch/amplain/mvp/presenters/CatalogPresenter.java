package com.crackncrunch.amplain.mvp.presenters;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.mvp.models.CatalogModel;
import com.crackncrunch.amplain.mvp.views.ICatalogView;

import java.util.List;

public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements
        ICatalogPresenter {
    private static CatalogPresenter sInstance = new CatalogPresenter();

    private CatalogModel mCatalogModel;
    private List<ProductDto> mProductDtoList;

    public static CatalogPresenter getInstance() {
        return sInstance;
    }

    private CatalogPresenter() {
        mCatalogModel = new CatalogModel();
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
                getView().showAddToCartMessage(mProductDtoList.get(position));
            } else {
                getView().showAuthScreen();
            }
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAuth();
    }
}
