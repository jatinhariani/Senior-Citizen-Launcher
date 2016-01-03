package com.jatinhariani.seniorcitizenlauncher.core.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jatin on 03/01/16.
 */
@Module
public class StorageModule {

    String mKey;

    public StorageModule(String key) {
        mKey = key;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences(mKey, Context.MODE_PRIVATE);
    }
}
