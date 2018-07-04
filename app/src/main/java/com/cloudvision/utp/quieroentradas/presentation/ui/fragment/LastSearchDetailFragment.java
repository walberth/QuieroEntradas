package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.data.model.UserSearch;
import com.cloudvision.utp.quieroentradas.domain.model.EventsFound;
import com.cloudvision.utp.quieroentradas.domain.model.LastEventSearch;
import com.cloudvision.utp.quieroentradas.presentation.adapter.EventsFoundAdapter;
import com.cloudvision.utp.quieroentradas.presentation.adapter.LastSearchDetailAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Walberth Gutierrez Telles on 02,July,2018
 */
public class LastSearchDetailFragment extends Fragment {
    private String eventGroup, eventTime, keyUserImageSearch;
    private TextView eventGroupSearch, eventTimeSearch;
    private List<LastEventSearch> eventSearchList;
    private FirebaseUser user;
    private RecyclerView recyclerSearchedEventsFound;
    private LastSearchDetailAdapter lastSearchDetailAdapter;

    public LastSearchDetailFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            eventGroup = mBundle.getString("eventGroupSearch");
            eventTime = mBundle.getString("eventTimeSearch");
            keyUserImageSearch = mBundle.getString("keyUserImageSearch");
        }

        return inflater.inflate(R.layout.fragment_last_search_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventSearchList = new ArrayList<>();
        eventGroupSearch = view.findViewById(R.id.eventGroupSearch);
        eventTimeSearch = view.findViewById(R.id.eventTimeSearch);
        user = FirebaseAuth.getInstance().getCurrentUser();
        eventGroupSearch.setText(eventGroup);
        eventTimeSearch.setText(eventTime);
        recyclerSearchedEventsFound = view.findViewById(R.id.recyclerSearchedEventsFound);
        recyclerSearchedEventsFound.setLayoutManager(new LinearLayoutManager(getContext()));

        getLastEventsFound();
    }

    public void getLastEventsFound() {
        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference();

        mFirebaseDatabase.child("eventSearch").orderByChild("idUser").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EventSearch eventSearch = dataSnapshot.getValue(EventSearch.class);

                if (eventSearch != null
                        && eventSearch.getEventName() != null
                        && !eventSearch.getEventName().equals("")
                        && eventSearch.getUserSearchKey().equals(keyUserImageSearch)
                        && eventSearch.getIdUser().equals(user.getUid())) {
                    final LastEventSearch lastEventSearch = new LastEventSearch();

                    //TODO: CHANGE WITH MORE ELEMTNS FOR THE FUTURE VIEW
                    lastEventSearch.setEventName(Objects.requireNonNull(eventSearch).getEventName());
                    lastEventSearch.setEventPicture(eventSearch.getEventPicture());


                    eventSearchList.add(lastEventSearch);
                    lastSearchDetailAdapter = new LastSearchDetailAdapter(recyclerSearchedEventsFound, eventSearchList, getContext());
                    recyclerSearchedEventsFound.setHasFixedSize(true);
                    recyclerSearchedEventsFound.setAdapter(lastSearchDetailAdapter);
                    //lastSearchDetailAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
        });
    }
}
