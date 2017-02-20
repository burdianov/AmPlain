package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.managers.DataManager;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;

import java.util.List;

public class CatalogModel {
    DataManager mDataManager = DataManager.getInstance();

    public CatalogModel() {

    }

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAuth() {
        return mDataManager.isAuthUser();
    }
}
