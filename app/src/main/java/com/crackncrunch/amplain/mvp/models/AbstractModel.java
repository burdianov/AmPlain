package com.crackncrunch.amplain.mvp.models;

import com.crackncrunch.amplain.data.managers.DataManager;
import com.crackncrunch.amplain.di.DaggerService;
import com.crackncrunch.amplain.di.components.DaggerModelComponent;
import com.crackncrunch.amplain.di.components.ModelComponent;
import com.crackncrunch.amplain.di.modules.ModelModule;

import javax.inject.Inject;

/**
 * Created by Lilian on 21-Feb-17.
 */

public abstract class AbstractModel {

    @Inject
    DataManager mDataManager;

    public AbstractModel() {
        ModelComponent component =
                DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}
