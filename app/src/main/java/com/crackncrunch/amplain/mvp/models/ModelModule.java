package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.managers.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lilian on 21-Feb-17.
 */

@Module
public class ModelModule {

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }
}
