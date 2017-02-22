package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;

import java.util.List;

public class CatalogModel extends AbstractModel {

    public CatalogModel() {

    }

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }

    public ProductDto getProductById(int productId) {
        // TODO: 28-Oct-16 get product from datamanager
        return mDataManager.getProductById(productId);
    }

    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }
}
