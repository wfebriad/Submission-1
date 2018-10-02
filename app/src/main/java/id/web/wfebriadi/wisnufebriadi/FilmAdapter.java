package id.web.wfebriadi.wisnufebriadi;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FilmAdapter extends BaseAdapter {

    private ArrayList<FilmItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private static final String URL_IMG = "http://image.tmdb.org/t/p/w185";

    public FilmAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setData (ArrayList<FilmItems> items){
        mData = items;
        notifyDataSetChanged();
    }
    public void addItem(final FilmItems item){
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData(){
        mData.clear();
    }
    @Override
    public int getItemViewType(int position){
        return 0;
    }
    @Override
    public int getViewTypeCount(){
        return 1;
    }

    @Override
    public int getCount() {
        if (mData == null) return 0;
        return mData.size();
    }
    @Override
    public FilmItems getItem(int position){
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.film_items, null);
            holder.tvNamaFilm = (TextView)convertView.findViewById(R.id.text_nama_film);
            holder.tvDeskripsi = (TextView)convertView.findViewById(R.id.text_detail);
            holder.tvTanggalRilis = (TextView)convertView.findViewById(R.id.text_tanggal_rilis);
            holder.imagePoster = (ImageView)convertView.findViewById(R.id.image_poster);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvNamaFilm.setText(mData.get(position).getTitle());
        holder.tvDeskripsi.setText(mData.get(position).getOverview());
        String tvTanggalRilis = mData.get(position).getRelease();
        SimpleDateFormat format_tanggal = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format_tanggal.parse(tvTanggalRilis);
            SimpleDateFormat new_format_tanggal = new SimpleDateFormat("EEEE, dd/MM/yyyy");
            String tanggal_rilis = new_format_tanggal.format(date);
            holder.tvTanggalRilis.setText(tanggal_rilis);
        } catch (Exception e){
            e.printStackTrace();
        }
        Glide.with(this.context).load(URL_IMG + mData.get(position).getPoster()).into(holder.imagePoster);
        //Log.e(TAG, "getView: get");
        return convertView;
    }

    private static class ViewHolder {
        TextView tvNamaFilm;
        TextView tvDeskripsi;
        TextView tvTanggalRilis;
        ImageView imagePoster;
    }
}
