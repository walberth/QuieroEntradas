package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.presentation.adapter.StadisticAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private DatabaseReference databaseReference;
    private ArrayList<PieEntry> pieEntries;
    private RecyclerView stadisticsRecyclerView;
    private StadisticAdapter stadisticAdapter;
    private FirebaseUser user;

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

        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.nav_item_ranking));
        databaseReference = FirebaseDatabase.getInstance().getReference();
        pieEntries = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        stadisticsRecyclerView = view.findViewById(R.id.stadisticsRecyclerView);
        stadisticsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setRecyclerView();

        databaseReference.child("eventSearch").addListenerForSingleValueEvent(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  Set<String> totalGroups = new HashSet<>();
                  List<String> totalEvents = new ArrayList<>();

                  for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                      if(snapshot.child("idUser").getValue(String.class).equals(user.getUid())) {
                          totalGroups.add(snapshot.child("groupName").getValue(String.class));
                          totalEvents.add(snapshot.child("groupName").getValue(String.class));
                      }
                  }

                  for(String names : totalGroups) {
                      pieEntries.add(new PieEntry(Collections.frequency(totalEvents, names), names));
                  }

                  stadisticAdapter.notifyDataSetChanged();
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
        });


    }

    public void setRecyclerView(){
        stadisticAdapter = new StadisticAdapter(stadisticsRecyclerView, pieEntries, getContext());
        stadisticsRecyclerView.setHasFixedSize(true);
        stadisticsRecyclerView.setAdapter(stadisticAdapter);
    }
}
