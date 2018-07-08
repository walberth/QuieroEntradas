package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.presentation.adapter.SearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ronald Estela on 07,June,2018
 */
public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<String> descriptionlist;
    private ArrayList<String> imagelist;
    private ArrayList<String> namelist;
    private ArrayList<String> eventTimeList;
    private SearchAdapter searchAdapter;
    private FirebaseUser user;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(getResources().getString(R.string.nav_item_search));
        EditText searchEditText = view.findViewById(R.id.searchedit);
        recyclerView=  view.findViewById(R.id.list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));

        descriptionlist = new ArrayList<>();
        imagelist = new ArrayList<>();
        namelist = new ArrayList<>();
        eventTimeList = new ArrayList<>();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    setAdapter(s.toString());
                } else{
                    descriptionlist.clear();
                    imagelist.clear();
                    namelist.clear();
                    eventTimeList.clear();
                    recyclerView.removeAllViews();}
            }
        });
    }

    public void setAdapter(final String searchstring) {

        databaseReference.child("eventSearch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                descriptionlist.clear();
                imagelist.clear();
                namelist.clear();
                eventTimeList.clear();
                recyclerView.removeAllViews();
                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    String[] description = Objects.requireNonNull(snapshot.child("eventDescription").getValue(String.class)).split("\\(");

                    String cleanDescription = description[0];
                    String image = snapshot.child("eventPicture").getValue(String.class);
                    String name = snapshot.child("groupName").getValue(String.class);
                    String eventTime = snapshot.child("eventDate").getValue(String.class);

                    if (Objects.requireNonNull(cleanDescription).toLowerCase().contains(searchstring.toLowerCase())
                            && user.getUid().equals(snapshot.child("idUser").getValue(String.class))){
                        descriptionlist.add(cleanDescription);
                        imagelist.add(image);
                        namelist.add(name);
                        eventTimeList.add(eventTime);
                        counter++;

                    } else  if (Objects.requireNonNull(name).toLowerCase().contains(searchstring.toLowerCase())){
                        descriptionlist.add(cleanDescription);
                        imagelist.add(image);
                        namelist.add(name);
                        eventTimeList.add(eventTime);
                        counter++;
                    }
                    if (counter == 10)
                        break;
                }

                searchAdapter = new SearchAdapter(getContext(), descriptionlist, imagelist, namelist, eventTimeList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
