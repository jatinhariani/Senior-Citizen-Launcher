package com.jatinhariani.seniorcitizenlauncher;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.jatinhariani.seniorcitizenlauncher.core.components.AppComponent;
import com.jatinhariani.seniorcitizenlauncher.core.components.DaggerAppComponent;
import com.jatinhariani.seniorcitizenlauncher.core.modules.AppModule;
import com.jatinhariani.seniorcitizenlauncher.core.modules.StorageModule;

import io.fabric.sdk.android.Fabric;

/**
 * Created by jatin on 03/01/16.
 */
public class MainApplication extends Application {

    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        //init crashlytics
        CrashlyticsCore core = new CrashlyticsCore.Builder().build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());

        //initialize dagger stuff
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .storageModule(new StorageModule("CONSUMER_APP_PREFERENCES"))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
