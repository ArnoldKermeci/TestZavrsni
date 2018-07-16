package com.example.androiddevelopment.testzavrsni.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.androiddevelopment.testzavrsni.R;
import com.example.androiddevelopment.testzavrsni.db.ORMLightHelper;
import com.example.androiddevelopment.testzavrsni.db.model.Prijava;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

import static com.example.androiddevelopment.testzavrsni.activity.ListActivity.NOTIF_STATUS;
import static com.example.androiddevelopment.testzavrsni.activity.ListActivity.NOTIF_TOAST;
import static com.example.androiddevelopment.testzavrsni.activity.ListActivity.PRIJAVA_KEY;

public class DetailActivity  extends AppCompatActivity {
    private ORMLightHelper databaseHelper;
    private SharedPreferences prefs;
    private Prijava prijava;

    private EditText ime;
    private EditText opis;
    private EditText status;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

//        if(toolbar != null) {
//            setSupportActionBar(toolbar);
//        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int key = getIntent().getExtras().getInt(PRIJAVA_KEY);

        try {
            prijava = getDatabaseHelper().getPrijavaDao().queryForId(key);

            ime = (EditText) findViewById(R.id.naziv_prijava);
            opis = (EditText) findViewById(R.id.opis_prijava);
            status = (EditText) findViewById(R.id.status_prijava);


            ime.setText(prijava.getmIme());
            opis.setText(prijava.getmOpis());
           status.setText(prijava.getmStatus());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView listView = (ListView) findViewById(R.id.stavke_prijava);

        try {
            List<Prijava> list = getDatabaseHelper().getPrijavaDao().queryBuilder()
                    .where()
                    .eq(Prijava.  FIELD_NAME_USER, prijava.getmId())
                    .query();

            ListAdapter adapter = new ArrayAdapter<>(this, R.layout.list_item, list);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Prijava m = (Prijava) listView.getItemAtPosition(position);
                    Toast.makeText(DetailActivity.this, m.getmIme()+" "+m.getmOpis()+" "+m.getmStatus(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        ListView listview = (ListView) findViewById(R.id.prijava_list);

        if (listview != null){
            ArrayAdapter<Prijava> adapter = (ArrayAdapter<Prijava>) listview.getAdapter();

            if(adapter!= null)
            {
                try {
                    adapter.clear();
                    List<Prijava> list = getDatabaseHelper().getPrijavaDao().queryBuilder()
                            .where()
                            .eq(Prijava.FIELD_NAME_USER, prijava.getmId())
                            .query();

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

    private void showMessage(String message){
        boolean toast = prefs.getBoolean(NOTIF_TOAST, false);
        boolean status = prefs.getBoolean(NOTIF_STATUS, false);

        if (toast){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (status){
            showStatusMesage(message);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
