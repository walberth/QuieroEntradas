package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by Ronald Estela on 07,June,2018
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private Context context;
    private ArrayList<String> descriptionlist;
    private ArrayList<String> imagelist;
    private ArrayList<String> namelist;
    private ArrayList<String> eventTimeList;

    class SearchViewHolder extends  RecyclerView.ViewHolder{
        ImageView eventImage;
        TextView description, name, eventTime;

        SearchViewHolder(View itemView) {
            super(itemView);
            eventImage = itemView.findViewById(R.id.imgPictureSearched);
            description = itemView.findViewById(R.id.eventDescription);
            name = itemView.findViewById(R.id.eventArtistName);
            eventTime = itemView.findViewById(R.id.eventTime);

        }
    }

    public SearchAdapter(Context context, ArrayList<String> descriptionlist, ArrayList<String> imagelist, ArrayList<String> namelist, ArrayList<String> eventTimeList) {
        this.context = context;
        this.descriptionlist = descriptionlist;
        this.imagelist = imagelist;
        this.namelist = namelist;
        this.eventTimeList = eventTimeList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchitems, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        holder.description.setText(descriptionlist.get(position));
        holder.name.setText(namelist.get(position));
        holder.eventTime.setText(eventTimeList.get(position));
        //Glide.with(context).load(imagelist.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(holder.eventImage);
        //  Glide.with(context).asBitmap().load(imagelist.get(position)).placeholder(R.mipmap.ic_launcher_round).into(holder.eventImage);
    }

    @Override
    public int getItemCount() {
        return descriptionlist.size();
    }
}
