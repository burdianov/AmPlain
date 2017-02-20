package com.crackncrunch.amplain.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.crackncrunch.amplain.BuildConfig;
import com.crackncrunch.amplain.R;
import com.crackncrunch.amplain.mvp.views.IView;
import com.crackncrunch.amplain.ui.fragments.AccountFragment;
import com.crackncrunch.amplain.ui.fragments.CatalogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RootActivity extends AppCompatActivity implements IView, NavigationView
        .OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorContainer;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    protected ProgressDialog mProgressDialog;

    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);

        initToolbar();
        initDrawer();

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,
                            new CatalogFragment())
                    .commit();
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_account:
                fragment = new AccountFragment();
                break;
            case R.id.nav_catalog:
                fragment = new CatalogFragment();
                break;
            case R.id.nav_favorites:
                break;
            case R.id.nav_orders:
                break;
            case R.id.nav_notifications:
                break;
        }
        if (fragment != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //region ==================== IView ===================

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorContainer, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage(getString(R.string.error_message));
            // TODO: 22-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.CustomDialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable
                    (Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    //endregion
}