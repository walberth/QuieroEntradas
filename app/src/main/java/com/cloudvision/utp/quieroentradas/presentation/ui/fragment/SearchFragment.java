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

/**
 * Created by Ronald Estela on 07,June,2018
 */
public class SearchFragment extends Fragment {
    private EditText search_edit_text;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseEvent;
    private ArrayList<String> descriptionlist;
    private ArrayList<String> imagelist;
    private ArrayList<String> namelist;
    private ArrayList<String> statuslist;
    private SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_edit_text= view.findViewById(R.id.searchedit);
        recyclerView=  view.findViewById(R.id.list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseEvent = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        descriptionlist = new ArrayList<>();
        imagelist = new ArrayList<>();
        namelist = new ArrayList<>();
        statuslist = new ArrayList<>();

        search_edit_text.addTextChangedListener(new TextWatcher() {
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
                    statuslist.clear();
                    recyclerView.removeAllViews();}
            }
        });
    }

    public void setAdapter(final String searchstring) {

        databaseReference.child("events").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // se limpia la vista cada vez que se realiza una busqueda

                descriptionlist.clear();
                imagelist.clear();
                namelist.clear();
                statuslist.clear();
                recyclerView.removeAllViews();
                int counter = 0;

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String uid = snapshot.getKey();
                    String description =  snapshot.child("description").getValue(String.class);
                    String image =  snapshot.child("image").getValue(String.class);
                    String name =  snapshot.child("name").getValue(String.class);
                    String status =  snapshot.child("status").getValue(String.class);

                    if (description.toLowerCase().contains(searchstring.toLowerCase())){
                        descriptionlist.add(description);
                        imagelist.add(image);
                        namelist.add(name);
                        statuslist.add(status);
                        counter++;

                    } else  if (name.toLowerCase().contains(searchstring.toLowerCase())){
                        descriptionlist.add(description);
                        imagelist.add(image);
                        namelist.add(name);
                        statuslist.add(status);
                        counter++;
                    }
                    if (counter == 15)
                        break;
                }

                searchAdapter = new SearchAdapter(getContext(), descriptionlist, imagelist, namelist, statuslist);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
