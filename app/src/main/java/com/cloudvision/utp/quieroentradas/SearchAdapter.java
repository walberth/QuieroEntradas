package com.cloudvision.utp.quieroentradas;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    Context context;
    ArrayList<String> descriptionlist;
    ArrayList<String> imagelist;
    ArrayList<String> namelist;
    ArrayList<String> statuslist;
    class SearchViewHolder extends  RecyclerView.ViewHolder{
        ImageView eventimage;
        TextView description, name, status;
        public SearchViewHolder(View itemView) {
            super(itemView);
            eventimage= (ImageView) itemView.findViewById(R.id.imageView2);
            description= (TextView) itemView.findViewById(R.id.textView2);
            name= (TextView) itemView.findViewById(R.id.textView3);
            status= (TextView) itemView.findViewById(R.id.textView4);

        }
    }
    public SearchAdapter(Context context, ArrayList<String> descriptionlist, ArrayList<String> imagelist, ArrayList<String> namelist, ArrayList<String> statuslist) {
        this.context = context;
        this.descriptionlist = descriptionlist;
        this.imagelist = imagelist;
        this.namelist = namelist;
        this.statuslist = statuslist;
    }


    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder( SearchViewHolder holder, int position) {

        holder.description.setText(descriptionlist.get(position));
        holder.name.setText(namelist.get(position));
        holder.status.setText(statuslist.get(position));
        Glide.with(context).load(imagelist.get(position)).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(holder.eventimage);
      //  Glide.with(context).asBitmap().load(imagelist.get(position)).placeholder(R.mipmap.ic_launcher_round).into(holder.eventimage);
    }



    @Override
    public int getItemCount() {
        return descriptionlist.size();
    }
}
