package com.pap.queropizza3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaboresSelecionadosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaboresSelecionadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaboresSelecionadosFragment extends Fragment {

    public SaboresSelecionadosFragment() {
        // Required empty public constructor
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static SaboresSelecionadosFragment newInstance(int sectionNumber) {
        SaboresSelecionadosFragment fragment = new SaboresSelecionadosFragment();
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
        return inflater.inflate(R.layout.fragment_sabores_selecionados, container, false);
    }

}
