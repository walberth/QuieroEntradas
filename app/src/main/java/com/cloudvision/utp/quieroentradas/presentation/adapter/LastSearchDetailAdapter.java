package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.domain.model.LastEventSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 03,July,2018
 */
public class LastSearchDetailAdapter extends SelectableAdapter<LastSearchDetailAdapter.ViewHolder> {
    private static final String TAG = "LastSearchDetailAdapter";
    private Context context;
    private List<LastEventSearch> lastEventSearchList;

    public LastSearchDetailAdapter(RecyclerView recyclerView, List<LastEventSearch> lastEventSearchList, Context context) {
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

        public void bind(LastEventSearch lastEventSearch){
            eventLastSearchName.setText(lastEventSearch.getEventName());
        }
    }

    class btnSeeLastSearchEvent implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Se the event detail", Toast.LENGTH_LONG).show();

        }
    }
}
