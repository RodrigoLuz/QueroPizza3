package com.pap.queropizza3.adapters;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoDetalhe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo on 28/06/2016.
 * http://cyrilmottier.com/2011/11/23/listview-tips-tricks-4-add-several-clickable-areas/
 */
public class TCheckPizzaAdapter extends BaseExpandableListAdapter {

    private List<String> keys;
    private Map<String, List<TPedidoDetalhe>> dados;

    public TCheckPizzaAdapter(Map<String, List<TPedidoDetalhe>> dados) {
        this.dados = dados;
        this.keys = new ArrayList<String>(dados.keySet());
    }

    protected void onCreate(Bundle savedInstanceState) {
    }

    // sub categorias
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.expandable_list_item_button, null);
            ImageButton btnExcluirPizza = (ImageButton)convertView.findViewById(R.id.btnExcluirPizza);
            btnExcluirPizza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TPedidoDetalhe det = (TPedidoDetalhe) getChild(groupPosition, 0); // pega o primeiro child

                    String group = (String) getGroup(groupPosition);
                    Toast.makeText(view.getContext(), group + " - " + det.getCardapio_item().getNome(), Toast.LENGTH_SHORT).show();


                    AppSQLDao dbDao;
                    dbDao = new AppSQLDao(view.getContext());
                    dbDao.apagarPedidoItem(det.getId_item());

                    dados.remove(getGroup(groupPosition)); // substituir adapter por cursor ?
                    notifyDataSetChanged();

                    //Toast.makeText(view.getContext(), group, Toast.LENGTH_SHORT).show();
                }
            });

        }

        TextView txt = (TextView) convertView.findViewById(android.R.id.text1);
        txt.setText(keys.get(groupPosition));
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.rgb(178, 34, 34));
        txt.setMinHeight(130);
        return convertView;
    }

    // itens
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_checkout_pizza, parent, false);
        }

        TextView txtvChkItem = (TextView)convertView.findViewById(R.id.txtvChkItem);
        TextView txtvChkDescricao = (TextView)convertView.findViewById(R.id.txtvChkDescricao);

        TPedidoDetalhe item = dados.get(keys.get(groupPosition)).get(childPosition);
        txtvChkItem.setText(String.valueOf(item.getCardapio_item().getNome()));
        txtvChkDescricao.setText(item.getCardapio_item().getDescricao());

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
