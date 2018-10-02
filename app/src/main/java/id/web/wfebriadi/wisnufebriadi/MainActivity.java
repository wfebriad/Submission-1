package id.web.wfebriadi.wisnufebriadi;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<FilmItems>> {

    ListView listView;
    FilmAdapter adapter;
    EditText editFilm;
    Button buttonCari;

    static final String API_KEY = "70bde5b712121097b11c98656b22b1d4";
    static final String SEARCH_FILM = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new FilmAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView)findViewById(R.id.listView);

        listView.setAdapter(adapter);

        editFilm = (EditText)findViewById(R.id.edit_nama_film);
        buttonCari =(Button)findViewById(R.id.btn_cari);

        buttonCari.setOnClickListener(myListener);

        String film = editFilm.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_FILM, film);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public Loader<ArrayList<FilmItems>> onCreateLoader(int id, Bundle args) {
        String listFilm = "";
        if (args != null){
            listFilm = args.getString(SEARCH_FILM);
        }
        return new MyAsyncTaskLoader(this, listFilm);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilmItems>> loader, ArrayList<FilmItems> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilmItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener myListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            String film = editFilm.getText().toString();

            if (TextUtils.isEmpty(film))return;;

            Bundle bundle = new Bundle();
            bundle.putString(SEARCH_FILM, film);
            getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}
