package com.crackncrunch.amplain;

import android.app.Application;

import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.components.AppComponent;
import com.crackncrunch.amplain.di.components.DaggerAppComponent;
import com.crackncrunch.amplain.di.modules.AppModule;
import com.crackncrunch.amplain.di.modules.PicassoCacheModule;
import com.crackncrunch.amplain.di.modules.RootModule;
import com.crackncrunch.amplain.mortar.ScreenScoper;
import com.crackncrunch.amplain.ui.activities.DaggerRootActivity_RootComponent;
import com.crackncrunch.amplain.ui.activities.RootActivity;

import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

/**
 * Created by Lilian on 21-Feb-17.
 */

public class App extends Application {

    private static AppComponent sAppComponent;

    private MortarScope mRootScope;
    private MortarScope mRootActivityScope;
    private RootActivity.RootComponent mRootActivityRootComponent;

    @Override
    public Object getSystemService(String name) {
        return mRootScope.hasService(name)
                ? mRootScope.getService(name)
                : super.getSystemService(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createAppComponent();
        createRootActivityComponent();

        mRootScope = MortarScope.buildRootScope()
                .withService(DaggerService.SERVICE_NAME, sAppComponent)
                .build("Root");

        mRootActivityScope = mRootScope.buildChild()
                .withService(DaggerService.SERVICE_NAME, mRootActivityRootComponent)
                .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                .build(RootActivity.class.getName());

        ScreenScoper.registerScope(mRootScope);
        ScreenScoper.registerScope(mRootActivityScope);
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
    
    private void createAppComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }

    private void createRootActivityComponent() {
       mRootActivityRootComponent = DaggerRootActivity_RootComponent.builder()
               .appComponent(sAppComponent)
               .rootModule(new RootModule())
               .picassoCacheModule(new PicassoCacheModule())
               .build();
    }
}
