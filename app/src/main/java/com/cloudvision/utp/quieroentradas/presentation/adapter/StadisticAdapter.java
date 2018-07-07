package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

/**
 * Created by Walberth Gutierrez Telles on 06,July,2018
 */
public class StadisticAdapter extends SelectableAdapter<StadisticAdapter.ViewHolder> {
    private Context context;
    private PieChart pieChart;
    private ArrayList<PieEntry> pieEntryList;

    public StadisticAdapter(RecyclerView recyclerView, ArrayList<PieEntry> pieEntryList, Context context) {
        super(recyclerView);
        this.context = context;
        this.pieEntryList = pieEntryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stadistics, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return pieEntryList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(pieEntryList.get(getItemCount() - 1 - position));
        holder.getAdapterPosition();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);

            pieChart = itemView.findViewById(R.id.pieChart);

            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(true);
            pieChart.setExtraOffsets(5, 10, 5, 5);
            pieChart.setDragDecelerationFrictionCoef(0.95f);
            pieChart.setDrawHoleEnabled(false);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.setTransparentCircleRadius(61f);
        }

        public void bind(PieEntry pieEntry) {
            PieDataSet dataSet = new PieDataSet(pieEntryList, "Preferencia Musical");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            pieChart.setData(data);
        }
    }
}
