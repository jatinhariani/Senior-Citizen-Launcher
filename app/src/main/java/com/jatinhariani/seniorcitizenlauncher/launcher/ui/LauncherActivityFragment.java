package com.jatinhariani.seniorcitizenlauncher.launcher.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jatinhariani.seniorcitizenlauncher.MainApplication;
import com.jatinhariani.seniorcitizenlauncher.R;
import com.jatinhariani.seniorcitizenlauncher.core.ui.BaseFragment;
import com.jatinhariani.seniorcitizenlauncher.launcher.presenters.LauncherPresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class LauncherActivityFragment extends BaseFragment<LauncherView, LauncherPresenter> implements LauncherView{

    @Inject
    SharedPreferences sharedPreferences;

    public LauncherActivityFragment() {}

    @Override
    public LauncherPresenter createPresenter() {
        return new LauncherPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launcher, container, false);
    }
}
