package com.rigobertosl.nevergiveapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class FoodsActivity extends MainActivity {

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
        setContentView(R.layout.activity_foods);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_foods);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs_foods); //Layout donde ponemos los tabs
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);

        //Función para volver a la pantalla anterior
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FoodsActivity.this, MainActivity.class);
                //Para matar la actividad anterior
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //Para leer la nueva actividad (volver al main)
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_foods);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_foods);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    //Función para abrir el menu de opciones del app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_foods, menu);
        return true;
    }


    //Función para dar funcionalidades a cada item del menu del app bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_foods_visual | id == R.id.menu_foods_settings | id == R.id.menu_foods_edit) {
            Toast.makeText(getApplicationContext(),
                    item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_foods, container, false);

            //Menues de la pantalla de inicio para cada elemento
            final ImageButton desayunoOptions = (ImageButton) rootView.findViewById(R.id.desayuno_options);
            registerForContextMenu(desayunoOptions);
            desayunoOptions.setOnClickListener(this);

            final ImageButton tentempieOptions = (ImageButton) rootView.findViewById(R.id.tentempie_options);
            registerForContextMenu(tentempieOptions);
            tentempieOptions.setOnClickListener(this);

            final ImageButton comidaOptions = (ImageButton) rootView.findViewById(R.id.comida_options);
            registerForContextMenu(comidaOptions);
            comidaOptions.setOnClickListener(this);

            final ImageButton meriendaOptions = (ImageButton) rootView.findViewById(R.id.merienda_options);
            registerForContextMenu(meriendaOptions);
            meriendaOptions.setOnClickListener(this);

            final ImageButton cenaOptions = (ImageButton) rootView.findViewById(R.id.cena_options);
            registerForContextMenu(cenaOptions);
            cenaOptions.setOnClickListener(this);

            return rootView;
        }

        public void showMenu(View v) {
            PopupMenu popup = new PopupMenu(getActivity(), v);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //CON ESTO METEMOS UNA FUNCION A CADA COSO DEL MENU DEPENDIENDO DE LA ID
                /*if(item.getItemId()==R.id.open_foods) {
                    Intent intent = new Intent(MainActivity.this, FoodsActivity.class);
                    startActivity(intent);
                }*/
                    return true;
                }
            });// to implement on click event on items of menu
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_foods_elements, popup.getMenu());
            popup.show();
        }

        @Override
        public void onClick(View v) {
        Toast.makeText(getActivity(),
                "ImageButton is clicked!", Toast.LENGTH_SHORT).show();

            switch (v.getId()) {
                case R.id.desayuno_options: {
                    showMenu(v);
                    break;
                }
                case R.id.tentempie_options: {
                    showMenu(v);
                    break;
                }
                case R.id.comida_options: {
                    showMenu(v);
                    break;
                }
                case R.id.merienda_options: {
                    showMenu(v);
                    break;
                }
                case R.id.cena_options: {
                    showMenu(v);
                    break;
                }
                default: {
                    break;
                }
            }
        }
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
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 7;
        }
    }

    /*********** FUNCIONES DE LA PANTALLA DE COMIDAS ******************/


}
