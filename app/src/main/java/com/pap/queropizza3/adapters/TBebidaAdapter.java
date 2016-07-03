package com.pap.queropizza3.adapters;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TItemTela;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 22/04/2016.
 * http://www.thiengo.com.br/expandablelistview-no-android-entendendo-e-utilizando
 */
public class TBebidaAdapter extends BaseExpandableListAdapter {

    private List<String> keys;
    private Map<String, List<TItemTela>> dados;

    public TBebidaAdapter(Map<String, List<TItemTela>> dados) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bebida, parent, false);
        }

        final TextView txtvQuant = (TextView)convertView.findViewById(R.id.txtvQuant);
        TextView txtvItem = (TextView)convertView.findViewById(R.id.txtvItem);
        TextView txtvValor = (TextView)convertView.findViewById(R.id.txtvValor);
        ImageButton btnMenos = (ImageButton)convertView.findViewById(R.id.btnMenos);
        ImageButton btnMais = (ImageButton)convertView.findViewById(R.id.btnMais);

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vQuant = dados.get(keys.get(groupPosition)).get(childPosition).getQuantidade();
                if (vQuant > 0) {
                    vQuant--;
                    dados.get(keys.get(groupPosition)).get(childPosition).setQuantidade(vQuant);
                }
                txtvQuant.setText(String.valueOf(dados.get(keys.get(groupPosition)).get(childPosition).getQuantidade()));
            }
        });

        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vQuant = dados.get(keys.get(groupPosition)).get(childPosition).getQuantidade(); // pega a quantidade
                if (vQuant < 9) {
                    vQuant++;
                    dados.get(keys.get(groupPosition)).get(childPosition).setQuantidade(vQuant);
                }
                txtvQuant.setText(String.valueOf(dados.get(keys.get(groupPosition)).get(childPosition).getQuantidade()));
            }
        });

        TItemTela item = dados.get(keys.get(groupPosition)).get(childPosition);
        txtvQuant.setText(String.valueOf(item.getQuantidade()));
        txtvItem.setText(item.getCardapio_item().getNome());
        txtvValor.setText(String.format("%.2f", (item.getCardapio_item().getValor())));

        return convertView;
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
