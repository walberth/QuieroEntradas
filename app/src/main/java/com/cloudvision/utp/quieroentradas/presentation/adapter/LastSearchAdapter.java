package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.domain.model.LastSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.LastSearchDetailFragment;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 07,June,2018
 */
public class LastSearchAdapter  extends SelectableAdapter<LastSearchAdapter.ViewHolder> {
    private static final String TAG = "LastSearchAdapter";
    private Context context;
    private List<LastSearch> eventSearchList;
    private String eventGroupSearch;
    private String eventTimeSearch;
    private String keyUserImageSearch;

    public LastSearchAdapter(RecyclerView recyclerView, List<LastSearch> eventSearchList, Context context) {
        super(recyclerView);
        this.context = context;
        this.eventSearchList = eventSearchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lastsearch, parent,false);
        return new LastSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventSearchList.get(getItemCount() - 1 - position));
    }

    @Override
    public int getItemCount() {
        return eventSearchList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        private ImageView eventImage;
        private TextView eventName, eventDate;
        private Button btnEventDetail;


        public ViewHolder(View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            btnEventDetail = itemView.findViewById(R.id.btnEventDetail);

            btnEventDetail.setOnClickListener(new btnSeeEventDetail());
        }

        public void bind(LastSearch lastSearch){
            Glide.with(context).load(lastSearch.getPictureSearched()).into(eventImage);
            eventName.setText(lastSearch.getGroupName());
            eventDate.setText(convertTime( Long.parseLong(lastSearch.getDateTimeSearched())));
            eventTimeSearch = lastSearch.getDateTimeSearched();
            eventGroupSearch = lastSearch.getGroupName();
            keyUserImageSearch = lastSearch.getKeyUserImageSearch();
        }
    }

    class btnSeeEventDetail implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle data = new Bundle();
            data.putString("eventTimeSearch", convertTime(Long.parseLong(eventTimeSearch)));
            data.putString("eventGroupSearch", eventGroupSearch);
            data.putString("keyUserImageSearch", keyUserImageSearch);
            android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LastSearchDetailFragment lastSearchDetailFragment = new LastSearchDetailFragment();
            lastSearchDetailFragment.setArguments(data);
            fragmentTransaction.replace(R.id.content, lastSearchDetailFragment).commit();
        }
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }
}
