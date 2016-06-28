package com.pap.queropizza3.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.pap.queropizza3.R;
import com.pap.queropizza3.adapters.TCheckBebidaAdapter;
import com.pap.queropizza3.dao.AppSQLDao;
import com.pap.queropizza3.models.TPedidoItem;

import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check, menu);
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
     * A placeholder fragment containing a simple view.
     */

    public static class CheckPizzaFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "1";

        public CheckPizzaFragment() {
        }

        public static CheckPizzaFragment newInstance(int sectionNumber) {
            CheckPizzaFragment fragment = new CheckPizzaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_check_pizza, container, false);
            ListView listView = (ExpandableListView) rootView.findViewById(R.id.lstvCheckoutPizza);

            AppSQLDao dbDao;
            dbDao = new AppSQLDao(getActivity().getApplicationContext());
            List<TPedidoItem> itens = new ArrayList<TPedidoItem>();
            itens = dbDao.listaTodosPedidoItem(1); // grupo pizzas

 /*           if (itens.size() == 0){
                Button btnFinalizarChk = (Button) rootView.findViewById(R.id.btnFinalizarChk);
                btnFinalizarChk.setVisibility(View.GONE);
            }
*/
            ArrayAdapter<TPedidoItem> adapter = new TCheckBebidaAdapter(getActivity(), 0, itens);
            listView.setAdapter(adapter);

            return rootView;
        }
    }

    public static class CheckBebidaFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "2";

        public CheckBebidaFragment() {
        }

        public static CheckBebidaFragment newInstance(int sectionNumber) {
            CheckBebidaFragment fragment = new CheckBebidaFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_check_bebida, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.lstvCheckoutBebida);

            AppSQLDao dbDao;
            dbDao = new AppSQLDao(getActivity().getApplicationContext());
            List<TPedidoItem> itens = new ArrayList<TPedidoItem>();
            itens = dbDao.listaTodosPedidoItem(2); // grupo bebidas

 /*           if (itens.size() == 0){
                Button btnFinalizarChk = (Button) rootView.findViewById(R.id.btnFinalizarChk);
                btnFinalizarChk.setVisibility(View.GONE);
            }
*/
            ArrayAdapter<TPedidoItem> adapter = new TCheckBebidaAdapter(getActivity(), 0, itens);
            listView.setAdapter(adapter);

            return rootView;
        }
    }

    public void btnAdicionarItensClick(View view) {
        Intent it = new Intent(this, GrupoActivity.class);
        startActivity(it);
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
                    return CheckPizzaFragment.newInstance(position + 1);
                case 1:
                    return CheckBebidaFragment.newInstance(position + 2);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pizzas";
                case 1:
                    return "Bebidas";
            }
            return null;
        }
    }
}
