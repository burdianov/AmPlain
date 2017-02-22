package com.crackncrunch.amplain.ui.screens.address;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.data.storage.dto.UserAddressDto;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.IAddressView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressView extends RelativeLayout implements IAddressView {

    @Inject
    AddressScreen.AddressPresenter mPresenter;

    public AddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            DaggerService.<AddressScreen.Component>getDaggerComponent(context)
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
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }

    //endregion

    //region ==================== IAddressView ===================

    @Override
    public void showInputError() {
        // TODO: 29-Nov-16 implement this
    }

    @Override
    public UserAddressDto getUserAddress() {
        // TODO: 29-Nov-16 implement this
        return null;
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion

    //region ==================== Events ===================

    @OnClick(R.id.add_address_btn)
    void AddAddress() {
        mPresenter.clickOnAddAddress();
    }

    //endregion


}
