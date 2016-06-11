package com.pap.queropizza3.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
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
        protected TextView txtvChkItem;
        protected TextView txtvChkQuantidade;
        protected TextView txtvChkValor;
        protected Button btnExcluir;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View vi = convertView;

        if (vi == null) {
            holder = new ViewHolder();
            final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.layout_item_checkout, parent, false);

            holder.txtvChkItem = (TextView)vi.findViewById(R.id.txtvChkItem);
            holder.txtvChkQuantidade = (TextView)vi.findViewById(R.id.txtvChkQuantidade);
            holder.txtvChkValor = (TextView)vi.findViewById(R.id.txtvChkValor);
            holder.btnExcluir = (Button)vi.findViewById(R.id.btnExcluir);

            holder.btnExcluir.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    AppSQLDao dbDao;
                    dbDao = new AppSQLDao(context);
                    dbDao.listaTodosPedidoItem();

                    list.remove(position); //or some other task
                    notifyDataSetChanged();
                }
            });

            vi.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)vi.getTag();
        }

        TPedidoItem p = getItem(position);
        holder.txtvChkItem.setText(p.getSubitens().get(0).getCardapio_item().getNome());
        holder.txtvChkQuantidade.setText(String.valueOf(p.getQuantidade()));
        holder.txtvChkValor.setText(String.format( "%.2f", p.getValor()));

        return vi;
    }
}
