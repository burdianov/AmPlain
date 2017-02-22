package com.crackncrunch.amplain.ui.screens.catalog;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.ICatalogView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CatalogView extends RelativeLayout implements ICatalogView {

    @BindView(R.id.add_to_card_btn)
    Button mAddToCartBtn;
    @BindView(R.id.product_pager)
    ViewPager mProductPager;

    @Inject
    CatalogScreen.CatalogPresenter mPresenter;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            DaggerService.<CatalogScreen.Component>getDaggerComponent(context)
                    .inject(this);
        }
    }

    //region ==================== Flow view lifecycle callbacks ===================

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if (!isInEditMode()) {
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }

    //endregion

    //region ==================== ICatalogView ===================

    @Override
    public void showCatalogView(List<ProductDto> productsList) {
        CatalogAdapter adapter = new CatalogAdapter();
        for (ProductDto product : productsList) {
            adapter.addItem(product);
        }
        mProductPager.setAdapter(adapter);
    }

    @Override
    public void updateProductCounter() {
        // TODO: 28-Oct-16 update count product on cart icon
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion

    //region ==================== Events ===================

    @OnClick(R.id.add_to_card_btn)
    void clickAddToCart() {
        mPresenter.clickOnBuyButton(mProductPager.getCurrentItem());
    }

    //endregion
}
