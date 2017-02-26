package com.crackncrunch.amplain.ui.screens.auth;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.mvp.views.IAuthView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

import static com.crackncrunch.amplain.utils.ConstantsManager.CUSTOM_FONTS_ROOT;
import static com.crackncrunch.amplain.utils.ConstantsManager.CUSTOM_FONT_NAME;

/**
 * Created by Lilian on 21-Feb-17.
 */

public class AuthView extends RelativeLayout implements IAuthView {

    public static final int LOGIN_STATE = 0;
    public static final int IDLE_STATE = 1;

    @BindView(R.id.app_name_txt)
    TextView mAppNameTxt;
    @BindView(R.id.auth_card)
    CardView mAuthCard;
    @BindView(R.id.show_catalog_btn)
    Button mShowCatalogBtn;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.login_email_et)
    EditText mEmailEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;

    private AuthScreen mScreen;

    @Inject
    AuthScreen.AuthPresenter mPresenter;

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            mScreen = Flow.getKey(this);
            DaggerService.<AuthScreen.Component>getDaggerComponent(context)
                    .inject(this);
        }
        // TODO: 22-Feb-17 get mScreen and dagger component
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if (!isInEditMode()) {
            showViewFromState();
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

    private void showViewFromState() {
        if (mScreen.getCustomState() == LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void showLoginState() {
        mAuthCard.setVisibility(VISIBLE);
        mShowCatalogBtn.setVisibility(GONE);
    }

    private void showIdleState() {
        mAuthCard.setVisibility(GONE);
        mShowCatalogBtn.setVisibility(VISIBLE);
    }

    //region ==================== Events ===================

    @OnClick(R.id.login_btn)
    void loginClick() {
        mPresenter.clickOnLogin();
    }

    @OnClick(R.id.fb_social_btn)
    void fbClick() {
        mPresenter.clickOnFb();
    }

    @OnClick(R.id.twitter_social_btn)
    void twitterClick() {
        mPresenter.clickOnTwitter();
    }

    @OnClick(R.id.vk_social_btn)
    void vkClick() {
        mPresenter.clickOnVk();
    }

    @OnClick(R.id.show_catalog_btn)
    void catalogClick() {
        mPresenter.clickOnShowCatalog();
    }

    //endregion

    //region ==================== IAuthView ===================

    @Override
    public void setTypeface() {
        mAppNameTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                CUSTOM_FONTS_ROOT + CUSTOM_FONT_NAME));
    }

    @Override
    public void showLoginBtn() {
        mLoginBtn.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mLoginBtn.setVisibility(GONE);
    }

    @Override
    public String getUserEmail() {
        return String.valueOf(mEmailEt.getText());
    }

    @Override
    public String getUserPassword() {
        return String.valueOf(mPasswordEt.getText());
    }

    @Override
    public boolean isIdle() {
        return mScreen.getCustomState() == IDLE_STATE;
    }

    @Override
    public void setCustomState(int state) {
        mScreen.setCustomState(state);
        showViewFromState();
    }

    @Override
    public boolean viewOnBackPressed() {
        if (!isIdle()) {
            setCustomState(IDLE_STATE);
            return true;
        } else {
            return false;
        }
    }

    //endregion
}
