package id.web.wfebriadi.wisnufebriadi;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyAsyncTaskLoader extends AsyncTaskLoader<ArrayList<FilmItems>> {

    private ArrayList<FilmItems> mData;
    private boolean mHasResult = false;
    private String mListFilm;

    private static final String API_KEY = "70bde5b712121097b11c98656b22b1d4";
    private static final String URL = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=";

    public MyAsyncTaskLoader(final Context context, String listFilm) {
        super(context);

        onContentChanged();
        this.mListFilm = listFilm;
    }

    @Override
    protected void onStartLoading(){
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }
    @Override
    public void deliverResult(final ArrayList<FilmItems> data){
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }
    @Override
    protected void onReset(){
        super.onReset();
        onStopLoading();
        if (mHasResult){
            //onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    //private static final String API_KEY = "70bde5b712121097b11c98656b22b1d4";

    @Override
    public ArrayList<FilmItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<FilmItems> filmItemses = new ArrayList<>();
        client.get(URL + mListFilm, new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                super.onStart();
                setUseSynchronousMode(true);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //Log.e(TAG, "onSuccess: Success");
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("result");

                    for (int i = 0; i < list.length(); i++){
                        JSONObject film = list.getJSONObject(i);
                        FilmItems filmItems = new FilmItems(film);
                        filmItemses.add(filmItems);
                        //Log.e(TAG, "onSuccess: Loop");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
               // Log.e(TAG, "onFailure: Failed" +error);
            }
        });

        return filmItemses;
    }
}
