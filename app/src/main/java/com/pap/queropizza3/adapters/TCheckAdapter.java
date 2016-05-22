package com.pap.queropizza3.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.models.TItemTela;

import java.util.List;

/**
 * Created by Rodrigo on 17/05/2016.
 * http://lalit3686.blogspot.com.br/2012/06/today-i-am-going-to-show-how-to-deal.html
 */
public class TCheckAdapter extends ArrayAdapter<TItemTela> {

        private final List<TItemTela> list;
        private final Activity context;

        public TCheckAdapter(Activity context, int textViewResourceId, List<TItemTela> list) {
            super(context, textViewResourceId, list);
            this.context = context;
            this.list = list;
        }

        static class ViewHolder {
            protected TextView txtvItemSabor;
            protected TextView txtvIngredientes;
            protected TextView txtvValor;
            protected CheckBox chkbSelecao;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.layout_item_sabor, parent, false);

                viewHolder = new ViewHolder();

                viewHolder.chkbSelecao = (CheckBox)convertView.findViewById(R.id.chkbSelecao);
                viewHolder.txtvItemSabor = (TextView)convertView.findViewById(R.id.txtvItemSabor);
                viewHolder.txtvIngredientes = (TextView)convertView.findViewById(R.id.txtvIngredientes);
                viewHolder.txtvValor = (TextView)convertView.findViewById(R.id.txtvValor);

                viewHolder.chkbSelecao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                        list.get(getPosition).setSelecionado(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    }
                });
                convertView.setTag(viewHolder);
                convertView.setTag(R.id.chkbSelecao, viewHolder.chkbSelecao);
                convertView.setTag(R.id.txtvItemSabor, viewHolder.txtvItemSabor);
                convertView.setTag(R.id.txtvIngredientes, viewHolder.txtvIngredientes);
                convertView.setTag(R.id.txtvValor, viewHolder.txtvValor);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.chkbSelecao.setTag(position); // This line is important.

            viewHolder.chkbSelecao.setChecked(list.get(position).isSelecionado());
            viewHolder.txtvItemSabor.setText(list.get(position).getNome());
            viewHolder.txtvIngredientes.setText(list.get(position).getIngredientes());
            viewHolder.txtvValor.setText(String.format("%.2f", (list.get(position).getValor())));

            return convertView;
        }

}
