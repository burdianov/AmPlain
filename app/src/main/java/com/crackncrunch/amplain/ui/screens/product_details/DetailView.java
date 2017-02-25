package com.crackncrunch.amplain.ui.screens.product_details;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.realm.ProductRealm;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.AbstractView;

import butterknife.BindView;

/**
 * Created by Lilian on 25-Feb-17.
 */

public class DetailView extends AbstractView<DetailScreen.DetailPresenter> {

    @BindView(R.id.detail_pager)
    ViewPager mViewPager;

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DetailScreen.Component>getDaggerComponent(context)
            .inject(this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void initView(ProductRealm product) {
        DetailAdapter adapter = new DetailAdapter(product);
        mViewPager.setAdapter(adapter);
    }
}
