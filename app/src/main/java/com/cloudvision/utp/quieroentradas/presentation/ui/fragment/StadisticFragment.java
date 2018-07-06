package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudvision.utp.quieroentradas.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.frequency;

/**
 * Created by Walberth Gutierrez Telles on 06,June,2018
 */
public class StadisticFragment extends Fragment {
    private PieChart pieChart;
    private DatabaseReference databaseReference;

    public StadisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stadistic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getActivity().setTitle(getResources().getString(R.string.nav_item_ranking));
        pieChart = view.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        final ArrayList<PieEntry> pieEntries = new ArrayList<>();


        /*databaseReference.child("eventSearch").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  Set<String> totalGroups = new HashSet<>();
                  List<String> totalEvents = new ArrayList<>();
                  int counterElements = 0;

                  for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                      totalGroups.add(snapshot.child("groupName").getValue(String.class));
                      totalEvents.add(snapshot.child("groupName").getValue(String.class));
                      counterElements++;
                  }

                  for(String names : totalGroups) {
                      pieEntries.add(new PieEntry((float)Collections.frequency(totalEvents, names), names));
                  }

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
        });*/


        pieEntries.add(new PieEntry(34f, "Salsa"));
        pieEntries.add(new PieEntry(23f, "Electro"));
        pieEntries.add(new PieEntry(14f, "Rock"));/*
        pieEntries.add(new PieEntry(35, "Perreo"));
        pieEntries.add(new PieEntry(40, "Cumbia"));
        pieEntries.add(new PieEntry(23, "Chicha"));*/

        PieDataSet dataSet = new PieDataSet(pieEntries, "Preferencia Musical");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
    }
}
