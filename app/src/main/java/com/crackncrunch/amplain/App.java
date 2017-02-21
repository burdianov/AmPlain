package com.crackncrunch.amplain;

import android.app.Application;

import com.crackncrunch.amplain.di.components.AppComponent;
import com.crackncrunch.amplain.di.components.DaggerAppComponent;
import com.crackncrunch.amplain.di.modules.AppModule;

/**
 * Created by Lilian on 21-Feb-17.
 */

public class App extends Application {

    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        createComponent();
    }

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }
    
    private void createComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
