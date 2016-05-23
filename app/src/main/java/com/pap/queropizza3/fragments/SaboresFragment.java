package com.pap.queropizza3.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TCheckAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TItemTela;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SaboresFragment#newInstance} factory method to
 * create an instance of this fragment.
 * http://stackoverflow.com/questions/4831918/how-to-get-all-checked-items-from-a-listview
 * http://stackoverflow.com/questions/33927312/listview-count-checked-items
 * http://stackoverflow.com/questions/16350670/listview-viewholder-checkbox-state
 * http://stackoverflow.com/questions/28252602/listview-with-viewholder-and-checkbox-state-save
 * http://android.amberfog.com/?p=296
 */
public class SaboresFragment extends Fragment implements AdapterView.OnItemClickListener{

    ListView lstvSabores;
    List<TItemTela> sabores = new ArrayList<TItemTela>();

    public SaboresFragment() {
        // Required empty public constructor
    }

    private static final String ARG_SECTION_NUMBER = "0";

    public static SaboresFragment newInstance(int sectionNumber) {
        SaboresFragment fragment = new SaboresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    // rotinas para talvez ajudar quandidade de sab selecionados
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
        TextView label = (TextView) v.getTag(R.id.txtvItemSabor);
        CheckBox checkbox = (CheckBox) v.getTag(R.id.chkbSelecao);
        Toast.makeText(v.getContext(), label.getText().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sabores, container, false);
        lstvSabores = (ListView)view.findViewById(R.id.lstvSabores);

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getActivity());
        sabores = dbDao.retornarItensPorGrupo(1);
        ArrayAdapter<TItemTela> adapter = new TCheckAdapter(getActivity(), R.layout.fragment_sabores, sabores);
        lstvSabores.setAdapter(adapter);
        lstvSabores.setOnItemClickListener(this);

        return view;
    }


}
