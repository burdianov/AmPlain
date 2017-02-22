package com.crackncrunch.amplain.ui.screens.product;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.IProductView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductView extends LinearLayout implements IProductView {

    public static final String TAG = "ProductView";

    @BindView(R.id.product_name_txt)
    TextView mProductNameTxt;
    @BindView(R.id.product_description_txt)
    TextView mProductDescriptionTxt;
    @BindView(R.id.product_image)
    ImageView mProductImage;
    @BindView(R.id.product_count_txt)
    TextView mProductCountTxt;
    @BindView(R.id.product_price_txt)
    TextView mProductPriceTxt;
    @BindView(R.id.plus_btn)
    ImageButton mPlusBtn;
    @BindView(R.id.minus_btn)
    ImageButton mMinusBtn;

    @Inject
    Picasso mPicasso;
    @Inject
    ProductScreen.ProductPresenter mPresenter;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<ProductScreen.Component>getDaggerComponent(context)
                    .inject(this);
        }
    }

    //region ==================== Flow view lifecycle callbacks ===================

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
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

    //region ==================== IProductView ===================

    @Override
    public void showProductView(final ProductDto product) {
        mProductNameTxt.setText(product.getProductName());
        mProductDescriptionTxt.setText(product.getDescription());
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        } else {
            mProductPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }

        mPicasso.load(product.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(mProductImage, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        mPicasso.load(product.getImageUrl())
                                .fit()
                                .centerCrop()
                                .placeholder(R.drawable.placeholder)
                                .into(mProductImage);
                    }
                });
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() >= 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion

    //region ==================== Events ===================

    @OnClick(R.id.plus_btn)
    void clickPlus() {
        mPresenter.clickOnPlus();
    }

    @OnClick(R.id.minus_btn)
    void clickMinus() {
        mPresenter.clickOnMinus();
    }

    //endregion
}
