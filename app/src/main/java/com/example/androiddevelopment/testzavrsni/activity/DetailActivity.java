package com.example.androiddevelopment.testzavrsni.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

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

        final ListView listView = (ListView) findViewById(R.id.prijava_list);

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
                    Toast.makeText(this, m.getmIme()+" "+m.getmOpis()+" "+m.getmStatus(), Toast.LENGTH_SHORT).show();

                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
