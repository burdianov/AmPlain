package com.crackncrunch.amplain.di.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lilian on 21-Feb-17.
 */

@Module
public class PicassoCacheModule {

    @Provides
    @Singleton
    Picasso providePicasso(Context context) {
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(context);
        Picasso picasso = new Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .debugging(true)
                .build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
