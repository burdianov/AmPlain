package com.crackncrunch.amplain.ui.screens.catalog;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.AbstractView;
import com.crackncrunch.amplain.mvp.views.ICatalogView;

import butterknife.BindView;
import butterknife.OnClick;


public class CatalogView extends AbstractView<CatalogScreen.CatalogPresenter>
        implements ICatalogView {

    @BindView(R.id.add_to_card_btn)
    Button mAddToCartBtn;
    @BindView(R.id.product_pager)
    ViewPager mProductPager;

    private CatalogAdapter mAdapter;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CatalogScreen.Component>getDaggerComponent(context)
                .inject(this);
        mAdapter = new CatalogAdapter();
    }

    //region ==================== ICatalogView ===================

    @Override
    public void showCatalogView() {
        mProductPager.setAdapter(mAdapter);
    }

    @Override
    public void updateProductCounter() {
        // TODO: 28-Oct-16 update count product on cart icon
    }

    public int getCurrentPagerPosition() {
        return mProductPager.getCurrentItem();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public CatalogAdapter getAdapter() {
        return mAdapter;
    }

    //endregion

    //region ==================== Events ===================

    @OnClick(R.id.add_to_card_btn)
    void clickAddToCart() {
        mPresenter.clickOnBuyButton(mProductPager.getCurrentItem());
    }

    //endregion
}
