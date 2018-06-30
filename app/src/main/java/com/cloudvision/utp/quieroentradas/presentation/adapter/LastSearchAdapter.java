package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.data.model.LastSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;

import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 07,June,2018
 */
public class LastSearchAdapter  extends SelectableAdapter<LastSearchAdapter.ViewHolder> {
    private static final String TAG = "LastSearchAdapter";
    private Context context;
    private List<LastSearch> eventSearchList;

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
            eventName.setText(lastSearch.getGroupName());
            eventDate.setText(lastSearch.getDateTimeSearched());
        }
    }

    class btnSeeEventDetail implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context.getApplicationContext(), "Yo want to review the detail", Toast.LENGTH_LONG).show();

            Log.d(TAG, "onClick: Go to the other fragment");
        }
    }
}
