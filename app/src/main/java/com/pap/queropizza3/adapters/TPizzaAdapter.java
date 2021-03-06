package com.pap.queropizza3.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TItemTela;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 27/06/2016.
 */
public class TPizzaAdapter extends BaseExpandableListAdapter {

    private List<String> keys;
    private Map<String, List<TItemTela>> dados;
    private final Activity context;
    private final int sabores;

    public TPizzaAdapter(Activity context, int sabores, Map<String, List<TItemTela>> dados) {
        this.sabores = sabores;
        this.context = context;
        this.dados = dados;
        this.keys = new ArrayList<String>(dados.keySet());
    }

    protected void onCreate(Bundle savedInstanceState) {

    }

    // sub categorias
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(
                    parent.getContext()).inflate(
                    android.R.layout. simple_expandable_list_item_1, null);
        }

        TextView txt = (TextView) convertView.findViewById(android.R.id.text1);
        txt.setText(keys.get(groupPosition));
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.rgb(0, 102, 51));
        txt.setMinHeight(130);
        return convertView;
    }

    // itens
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_pizza, parent, false);
        }

        CheckBox chkbSelecao = (CheckBox)convertView.findViewById(R.id.chkbSelecao);
        TextView txtvItemSabor = (TextView)convertView.findViewById(R.id.txtvItemSabor);
        TextView txtvIngredientes = (TextView)convertView.findViewById(R.id.txtvIngredientes);
        TextView txtvValor = (TextView)convertView.findViewById(R.id.txtvValor);
        final TItemTela item = dados.get(keys.get(groupPosition)).get(childPosition);

        chkbSelecao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                item.setSelecionado(buttonView.isChecked());
                int i = getCheckedItemCount(dados);
                if (i > sabores) {
                    item.setSelecionado(false);
                    buttonView.setChecked(false);
                    Toast.makeText(context, "Máximo " + sabores + " sabores", Toast.LENGTH_SHORT).show();
                }

                FloatingActionButton fab = (FloatingActionButton) context.findViewById(R.id.fab);
                if (i == 0) {
                    fab.setVisibility(View.INVISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }

           }
         });

        chkbSelecao.setChecked(item.isSelecionado());
        txtvItemSabor.setText(item.getCardapio_item().getNome());
        txtvIngredientes.setText(item.getCardapio_item().getDescricao());
        txtvValor.setText(String.format("%.2f", (item.getCardapio_item().getValor())));

        return convertView;
    }

    public int getCheckedItemCount(Map<String, List<TItemTela>> dados){
        int count = 0;
        for (String key: keys) {
            for (TItemTela item: dados.get(key)) {
                if (item.isSelecionado()){
                    count ++;
                }
            }
        }
        return count;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return keys.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return dados.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dados.get(keys.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dados.get(keys.get(groupPosition)).size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(
            int groupPosition, int childPosition) {
        return true;
    }


}
