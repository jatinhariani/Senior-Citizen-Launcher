package com.jatinhariani.seniorcitizenlauncher.core.components;

import com.jatinhariani.seniorcitizenlauncher.core.modules.AppModule;
import com.jatinhariani.seniorcitizenlauncher.core.modules.StorageModule;
import com.jatinhariani.seniorcitizenlauncher.launcher.ui.LauncherActivityFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jatin on 03/01/16.
 */
@Singleton
@Component(modules = {AppModule.class, StorageModule.class})
public interface AppComponent {

    void inject(LauncherActivityFragment fragment);

}