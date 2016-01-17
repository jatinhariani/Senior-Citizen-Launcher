package com.jatinhariani.seniorcitizenlauncher.launcher.models;

import android.net.Uri;

/**
 * Created by jatin on 18/01/16.
 */
public class StoredContact {

    private String name;
    private Uri contactUri;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getContactUri() {
        return contactUri;
    }

    public void setContactUri(Uri contactUri) {
        this.contactUri = contactUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
