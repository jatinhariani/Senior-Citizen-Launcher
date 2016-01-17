package com.jatinhariani.seniorcitizenlauncher.launcher.presenters;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;

/**
 * Created by jatin on 03/01/16.
 */
public class LauncherPresenter extends MvpBasePresenter<LauncherView> {

    Activity mActivity;

    public LauncherPresenter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void getContact(int position, Uri uriContact) {
        retrieveContactName(position, uriContact);
        retrieveContactNumber(position, uriContact);
        getView().setContactImage(position, uriContact);
    }

    public void saveContact() {
//        sharedPreferences.edit().putOb
    }

    public void retrieveContactName(int position, Uri uriContact) {

        String contactName = null;

        Cursor cursor = mActivity.getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();
        if(isViewAttached()) {
            getView().setContactName(position, contactName);
        }

    }

    public void retrieveContactNumber(int position, Uri uriContact) {

    }
}
