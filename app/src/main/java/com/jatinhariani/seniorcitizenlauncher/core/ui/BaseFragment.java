package com.jatinhariani.seniorcitizenlauncher.core.ui;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by jatin on 03/01/16.
 */
public abstract class BaseFragment<V extends MvpView, P extends MvpPresenter<V>>
extends MvpFragment<V, P> {
}
