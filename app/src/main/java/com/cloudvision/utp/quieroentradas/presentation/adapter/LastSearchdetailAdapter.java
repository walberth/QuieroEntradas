package com.cloudvision.utp.quieroentradas.presentation.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.LastSearchdetail;
import com.cloudvision.utp.quieroentradas.presentation.ui.Detailsearchlas;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;

import java.util.ArrayList;
import java.util.List;

public class LastSearchdetailAdapter extends RecyclerView.Adapter<LastSearchdetailAdapter.MyViewHolder> {
    private Context context;
    private  List<LastSearchdetail> lastSearchdetailList;
    public  LastSearchdetailAdapter(Context context, List<LastSearchdetail> lastSearchdetailList2){
        this.lastSearchdetailList = lastSearchdetailList2;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.lastsearchdetail_adapter, parent, false);
        MyViewHolder pvh = new MyViewHolder(itemView2);
        return pvh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LastSearchdetail lastSearchdetail2 = lastSearchdetailList.get(position);
        holder.groupname.setText(lastSearchdetail2.getEventDescription());
        holder.place.setText(lastSearchdetail2.getEventplacedirection());
//        Log.d("mensaje",lastSearchdetail2.getEventLocationidplace());

    }
    @Override
    public int getItemCount() {
        return lastSearchdetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView groupname, place;

        public MyViewHolder(View itemView) {
            super(itemView);
            groupname = (TextView) itemView.findViewById(R.id.detaileventdescription);
            place = (TextView) itemView.findViewById(R.id.detaileventplace);
        }
    }




}

