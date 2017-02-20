package com.crackncrunch.amplain.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.mvp.presenters.ProductPresenter;
import com.crackncrunch.amplain.mvp.presenters.ProductPresenterFactory;
import com.crackncrunch.amplain.mvp.views.IProductView;
import com.crackncrunch.amplain.ui.activities.RootActivity;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lilian on 20-Feb-17.
 */

public class ProductFragment extends Fragment implements IProductView, View.OnClickListener {
    public static final String TAG = "ProductFragment";

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

    // TODO: 20-Feb-17 retrieve any image from internet via url with Picasso
    @BindDrawable(R.drawable.radio_image)
    Drawable mProductDraw;

    private ProductPresenter mPresenter;

    public ProductFragment() {

    }

    public static ProductFragment newInstance(ProductDto product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("PRODUCT", product);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            ProductDto product = bundle.getParcelable("PRODUCT");
            // TODO: 28-Oct-16 init presenter
            mPresenter = ProductPresenterFactory.getInstance(product);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        mPresenter.takeView(this);
        mPresenter.initView();
        mPlusBtn.setOnClickListener(this);
        mMinusBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    //region ==================== IProductView ===================

    @Override
    public void showProductView(ProductDto product) {
        mProductNameTxt.setText(product.getProductName());
        mProductDescriptionTxt.setText(product.getDescription());
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        } else {
            mProductPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }

        // TODO: 28-Oct-16 Picasso load from url
        mProductImage.setImageDrawable(mProductDraw);
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        }
    }

    @Override
    public void showMessage(String message) {
        getRootActivity().showMessage(message);
    }

    @Override
    public void showError(Throwable e) {
        getRootActivity().showError(e);
    }

    @Override
    public void showLoad() {
        getRootActivity().showLoad();
    }

    @Override
    public void hideLoad() {
        getRootActivity().hideLoad();
    }

    //endregion

    private RootActivity getRootActivity() {
        return (RootActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.minus_btn:
                mPresenter.clickOnMinus();
                break;
            case R.id.plus_btn:
                mPresenter.clickOnPlus();
                break;
        }
    }
}