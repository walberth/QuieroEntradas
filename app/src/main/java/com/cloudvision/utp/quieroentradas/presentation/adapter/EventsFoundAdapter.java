package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.domain.model.EventsFound;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.EventsFoundDetailFragment;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 24,June,2018
 */
public class EventsFoundAdapter extends SelectableAdapter<EventsFoundAdapter.ViewHolder> {
    private static final String TAG = "EventsFoundAdapter";
    private Context context;
    private List<EventsFound> eventsFoundList;
    private String idLocation;
    private String latitud;
    private String longitud;
    private String eventName;
    private String eventGroup;
    private String keyEventSearch;
    private String eventPlaceDescription;
    private String eventDescription;

    public EventsFoundAdapter(RecyclerView recyclerView, List<EventsFound> eventsFoundList, Context context) {
        super(recyclerView);
        this.context = context;
        this.eventsFoundList = eventsFoundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventsfound, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventsFoundList.get(getItemCount() - 1 - position));
        holder.getAdapterPosition();

        //EventsFound eventsFound = eventsFoundList.get(position);
        //idLocation = eventsFound.getEventLocationId();
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
            idLocation = eventsFound.getEventLocationId();
            latitud = eventsFound.getLatitud();
            longitud = eventsFound.getLongitud();
            eventName = eventsFound.getEventName();
            eventGroup = eventsFound.getEventGroup();
            keyEventSearch = eventsFound.getEventId();
            eventPlaceDescription = eventsFound.getEventPlaceDescription();
            eventDescription = eventsFound.getEventDescription();
        }
    }

    class btnSeeEventFound implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle data = new Bundle();
            data.putString("idLocation", idLocation);
            data.putString("latitud", latitud);
            data.putString("longitud", longitud);
            data.putString("eventName", eventName);
            data.putString("eventGroup", eventGroup);
            data.putString("keyEventSearch", keyEventSearch);
            data.putString("eventPlaceDescription", eventPlaceDescription);
            data.putString("eventDescription", eventDescription);
            android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            EventsFoundDetailFragment eventsFoundDetailFragment = new EventsFoundDetailFragment();
            eventsFoundDetailFragment.setArguments(data);
            fragmentTransaction.replace(R.id.content, eventsFoundDetailFragment).commit();
        }
    }
}
