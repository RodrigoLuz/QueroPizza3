package com.pap.queropizza3.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.List;

/**
 * Created by Rodrigo on 28/05/2016.
 */
public class TCheckoutAdapter extends ArrayAdapter<TPedidoItem> {

    private final List<TPedidoItem> list;
    private final Activity context;

    public TCheckoutAdapter(Activity context, int textViewResourceId, List<TPedidoItem> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView txtvItem;
        protected TextView txtvValor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_item_checkout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtvItem = (TextView)convertView.findViewById(R.id.txtvItemSabor);
            viewHolder.txtvValor = (TextView)convertView.findViewById(R.id.txtvValor);

            convertView.setTag(viewHolder);
            convertView.setTag(R.id.txtvItem, viewHolder.txtvItem);
            convertView.setTag(R.id.txtvValor, viewHolder.txtvValor);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtvItem.setText(list.get(position).getQuantidade());
        viewHolder.txtvValor.setText(String.format("%.2f", (list.get(position).getValor())));

        return convertView;
    }
}
