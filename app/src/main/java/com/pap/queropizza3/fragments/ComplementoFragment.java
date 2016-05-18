package com.pap.queropizza3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pap.queropizza3.R;
import com.pap.queropizza3.activities.GrupoActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComplementoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComplementoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplementoFragment extends Fragment {

    Button btnContinuar;

    public ComplementoFragment() {
        // Required empty public constructor
    }

    private static final String ARG_SECTION_NUMBER = "1";

    public static ComplementoFragment newInstance(int sectionNumber) {
        ComplementoFragment fragment = new ComplementoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_complemento, container, false);
    }


}


//  Button btNext = (Button)getView().findViewById(R.id.btNextCli);
//  btNext.setOnClickListener(new View.OnClickListener() {