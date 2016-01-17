package com.jatinhariani.seniorcitizenlauncher.launcher.views;

import android.net.Uri;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by jatin on 03/01/16.
 */
public interface LauncherView extends MvpView {

    void setContactName(String name);
    void setContactImage(Uri pictureUri);

}
