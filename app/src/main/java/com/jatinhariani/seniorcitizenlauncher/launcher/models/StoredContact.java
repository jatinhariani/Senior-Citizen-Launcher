package com.jatinhariani.seniorcitizenlauncher.launcher.models;

import android.net.Uri;

/**
 * Created by jatin on 18/01/16.
 */
public class StoredContact {

    private String name;
    private Uri photoUri;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
