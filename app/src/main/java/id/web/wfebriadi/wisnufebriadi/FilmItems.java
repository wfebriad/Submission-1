package id.web.wfebriadi.wisnufebriadi;

import android.util.Log;

import org.json.JSONObject;

import static android.support.constraint.Constraints.TAG;

public class FilmItems {
    private int id;
    private String title, overview, release, poster;

    public FilmItems(JSONObject object){

        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String overview = object.getString("overview");
            String release = object.getString("releases");
            String poster = object.getString("poster_path");
            this.id = id;
            this.title = title;
            this.overview = overview;
            this.release = release;
            this.poster = poster;
            //Log.e(TAG, "FilmItems : try");

        } catch (Exception e){
            e.printStackTrace();
            //Log.e(TAG, "FilmItems : error");
        }
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getRelease(){
        return release;
    }
    public void setRelease(String release){
        this.release = release;
    }

    public String getPoster(){
        return poster;
    }
    public void setPoster(String poster){
        this.poster = poster;
    }
}
