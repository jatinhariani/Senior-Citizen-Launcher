package com.jatinhariani.seniorcitizenlauncher.launcher.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jatinhariani.seniorcitizenlauncher.R;
import com.jatinhariani.seniorcitizenlauncher.launcher.models.StoredContact;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class SOSConfirmFragment extends DialogFragment {

    ArrayList<StoredContact> storedContacts;

    public SOSConfirmFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.confirm_sos, null);
        builder.setView(view);
        ButterKnife.bind(this, view);

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_confirm_sos)
    void sendSMS() {
        SmsManager smsManager = SmsManager.getDefault();
        for(StoredContact i : storedContacts) {
            smsManager.sendTextMessage(i.getPhoneNumber(), null, getResources().getString(R.string.sms_message), null, null);
        }
        dismiss();
    }

    public void setStoredContacts(ArrayList<StoredContact> storedContacts) {
        this.storedContacts = storedContacts;
    }
}
