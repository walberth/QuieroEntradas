package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;

import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 07,June,2018
 */
public class LastSearchAdapter  extends SelectableAdapter<LastSearchAdapter.ViewHolder> {
    private Context context;
    private List<EventSearch> eventSearchList;

    public LastSearchAdapter(RecyclerView recyclerView, List<EventSearch> eventSearchList, Context context) {
        super(recyclerView);
        this.context = context;
        this.eventSearchList = eventSearchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return eventSearchList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
