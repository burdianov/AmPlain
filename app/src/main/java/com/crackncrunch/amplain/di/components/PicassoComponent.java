package com.crackncrunch.amplain.di.components;

import com.crackncrunch.amplain.di.modules.PicassoCacheModule;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Lilian on 21-Feb-17.
 */

@Component(dependencies = AppComponent.class,
        modules = PicassoCacheModule.class)
@Singleton
public interface PicassoComponent {
    Picasso getPicasso();
}
