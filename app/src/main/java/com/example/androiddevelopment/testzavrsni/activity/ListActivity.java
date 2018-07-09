package com.example.androiddevelopment.testzavrsni.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.androiddevelopment.testzavrsni.R;
import com.example.androiddevelopment.testzavrsni.db.ORMLightHelper;
import com.example.androiddevelopment.testzavrsni.db.model.Prijava;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ORMLightHelper databaseHelper;
    private SharedPreferences prefs;

    public static String PRIJAVA_KEY = "PRIJAVA_KEY";
    public static String NOTIF_TOAST = "notif_toast";
    public static String NOTIF_STATUS = "notif_statis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final ListView listView = (ListView) findViewById(R.id.prijava_list);

        try {
            List<Prijava> list = getDatabaseHelper().getPrijavaDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(ListActivity.this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Prijava p = (Prijava) listView.getItemAtPosition(position);

                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                    intent.putExtra(PRIJAVA_KEY, p.getmId());
                    startActivity(intent);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.prijava_list);

        if (listview != null){
            ArrayAdapter<Prijava> adapter = (ArrayAdapter<Prijava>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Prijava> list = getDatabaseHelper().getPrijavaDao().queryForAll();

                    adapter.addAll(list);

                    adapter.notifyDataSetChanged();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }






/////////////////////
        public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }
}