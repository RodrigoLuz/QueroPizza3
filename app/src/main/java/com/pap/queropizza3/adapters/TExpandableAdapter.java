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
import com.pap.queropizza3.models.TItemTela;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 22/04/2016.
 */
public class TExpandableAdapter extends BaseExpandableListAdapter {

    private Map<String, List<TItemTela>> dados;
    private List<String> keys;
    int vQuant, childPos, groupPos;

    public TExpandableAdapter(Map<String, List<TItemTela>> dados) {
        this.dados = dados;
        this.keys = new ArrayList<String>(dados.keySet());
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(
                    parent.getContext()).inflate(
                    android.R.layout. simple_expandable_list_item_1, null);
        }

        TextView txt = (TextView) convertView.findViewById(android.R.id.text1);
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.GRAY);
        txt.setMinHeight(150);
        txt.setText(keys.get(groupPosition));
        return convertView;
    }

    // itens
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder = null;
        childPos = childPosition;
        groupPos = groupPosition;

        if (vi == null) {
            vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_lista_simples, parent, false);

            holder = new ViewHolder();

            holder.txtvQuant = (TextView)vi.findViewById(R.id.txtvQuant);
            holder.txtvItem = (TextView)vi.findViewById(R.id.txtvItem);
            holder.txtvValor = (TextView)vi.findViewById(R.id.txtvValor);
            holder.btnMenos = (Button)vi.findViewById(R.id.btnMenos);
            holder.btnMais = (Button)vi.findViewById(R.id.btnMais);

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

            holder.btnMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TItemTela i; // cria um item/linha
                    i = dados.get(keys.get(groupPos)).get(childPos); // pega o item da listatela que será apresentado
                    ViewHolder holder1 = (ViewHolder) v.getTag(); // pega a linha que clicou
                    vQuant = i.getQuantidade(); // pega a quantidade
                    if (vQuant < 9) {
                        vQuant++;
                        i.setQuantidade(vQuant);
                        holder1.txtvQuant.setText(Integer.toString(vQuant)); // seta a quantidade
                    }
                }
            });

            vi.setTag(holder);
            vi.setTag(R.id.txtvQuant, holder.txtvQuant);
            vi.setTag(R.id.txtvItem, holder.txtvItem);
            vi.setTag(R.id.txtvValor, holder.txtvValor);

        }else{
            holder = (ViewHolder)vi.getTag();
        }

        holder.btnMais.setTag(holder);
        holder.btnMenos.setTag(holder);

        TItemTela i; // cria um item/linha
        i = dados.get(keys.get(groupPosition)).get(childPosition); // pega o item da listatela que será apresentado

        holder.txtvQuant.setText(String.valueOf(i.getQuantidade()));
        holder.txtvItem.setText(i.getNome());
        holder.txtvValor.setText(String.format("%.2f", (i.getValor())));

        return vi;
    }

    static class ViewHolder{
        protected TextView txtvQuant, txtvItem, txtvValor;
        protected Button btnMenos, btnMais;
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
        return true;
    }

    @Override
    public boolean isChildSelectable(
            int groupPosition, int childPosition) {
        return true;
    }

}
