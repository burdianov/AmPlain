package com.crackncrunch.amplain.ui.fragments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.ui.fragments.ProductFragment;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends FragmentPagerAdapter {
    private List<ProductDto> mProductList = new ArrayList<>();

    public CatalogAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductFragment.newInstance(mProductList.get(position));
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    public void addItem(ProductDto product) {
        mProductList.add(product);
        notifyDataSetChanged();
    }
}
