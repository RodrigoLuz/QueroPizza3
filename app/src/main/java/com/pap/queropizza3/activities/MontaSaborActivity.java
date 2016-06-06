package com.pap.queropizza3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pap.queropizza3.R;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.fragments.ComplementoFragment;
import com.pap.queropizza3.fragments.SaboresFragment;
import com.pap.queropizza3.models.TCardapioItem;
import com.pap.queropizza3.models.TItemTela;
import com.pap.queropizza3.models.TPedidoDetalhe;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MontaSaborActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    Map<String, List<TItemTela>> dados =  new HashMap<String, List<TItemTela>>();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monta_sabor);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_monta_sabor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return SaboresFragment.newInstance(position + 1);
                case 1:
                    return ComplementoFragment.newInstance(position + 2);
            }
            return null; //if you use default, you would not need to return null
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sabores";
                case 1:
                    return "Complemento";
            }
            return null;
        }
    }

    public void  btnContinuarClick(View view) {
        gravarLista(dados);
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
    }

    public void gravarLista(Map<String, List<TItemTela>> dados) {
        this.dados = dados;

        //Restaura id_pedido gravado
        SharedPreferences prefs = getSharedPreferences("pedido", MODE_PRIVATE);
        int id_pedido = prefs.getInt("id_pedido", -1);
        int id_item;

        AppSQLDao dbDao;
        dbDao = new AppSQLDao(getApplicationContext());
        List<String> keys = new ArrayList<String>(this.dados.keySet());

        for(int i = 0 ; i < keys.size(); i++) {

            for(int j = 0 ; j < this.dados.get(keys.get(i)).size(); j++) {
                int quant = this.dados.get(keys.get(i)).get(j).getQuantidade();
                if (quant > 0) {
                    TPedidoItem item = new TPedidoItem();
                    item.setId_pedido(id_pedido);
                    item.setQuantidade(quant);
                    item.setValor(this.dados.get(keys.get(i)).get(j).getCardapio_item().getValor());
                    id_item =  dbDao.inserirPedidoItem(item);

                    TPedidoDetalhe detalhe = new TPedidoDetalhe();
                    detalhe.setId_item(id_item);

                    TCardapioItem c = this.dados.get(keys.get(i)).get(j).getCardapio_item();
                    detalhe.setCardapio_item(c);
                    dbDao.inserirPedidoSubItem(detalhe);

                    // Log.d(TAG, String.valueOf(this.dados.get(keys.get(i)).get(j).getQuantidade()));
                }
            }
        }
    }

}
