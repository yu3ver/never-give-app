package com.rigobertosl.nevergiveapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
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

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class FoodsActivity extends MainActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    String tableName;
    String tableDays;
    private static DataBaseContract dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs); //Layout donde ponemos los tabs
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
                openDialog(view);
            }
        });

        ListView item_list = (ListView)findViewById(R.id.list_item);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

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

    public void openDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogLayout = getLayoutInflater().inflate(R.layout.popup_new_table, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(dialogLayout);
        dialog.setTitle("Nueva comida");
        dialog.show();

        final Button continuar = (Button) dialogLayout.findViewById(R.id.button_continue);
        final Button cancelar = (Button) dialogLayout.findViewById(R.id.button_cancel);

        final EditText tableNameEditText = (EditText) dialogLayout.findViewById(R.id.table_name);
        final EditText tableDaysEditText = (EditText) dialogLayout.findViewById(R.id.table_days);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableName = tableNameEditText.getText().toString();
                tableDays = tableDaysEditText.getText().toString();
                if (tableName.matches("") || tableDays.matches("")) {
                    Toast.makeText(FoodsActivity.this,
                            "Necesitas rellenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    dialog.cancel();
                    saveData(view);
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    /** En esta función se guradan los datos en la base de datos **/
    public void saveData(View v){

        DataBaseContract.DataBaseHelper nuevaComida = new DataBaseContract.DataBaseHelper(this);
        SQLiteDatabase db = nuevaComida.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseContract.DataBaseEntryFoods.COLUMN_NAME, tableName);
        values.put(DataBaseContract.DataBaseEntryFoods.COLUMN_DAYS, tableDays);

        long newRowId = db.insert(DataBaseContract.DataBaseEntryFoods.TABLE_NAME, null, values);
        Toast.makeText(this, "Datos de la tabla guardados", Toast.LENGTH_SHORT).show();
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }

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

            String[] comidas = {"Desayuno", "Tentempié", "Comida", "Merienda", "Cena"};
            ListAdapter listAdapter = new CustomFoodAdapter(getContext(), comidas);
            ListView lista = (ListView) rootView.findViewById(R.id.list_item);
            lista.setAdapter(listAdapter);

            // Para que se haga el scroll correctamente ocultando el toolbar
            ViewCompat.setNestedScrollingEnabled(lista, true);

            return rootView;
        }

    }

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

// TODO: hay que implementar todos los menús después de usar las listas