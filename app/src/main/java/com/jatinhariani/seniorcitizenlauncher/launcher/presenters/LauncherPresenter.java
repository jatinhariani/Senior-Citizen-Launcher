package com.jatinhariani.seniorcitizenlauncher.launcher.presenters;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.models.StoredContact;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;
import com.tramsun.libs.prefcompat.Pref;

import java.util.ArrayList;

public class LauncherPresenter extends MvpBasePresenter<LauncherView> {

    Activity mActivity;
    ArrayList<StoredContact> storedContacts;

    public LauncherPresenter(Activity mActivity) {
        this.mActivity = mActivity;
        storedContacts = Pref.getObject("storedContacts", ArrayList.class, new ArrayList<>());
    }

    public void getContact(int position, Uri uriContact) {

        StoredContact storedContact = new StoredContact();

        storedContact.setContactUri(uriContact);
        storedContact.setName(retrieveContactName(uriContact));
        storedContact.setContactID(retreiveContactID(uriContact));
        storedContact.setPhoneNumber(retrieveContactNumber(storedContact.getContactID()));

        if(storedContacts.size() > position) {
            //thing already exists.
            storedContacts.set(position, storedContact);
        }
        else {
            storedContacts.add(storedContact);
        }

        Pref.putObject("storedContacts", storedContacts);

        if(isViewAttached()) {
            getView().setContactImage(position, uriContact);
            getView().setContactName(position, storedContact.getName());
            getView().setContactStyle(position);
        }
    }

    public String retrieveContactName(Uri uriContact) {

        String contactName = null;

        Cursor cursor = mActivity.getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        return contactName;
    }

    public String retreiveContactID(Uri uriContact) {

        String contactID = null;

        // getting contacts ID
        Cursor cursorID = mActivity.getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();
        return contactID;

    }

    public String retrieveContactNumber(String contactID) {

        String contactNumber = null;

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = mActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();
        return contactNumber;
    }

    public void displayStoredContacts() {

        int counter = 0;
        if(isViewAttached()) {
            for(StoredContact storedContact : storedContacts) {
                getView().setContactName(counter, storedContact.getName());
                getView().setContactImage(counter, storedContact.getContactUri());
                getView().setContactStyle(counter);
                counter++;
            }
        }
    }

    //retrieves stored contact
    public StoredContact getStoredPhoneNumber(int position) {
        return storedContacts.get(position);
    }
}
