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
import android.widget.Toast;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.domain.model.EventsFound;
import com.cloudvision.utp.quieroentradas.domain.model.LastEventSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.EventsFoundDetailFragment;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 03,July,2018
 */
public class LastSearchDetailAdapter extends SelectableAdapter<LastSearchDetailAdapter.ViewHolder> {
    private static final String TAG = "LastSearchDetailAdapter";
    private Context context;
    private List<EventsFound> lastEventSearchList;
    private String latitud;
    private String longitud;
    private String eventName;
    private String eventGroup;
    private String idLocation;

    public LastSearchDetailAdapter(RecyclerView recyclerView, List<EventsFound> lastEventSearchList, Context context) {
        super(recyclerView);
        this.context = context;
        this.lastEventSearchList = lastEventSearchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchedeventsfound, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(lastEventSearchList.get(getItemCount() - 1 - position));
        holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return lastEventSearchList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder {
        private ImageView eventLastSearchImage;
        private TextView eventLastSearchName;
        private Button btnSeeLastSearchEvent;

        public ViewHolder(View itemView) {
            super(itemView);

            eventLastSearchImage = itemView.findViewById(R.id.eventLastSearchImage);
            eventLastSearchName = itemView.findViewById(R.id.eventLastSearchName);
            btnSeeLastSearchEvent = itemView.findViewById(R.id.btnSeeLastSearchEvent);

            btnSeeLastSearchEvent.setOnClickListener(new btnSeeLastSearchEvent());
        }

        public void bind(EventsFound lastEventSearch){
            eventLastSearchName.setText(lastEventSearch.getEventName());
            idLocation = lastEventSearch.getEventLocationId();
            latitud = lastEventSearch.getLatitud();
            longitud = lastEventSearch.getLongitud();
            eventName = lastEventSearch.getEventName();
            eventGroup = lastEventSearch.getEventGroup();
        }
    }

    class btnSeeLastSearchEvent implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle data = new Bundle();
            data.putString("idLocation", idLocation);
            data.putString("latitud", latitud);
            data.putString("longitud", longitud);
            data.putString("eventName", eventName);
            data.putString("eventGroup", eventGroup);
            android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            EventsFoundDetailFragment eventsFoundDetailFragment = new EventsFoundDetailFragment();
            eventsFoundDetailFragment.setArguments(data);
            fragmentTransaction.replace(R.id.content, eventsFoundDetailFragment).commit();

        }
    }
}
