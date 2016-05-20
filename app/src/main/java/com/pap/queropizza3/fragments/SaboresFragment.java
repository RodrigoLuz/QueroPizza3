package com.pap.queropizza3.fragments;

import android.content.Context;
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
import com.pap.queropizza3.models.AppSQLDao;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TSabores;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaboresFragment.OnFragmentInteractionListener} interface
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
    List<TSabores> sabores = new ArrayList<TSabores>();

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


    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
//        TextView label = (TextView) v.getTag(R.id.label);
//        CheckBox checkbox = (CheckBox) v.getTag(R.id.check);
//        Toast.makeText(v.getContext(), label.getText().toString()+" "+isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();
    }

    private String isCheckedOrNot(CheckBox checkbox) {
        if(checkbox.isChecked())
            return "is checked";
        else
            return "is not checked";
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

        sabores = retornarSabores();
        ArrayAdapter<TSabores> adapter = new TCheckAdapter(getActivity(), R.layout.fragment_sabores, sabores);
        lstvSabores.setAdapter(adapter);
        lstvSabores.setOnItemClickListener(this);

        return view;
    }

    public List<TSabores> retornarSabores(){
        List<TSabores> sabores = new ArrayList<TSabores>();
        TSabores s;

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getActivity());

        List<TCardapioItem> itens = dbDao.listaSabores(null);

        for(int i = 0 ; i < itens.size(); i++){
            s = new TSabores();
            s.setNome(itens.get(i).getNome());
            s.setIngredientes(itens.get(i).getDescricao());
            s.setValor(itens.get(i).getValor());
            sabores.add(s);
        }

        return sabores;
    }

    public void chkbSelecaoClick(View view) {
    }
}
