package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.managers.DataManager;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;

public class ProductModel {
    DataManager mDataManager = DataManager.getInstance();

    public ProductDto getProductById(int productId) {
        // TODO: 28-Oct-16 get product from datamanager
        return mDataManager.getProductById(productId);
    }

    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }
}
