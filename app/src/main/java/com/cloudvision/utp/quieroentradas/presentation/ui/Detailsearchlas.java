package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.LastSearchdetail;
import com.cloudvision.utp.quieroentradas.domain.model.LastSearch;
import com.cloudvision.utp.quieroentradas.presentation.adapter.LastSearchdetailAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Detailsearchlas extends AppCompatActivity {
    private Query databaseReference;
    public LastSearchdetail lastSearchdetail;
    private List<LastSearchdetail> list = new ArrayList<>();
    private RecyclerView recyclerLastSearchdetail;
    private LastSearchdetailAdapter lastSearchdetailAdapter;
    private  RecyclerView.Adapter adapter;
    private DatabaseReference mFirebaseDatabase2 = FirebaseDatabase.getInstance().getReference("eventSearch");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_last_search_detail);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String getName = (String) bd.get("eventName");
        //String getDate= (String)  bd.get("eventDate");
        recyclerLastSearchdetail = (RecyclerView) findViewById(R.id.reciclerdetail);
        recyclerLastSearchdetail.setHasFixedSize(true);
        recyclerLastSearchdetail.setLayoutManager(new LinearLayoutManager(Detailsearchlas.this));

        //recyclerLastSearchdetail.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        mFirebaseDatabase2.orderByChild("groupName").equalTo(getName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1s : dataSnapshot.getChildren()){
                    LastSearchdetail lastSearchdetail = dataSnapshot1s.getValue(LastSearchdetail.class);
                    list.add(lastSearchdetail);
                }
                adapter = new LastSearchdetailAdapter(Detailsearchlas.this, list);
                recyclerLastSearchdetail.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }}





