package com.jatinhariani.seniorcitizenlauncher.launcher.models;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by jatin on 18/01/16.
 */
public class StoredContact implements Serializable{

    private String contactID;
    private String contactUri;
    private String name;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getContactUri() {
        return Uri.parse(contactUri);
    }

    public void setContactUri(Uri contactUri) {
        this.contactUri = contactUri.toString();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}
