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
    private List<LastSearchdetail> lastSearchListdetailLis = new ArrayList<>();
    private RecyclerView recyclerLastSearchdetail;
    private LastSearchdetailAdapter lastSearchdetailAdapter;
    private DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("eventSearch");
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
        lastSearchdetailAdapter = new LastSearchdetailAdapter(lastSearchListdetailLis);
         //recyclerLastSearchdetail.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerLastSearchdetail.setLayoutManager(mLayoutManager);
        recyclerLastSearchdetail.setItemAnimator(new DefaultItemAnimator());
        recyclerLastSearchdetail.setAdapter(lastSearchdetailAdapter);
        getLastSearchDetailsItems(getName);
       // databaseReference= FirebaseDatabase.getInstance().getReference("eventSearch").equalTo("groupName",getName).equalTo("dateTimeSearch",getLocal);
      // String desc= databaseReference.toString();








        /*btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FirstViewActivity.this, LoginActivity.class));
            }
        });*/
    }


    private void getLastSearchDetailsItems(String getName) {
        mFirebaseDatabase.orderByChild("groupName").equalTo("Nirvana").addChildEventListener(new ChildEventListener()

        {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Log.d("coment", "ez");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String eventName =  snapshot.child("eventName").getValue(String.class);
                        String eventLocationidplace =  snapshot.child("idPlace").getValue(String.class);
                        String eventPicture =  snapshot.child("eventPicture").getValue(String.class);
                        String eventId =  snapshot.child("idEvent").getValue(String.class);
                        String eventgroupName =  snapshot.child("groupName").getValue(String.class);
                        String idUser =  snapshot.child("idUser").getValue(String.class);
                        String eventDescription =  snapshot.child("eventDescription").getValue(String.class);
                        String eventDate =  snapshot.child("eventDate").getValue(String.class);
                        Long eventdateTimeSearch =  snapshot.child("dateTimeSearch").getValue(Long.class);


                        lastSearchdetail = new LastSearchdetail( eventName, eventLocationidplace, eventPicture, eventId,eventgroupName, idUser, eventDescription , eventDate, eventdateTimeSearch );
                        lastSearchListdetailLis.add(lastSearchdetail);

                        lastSearchdetailAdapter.notifyDataSetChanged();
                       /* search.setEventName(eventName);
                        search.setEventLocationidplace(eventLocationidplace);
                        search.setEventPicture(eventPicture);
                        search.setEventId(eventId);
                        search.setEventgroupName(eventgroupName);
                        search.setIdUser(idUser);
                        search.setEventDescription(eventDescription);
                        search.setEventDate(eventDate);
                        search.setEventdateTimeSearch(eventdateTimeSearch);

                        lastSearchListdetailList.add(search);*/

                    }
                }else {
                    Log.d("gg","WIPE");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Log.d("coment", "ez");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String eventName =  snapshot.child("eventName").getValue(String.class);
                        String eventLocationidplace =  snapshot.child("idPlace").getValue(String.class);
                        String eventPicture =  snapshot.child("eventPicture").getValue(String.class);
                        String eventId =  snapshot.child("idEvent").getValue(String.class);
                        String eventgroupName =  snapshot.child("groupName").getValue(String.class);
                        String idUser =  snapshot.child("idUser").getValue(String.class);
                        String eventDescription =  snapshot.child("eventDescription").getValue(String.class);
                        String eventDate =  snapshot.child("eventDate").getValue(String.class);
                        Long eventdateTimeSearch =  snapshot.child("dateTimeSearch").getValue(Long.class);


                        LastSearchdetail lastSearchdetail = new LastSearchdetail( eventName, eventLocationidplace, eventPicture, eventId,eventgroupName, idUser, eventDescription , eventDate, eventdateTimeSearch );
                        lastSearchListdetailLis.add(lastSearchdetail);
                       /* search.setEventName(eventName);
                        search.setEventLocationidplace(eventLocationidplace);
                        search.setEventPicture(eventPicture);
                        search.setEventId(eventId);
                        search.setEventgroupName(eventgroupName);
                        search.setIdUser(idUser);
                        search.setEventDescription(eventDescription);
                        search.setEventDate(eventDate);
                        search.setEventdateTimeSearch(eventdateTimeSearch);

                        lastSearchListdetailList.add(search);*/

                    }
                }else {
                    Log.d("gg","WIPE");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        }); } }