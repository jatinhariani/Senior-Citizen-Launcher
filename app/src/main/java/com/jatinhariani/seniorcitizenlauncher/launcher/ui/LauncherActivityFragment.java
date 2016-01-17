package com.jatinhariani.seniorcitizenlauncher.launcher.ui;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jatinhariani.seniorcitizenlauncher.MainApplication;
import com.jatinhariani.seniorcitizenlauncher.R;
import com.jatinhariani.seniorcitizenlauncher.core.ui.BaseFragment;
import com.jatinhariani.seniorcitizenlauncher.launcher.presenters.LauncherPresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LauncherActivityFragment extends BaseFragment<LauncherView, LauncherPresenter> implements LauncherView{

    private static final int CONTACT_PICKER_RESULT = 1001;

    @Inject
    SharedPreferences sharedPreferences;

    @Bind({R.id.btn_contact1, R.id.btn_contact2, R.id.btn_contact3, R.id.btn_contact4, R.id.btn_contact5, R.id.btn_contact6})
    List<Button> contactButton;

    @Bind({R.id.fl_contact1, R.id.fl_contact2, R.id.fl_contact3, R.id.fl_contact4, R.id.fl_contact5, R.id.fl_contact6})
    List<FrameLayout> contactLayout;

    @Bind({R.id.iv_contact1, R.id.iv_contact2, R.id.iv_contact3, R.id.iv_contact4, R.id.iv_contact5, R.id.iv_contact6})
    List<ImageView> contactImage;



    public LauncherActivityFragment() {}

    Uri uriContact;
    String contactID;

    int currentLayout;

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
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_contact1, R.id.btn_contact2, R.id.btn_contact3, R.id.btn_contact4, R.id.btn_contact5, R.id.btn_contact6})
    void onClickContactButton(View v) {
        switch(v.getId()) {
            case R.id.btn_contact1:
                currentLayout = 0;
                break;
            case R.id.btn_contact2:
                currentLayout = 1;
                break;
            case R.id.btn_contact3:
                currentLayout = 2;
                break;
            case R.id.btn_contact4:
                currentLayout = 3;
                break;
            case R.id.btn_contact5:
                currentLayout = 4;
                break;
            case R.id.btn_contact6:
                currentLayout = 5;
                break;
            default:
                Log.d("test", "default");
        }
    }

    @OnLongClick({R.id.btn_contact1, R.id.btn_contact2, R.id.btn_contact3, R.id.btn_contact4, R.id.btn_contact5, R.id.btn_contact6})
    boolean onLongClickContactButton(View v) {
        switch(v.getId()) {
            case R.id.btn_contact1:
                currentLayout = 0;
                break;
            case R.id.btn_contact2:
                currentLayout = 1;
                break;
            case R.id.btn_contact3:
                currentLayout = 2;
                break;
            case R.id.btn_contact4:
                currentLayout = 3;
                break;
            case R.id.btn_contact5:
                currentLayout = 4;
                break;
            case R.id.btn_contact6:
                currentLayout = 5;
                break;
            default:
                Log.d("test", "default");
        }
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    uriContact = data.getData();
                    retrieveContactName();
                    retrieveContactNumber();
                    Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
                    Picasso.with(getActivity()).load(my_contact_Uri).into(contactImage.get(currentLayout));
                default:
                    break;
            }

        } else {
            // gracefully handle failure
            Log.w("test", "Warning: activity result not ok");
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getActivity().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d("test", "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
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

        Log.d("test", "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getActivity().getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();
        contactButton.get(currentLayout).setText(contactName);

        Log.d("test", "Contact Name: " + contactName);

    }
}
