package com.crackncrunch.amplain.data.managers;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lilian on 20-Feb-17.
 */

public class DataManager {
    private static DataManager sInstance = new DataManager();

    private List<ProductDto> mMockProductList;

    public static DataManager getInstance() {
        return sInstance;
    }

    private DataManager() {
        generateMockData();
    }

    public ProductDto getProductById(int productId) {
        // TODO: 28-Oct-16 gets product from mock (to be converted to DB)
        return mMockProductList.get(productId + 1);
    }

    public List<ProductDto> getProductList() {
        // TODO: 28-Oct-16 get product list
        return mMockProductList;
    }

    public void updateProduct(ProductDto product) {
        // TODO: 28-Oct-16 update product count or other property and save to DB
    }

    private void generateMockData() {
        mMockProductList = new ArrayList<>();
        mMockProductList.add(new ProductDto(1, "test 1", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 100,
                1));
        mMockProductList.add(new ProductDto(2, "test 2", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 200,
                1));
        mMockProductList.add(new ProductDto(3, "test 3", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 300,
                1));
        mMockProductList.add(new ProductDto(4, "test 4", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 400,
                1));
        mMockProductList.add(new ProductDto(5, "test 5", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 500,
                1));
        mMockProductList.add(new ProductDto(6, "test 6", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 600,
                1));
        mMockProductList.add(new ProductDto(7, "test 7", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 700,
                1));
        mMockProductList.add(new ProductDto(8, "test 8", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 800,
                1));
        mMockProductList.add(new ProductDto(9, "test 9", "imageUrl", "description" +
                " 1 description 1 description 1 description 1 description 1", 900,
                1));
        mMockProductList.add(new ProductDto(10, "test 10", "imageUrl",
                "description " +
                        "1 description 1 description 1 description 1 description 1", 1000,
                1));
    }

    public boolean isAuthUser() {
        // TODO: 20-Feb-17 Check User auth token in SharedPreferences
        return false;
    }
}
