package com.pap.queropizza3.adapters;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TCardapioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 22/04/2016.
 */
public class TExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String, List<TCardapioItem>> dados;
    private List<String> keys;
    int vQuant;

    public TExpandableAdapter(Map<String, List<TCardapioItem>> dados) {

        this.dados = dados;
        this.keys = new ArrayList<String>(
                dados.keySet());
    }

    protected void onCreate(Bundle savedInstanceState) {
//        TPedidoCardapio p;
//        p = dados.get(keys.get(groupPosition)).get(childPosition);
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

    // sub categorias
    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded, View convertView,
                             ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(
                    parent.getContext()).inflate(
                    android.R.layout. simple_expandable_list_item_1,
                    null);
        }
        TextView txt = (TextView)
                convertView.findViewById(android.R.id.text1);
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.GRAY);
        txt.setMinHeight(150);
        txt.setText(keys.get(groupPosition));
        return convertView;
    }

    // itens
    @Override
    public View getChildView(int groupPosition,
                             int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder = null;
        if (vi == null) {
            vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_lista_simples, parent, false);
            holder = new ViewHolder();

            holder.txtvQuant = (TextView)vi.findViewById(R.id.txtvQuant);
            holder.txtvItem = (TextView)vi.findViewById(R.id.txtvItem);

            holder.btnMenos = (Button)vi.findViewById(R.id.btnMenos);
            holder.btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder1 = (ViewHolder) v.getTag();
                    vQuant = Integer.parseInt(holder1.txtvQuant.getText().toString());
                    if (vQuant > 0) {
                        vQuant--;
                        holder1.txtvQuant.setText(Integer.toString(vQuant));

                    }
                }
            });

            holder.btnMais = (Button)vi.findViewById(R.id.btnMais);
            holder.btnMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder holder1 = (ViewHolder) v.getTag();
                    vQuant = Integer.parseInt(holder1.txtvQuant.getText().toString());
                    if (vQuant < 9) {
                        vQuant++;
                        holder1.txtvQuant.setText(Integer.toString(vQuant));
                    }
                }
            });
            vi.setTag(holder);

        }else{
            holder = (ViewHolder)vi.getTag();
        }
        holder.btnMais.setTag(holder);
        holder.btnMenos.setTag(holder);

//        TextView txtvQuant = (TextView)vi.findViewById(R.id.txtvQuant);
        TextView txtvItem = (TextView)vi.findViewById(R.id.txtvItem);

        TCardapioItem p;

        p = dados.get(keys.get(groupPosition)).get(childPosition);
//        txtvQuant.setText(Integer.toString(p.getQuantidade()));
        txtvItem.setText(p.getNome());
        return vi;
    }

    static class ViewHolder{
        TextView txtvQuant, txtvItem, txtvValor;
        Button btnMenos, btnMais;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return dados.get(
                keys.get(groupPosition)).get(childPosition);
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
        return true;
    }

    @Override
    public boolean isChildSelectable(
            int groupPosition, int childPosition) {
        return true;
    }

}
