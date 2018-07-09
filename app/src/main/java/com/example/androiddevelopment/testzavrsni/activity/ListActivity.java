package com.example.androiddevelopment.testzavrsni.activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.testzavrsni.R;
import com.example.androiddevelopment.testzavrsni.about.AboutDialog;
import com.example.androiddevelopment.testzavrsni.db.ORMLightHelper;
import com.example.androiddevelopment.testzavrsni.db.model.Prijava;
import com.example.androiddevelopment.testzavrsni.preferences.Preferencess;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;
import java.util.prefs.Preferences;

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

    private void showStatusMesage(String message){
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle("Test");
        mBuilder.setContentText(message);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_add);

        mBuilder.setLargeIcon(bm);
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_new_stavka:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_prijava_layout);

                Button add = (Button) dialog.findViewById(R.id.add_prijava);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText naziv = (EditText) dialog.findViewById(R.id.prijava_naziv);
                        EditText opis = (EditText) dialog.findViewById(R.id.prijava_opis);
                        EditText status = (EditText) dialog.findViewById(R.id.prijava_status);
                       // EditText datum = (EditText) dialog.findViewById(R.id.prijava_datum);
                       // EditText stavka = (EditText) dialog.findViewById(R.id.stavka_naslov);

                        Prijava a = new Prijava();
                        a.setmIme(naziv.getText().toString());
                        a.setmOpis(opis.getText().toString());
                        a.setmStatus(status.getText().toString());
                      //  a.setmDatum(datum.getText().toString());

                        try {
                            getDatabaseHelper().getPrijavaDao().create(a);

                            boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
                            boolean statuss = prefs.getBoolean(NOTIF_STATUS, false);

                            if (toast){
                                Toast.makeText(ListActivity.this, "Nova prijava je dodata", Toast.LENGTH_SHORT).show();
                            }

                            if (statuss){
                                showStatusMesage("Nova prijava je dodata");
                            }

                            refresh();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();

                    }
                });

                Button back = (Button) dialog.findViewById(R.id.back);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

                break;
            case R.id.about:

                AlertDialog alertDialog = new AboutDialog(this).prepareDialog();
                alertDialog.show();
                break;
            case R.id.preferences:
                startActivity(new Intent(ListActivity.this, Preferencess.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public ORMLightHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, ORMLightHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // nakon rada sa bazo podataka potrebno je obavezno
        //osloboditi resurse!
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}