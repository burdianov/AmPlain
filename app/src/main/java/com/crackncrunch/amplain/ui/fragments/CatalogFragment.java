package com.crackncrunch.amplain.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.ProductDto;
import com.crackncrunch.amplain.mvp.presenters.CatalogPresenter;
import com.crackncrunch.amplain.mvp.views.ICatalogView;
import com.crackncrunch.amplain.ui.activities.RootActivity;
import com.crackncrunch.amplain.ui.fragments.adapters.CatalogAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lilian on 20-Feb-17.
 */

public class CatalogFragment extends Fragment implements ICatalogView, View.OnClickListener {
    public static final String TAG = "CatalogFragment";
    private CatalogPresenter mPresenter = CatalogPresenter.getInstance();

    @BindView(R.id.add_to_card_btn)
    Button mAddToCartBtn;
    @BindView(R.id.product_pager)
    ViewPager mProductPager;

    public CatalogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);
        mPresenter.takeView(this);
        mPresenter.initView();
        mAddToCartBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_to_card_btn) {
            mPresenter.clickOnBuyButton(mProductPager.getCurrentItem());
        }
    }

    //region ==================== ICatalogView ===================

    @Override
    public void showAddToCartMessage(ProductDto productDto) {
        showMessage("Item " + productDto.getProductName() + " added successfully " +
                "to the Cart");
    }

    @Override
    public void showCatalogView(List<ProductDto> productsList) {
        CatalogAdapter adapter = new CatalogAdapter(getChildFragmentManager());
        for (ProductDto product : productsList) {
            adapter.addItem(product);
        }
        mProductPager.setAdapter(adapter);
    }

    @Override
    public void showAuthScreen() {
        // TODO: 28-Oct-16 show auth screen if user is not authorized
    }

    @Override
    public void updateProductCounter() {
        // TODO: 28-Oct-16 update count product on cart icon
    }

    //endregion

    //region ==================== IView ===================

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
}

