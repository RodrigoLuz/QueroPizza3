package com.pap.queropizza3.activities;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.pap.queropizza3.R;

/**
 * A simple {@link Fragment} subclass.
 */
// http://android-developers.blogspot.com.br/2012/05/using-dialogfragments.html
public class TrocoDialog extends DialogFragment implements TextView.OnEditorActionListener {


    public interface TrocoDialogListener {
        void onFinishTrocoDialog(Double inputValor);
    }

    private EditText mEditText;

    public TrocoDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_troco, container, false);
        mEditText = (EditText) view.findViewById(R.id.edtTextTroco);
        getDialog().setTitle("Troco para");

        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);
        return  view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            TrocoDialogListener activity = (TrocoDialogListener) getActivity();
            activity.onFinishTrocoDialog(Double.parseDouble(mEditText.getText().toString()));
            this.dismiss();
            return true;
        }
        return false;
    }
}
