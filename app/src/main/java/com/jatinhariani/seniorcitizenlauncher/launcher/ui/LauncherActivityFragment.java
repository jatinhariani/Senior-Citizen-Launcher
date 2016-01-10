package com.jatinhariani.seniorcitizenlauncher.launcher.ui;

import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jatinhariani.seniorcitizenlauncher.MainApplication;
import com.jatinhariani.seniorcitizenlauncher.R;
import com.jatinhariani.seniorcitizenlauncher.core.ui.BaseFragment;
import com.jatinhariani.seniorcitizenlauncher.launcher.presenters.LauncherPresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LauncherActivityFragment extends BaseFragment<LauncherView, LauncherPresenter> implements LauncherView{

    private static final int CONTACT_PICKER_RESULT = 1001;

    @Inject
    SharedPreferences sharedPreferences;

    @Bind({R.id.btn_contact1})
    List<Button> contactButton;

    @Bind({R.id.ll_contact1})
    List<LinearLayout> contactLayout;

    public LauncherActivityFragment() {}

    Uri uriContact;
    String contactID;

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

    @OnClick({R.id.btn_contact1, R.id.btn_contact2})
    void onClickContactbutton(View v) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
        switch(v.getId()) {
            case R.id.btn_contact1:
                Log.d("test", "1");
                break;
            case R.id.btn_contact2:
                Log.d("test", "2");
                break;
            default:
                Log.d("test", "default");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    uriContact = data.getData();
                    retrieveContactName();
                    retrieveContactNumber();
                    retrieveContactPhoto();
                    break;
                default:
                    break;
            }

        } else {
            // gracefully handle failure
            Log.w("test", "Warning: activity result not ok");
        }
    }

    private void retrieveContactPhoto() {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getActivity().getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactID)));

            if (inputStream != null) {
//                ImageView imageView = (ImageView) findViewById(R.id.img_contact);
//                imageView.setImageBitmap(photo);
                contactLayout.get(0).setBackground(new BitmapDrawable(getActivity().getResources(), inputStream));
                assert inputStream != null;
                inputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
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

        Log.d("test", "Contact Name: " + contactName);

    }
}
