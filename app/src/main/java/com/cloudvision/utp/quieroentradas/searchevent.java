package com.cloudvision.utp.quieroentradas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchevent extends AppCompatActivity {
    EditText search_edit_text;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseEvent;
    ArrayList<String> descriptionlist;
    ArrayList<String> imagelist;
    ArrayList<String> namelist;
    ArrayList<String> statuslist;
    SearchAdapter searchAdapter;
    @Override
    protected  void  onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        search_edit_text=(EditText) findViewById(R.id.searchedit);
        recyclerView= (RecyclerView) findViewById(R.id.list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseEvent = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

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

                searchAdapter = new SearchAdapter(searchevent.this, descriptionlist, imagelist, namelist, statuslist);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
