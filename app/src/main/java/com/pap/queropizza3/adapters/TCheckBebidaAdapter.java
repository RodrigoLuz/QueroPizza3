package com.pap.queropizza3.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.List;

/**
 * Created by Rodrigo on 28/05/2016.
 */
public class TCheckBebidaAdapter extends ArrayAdapter<TPedidoItem> {

    private final List<TPedidoItem> list;
    private final Activity context;

    public TCheckBebidaAdapter(Activity context, int textViewResourceId, List<TPedidoItem> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView txtvChkItem;
        protected TextView txtvChkQuantidade;
        protected TextView txtvChkValor;
        protected ImageButton btnExcluirChk;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View vi = convertView;

        if (vi == null) {
            holder = new ViewHolder();
            final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = inflater.inflate(R.layout.layout_checkout_bebida, parent, false);

            holder.txtvChkItem = (TextView)vi.findViewById(R.id.txtvChkItem);
            holder.txtvChkQuantidade = (TextView)vi.findViewById(R.id.txtvChkQuantidade);
            holder.txtvChkValor = (TextView)vi.findViewById(R.id.txtvChkValor);
            holder.btnExcluirChk = (ImageButton)vi.findViewById(R.id.btnExcluirChk);

            holder.btnExcluirChk.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    AppSQLDao dbDao;
                    dbDao = new AppSQLDao(context);
                    dbDao.apagarPedidoItem(list.get(position).getId_item());

                    list.remove(position); // substituir adapter por cursor ?
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
