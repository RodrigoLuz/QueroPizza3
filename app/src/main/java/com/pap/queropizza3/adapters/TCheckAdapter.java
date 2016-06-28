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
 * http://www.vogella.com/tutorials/AndroidListView/article.html
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
            ViewHolder holder = null;
            View vi = convertView;

            if (vi == null) {
                final LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi = inflater.inflate(R.layout.layout_item_pizza, parent, false);

                holder = new ViewHolder();

                holder.chkbSelecao = (CheckBox)vi.findViewById(R.id.chkbSelecao);
                holder.txtvItemSabor = (TextView)vi.findViewById(R.id.txtvItemSabor);
                holder.txtvIngredientes = (TextView)vi.findViewById(R.id.txtvIngredientes);
                holder.txtvValor = (TextView)vi.findViewById(R.id.txtvValor);

                holder.chkbSelecao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                        list.get(getPosition).setSelecionado(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    }
                });
                vi.setTag(holder);
                vi.setTag(R.id.chkbSelecao, holder.chkbSelecao);
                vi.setTag(R.id.txtvItemSabor, holder.txtvItemSabor);
                vi.setTag(R.id.txtvIngredientes, holder.txtvIngredientes);
                vi.setTag(R.id.txtvValor, holder.txtvValor);
            } else {
                holder = (ViewHolder)vi.getTag();
            }
            holder.chkbSelecao.setTag(position); // This line is important.

            holder.chkbSelecao.setChecked(list.get(position).isSelecionado());
            holder.txtvItemSabor.setText(list.get(position).getCardapio_item().getNome());
            holder.txtvIngredientes.setText(list.get(position).getCardapio_item().getDescricao());
            holder.txtvValor.setText(String.format("%.2f", (list.get(position).getCardapio_item().getValor())));

            return vi;
        }

}
