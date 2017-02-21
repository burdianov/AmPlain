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
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.scopes.CatalogScope;
import com.crackncrunch.amplain.mvp.presenters.CatalogPresenter;
import com.crackncrunch.amplain.mvp.views.ICatalogView;
import com.crackncrunch.amplain.ui.fragments.adapters.CatalogAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Provides;

/**
 * Created by Lilian on 20-Feb-17.
 */

public class CatalogFragment extends Fragment implements ICatalogView, View.OnClickListener {
    public static final String TAG = "CatalogFragment";

    @Inject
    CatalogPresenter mPresenter;

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

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

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

    //region ==================== DI ===================

    private Component createDaggerComponent() {
        return DaggerCatalogFragment_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {

        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(modules = Module.class)
    @CatalogScope
    interface Component {
        void inject(CatalogFragment fragment);
    }

    //endregion
}

