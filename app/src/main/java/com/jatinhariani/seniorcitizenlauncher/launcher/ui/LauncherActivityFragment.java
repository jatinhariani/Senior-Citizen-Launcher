package com.jatinhariani.seniorcitizenlauncher.launcher.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jatinhariani.seniorcitizenlauncher.MainApplication;
import com.jatinhariani.seniorcitizenlauncher.R;
import com.jatinhariani.seniorcitizenlauncher.core.ui.BaseFragment;
import com.jatinhariani.seniorcitizenlauncher.launcher.models.StoredContact;
import com.jatinhariani.seniorcitizenlauncher.launcher.presenters.LauncherPresenter;
import com.jatinhariani.seniorcitizenlauncher.launcher.views.LauncherView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LauncherActivityFragment extends BaseFragment<LauncherView, LauncherPresenter> implements LauncherView{

    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Inject
    SharedPreferences sharedPreferences;

    @Bind({R.id.btn_contact1, R.id.btn_contact2, R.id.btn_contact3, R.id.btn_contact4, R.id.btn_contact5, R.id.btn_contact6})
    List<Button> contactButton;

    @Bind({R.id.iv_contact1, R.id.iv_contact2, R.id.iv_contact3, R.id.iv_contact4, R.id.iv_contact5, R.id.iv_contact6})
    List<ImageView> contactImage;

    public LauncherActivityFragment() {}

    int currentLayout;

    @NonNull
    @Override
    public LauncherPresenter createPresenter() {
        return new LauncherPresenter(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApplication) getActivity().getApplication()).getAppComponent().inject(this);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
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

    @Override
    public void onStart() {
        getPresenter().displayStoredContacts();
        super.onStart();
    }

    @OnClick({R.id.btn_contact1, R.id.btn_contact2, R.id.btn_contact3, R.id.btn_contact4, R.id.btn_contact5, R.id.btn_contact6, R.id.btn_call, R.id.btn_sos})
    void onClickContactButton(View v) {
        Intent intent;
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
            case R.id.btn_sos:
                ArrayList<StoredContact> storedContacts = getPresenter().getStoredContacts();
                SOSConfirmFragment dialogFragment = new SOSConfirmFragment();
                dialogFragment.setStoredContacts(storedContacts);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "SOSConfirmFragment");
                return;
            default:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"));
                startActivity(intent);
                return;
        }

        //Check if the particular position is not empty
        if(getPresenter().getStoredContacts().size() > 0
                && getPresenter().getStoredContacts().size() > currentLayout
                && !getPresenter().getStoredPhoneNumber(currentLayout).getPhoneNumber().equals("")) {

            String uri = "tel:" + getPresenter().getStoredPhoneNumber(currentLayout).getPhoneNumber();
            intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            startActivity(intent);

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setMessage("Please long press to select a contact.");

            alertDialog.setCancelable(true);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            alertDialog.show();

            Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            //todo: dont use deprecated methods.
            button.setTextColor(getResources().getColor(android.R.color.black));

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
                break;
        }
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    getPresenter().getContact(currentLayout, data.getData());
                    break;
                default:
                    break;
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //todo: nothing to do here.
                } else {
                    //todo: show warning modal.
                }
            }
        }
    }

    @Override
    public void setContactName(int position, String name) {
        contactButton.get(position).setText(name);
    }

    @Override
    public void setContactImage(int position, Uri contactUri) {
        Picasso.with(getActivity()).load(contactUri).into(contactImage.get(position));
    }

    @Override
    public void setContactStyle(int position) {

        //TODO: @Neha needs to figure if there is a better way to just apply a previously defined style

        contactButton.get(position).setTextColor(getResources().getColor(android.R.color.white));
        contactButton.get(position).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        contactButton.get(position).setAllCaps(false);
        contactButton.get(position).setGravity(Gravity.LEFT | Gravity.BOTTOM);

        int padding = (int) getResources().getDimension(R.dimen.contact_padding);
        contactButton.get(position).setPadding(padding, padding, padding, padding);

        contactButton.get(position).setBackgroundResource(R.drawable.gradient_background);
    }
}
