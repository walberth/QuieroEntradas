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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.EventsFound;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;

import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 24,June,2018
 */
public class EventsFoundAdapter extends SelectableAdapter<EventsFoundAdapter.ViewHolder> {
    private static final String TAG = "EventsFoundAdapter";
    private Context context;
    private List<EventsFound> eventsFoundList;

    public EventsFoundAdapter(RecyclerView recyclerView, List<EventsFound> eventsFoundList, Context context) {
        super(recyclerView);
        this.context = context;
        this.eventsFoundList = eventsFoundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventsfound, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventsFoundList.get(getItemCount() - 1 - position));
    }

    @Override
    public int getItemCount() {
        return eventsFoundList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        private ImageView eventImageFound;
        private TextView eventNameFound, eventLocationFound;
        private Button btnSeeEventFound;

        public ViewHolder(View itemView) {
            super(itemView);

            eventImageFound = itemView.findViewById(R.id.eventImageFound);
            eventNameFound = itemView.findViewById(R.id.eventNameFound);
            eventLocationFound = itemView.findViewById(R.id.eventLocationFound);
            btnSeeEventFound = itemView.findViewById(R.id.btnSeeEventFound);

            btnSeeEventFound.setOnClickListener(new btnSeeEventFound());
        }

        public void bind(EventsFound eventsFound){
            eventNameFound.setText(eventsFound.getEventName());
            eventLocationFound.setText(eventsFound.getEventLocation());
        }
    }

    class btnSeeEventFound implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick: Go to the other fragment");
        }
    }
}
