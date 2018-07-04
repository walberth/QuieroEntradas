package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.LastSearchdetail;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Detailsearchlas extends AppCompatActivity {
    private TextView description, name;
    private Query databaseReference;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public LastSearchdetail lastSearchdetail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_last_search_detail);
        description= (TextView) findViewById(R.id.lugarevent);
        name= (TextView) findViewById(R.id.descevent);
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        String getName = (String) bd.get("eventName");
        String getDate= (String)  bd.get("eventDate");

       // databaseReference= FirebaseDatabase.getInstance().getReference("eventSearch").equalTo("groupName",getName).equalTo("dateTimeSearch",getLocal);
      // String desc= databaseReference.toString();


        name.setText(getName);







        /*btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FirstViewActivity.this, LoginActivity.class));
            }
        });*/
    }


}


/*

    private void getLastSearchItemDetail(String getDate) {

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();


        try {
            mFirebaseDatabase.child("eventSearch").orderByChild("dateTimeSearch").equalTo(getDate).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   // LastSearchdetail LastSearchdetail = dataSnapshot.getValue(LastSearchdetail.class);

                    */
/*if (LastSearchdetail != null
                            && LastSearchdetail.getEventgroupName() != null)

                            {
                        final LastSearchdetail search = new LastSearchdetail();

                        search.setEventName(Objects.requireNonNull(LastSearchdetail).getEventName());
                        search.setDateTimeSearched(Long.toString(LastSearchdetail.getDateTimeSearch()));
                        search.setPictureSearched(lastSearch.getPicture());



                    }*//*

                    String eventName =  dataSnapshot.child("eventName").getValue(String.class);
                    String eventLocationidplace =  dataSnapshot.child("idPlace").getValue(String.class);
                    String eventPicture =  dataSnapshot.child("eventPicture").getValue(String.class);
                    String eventId =  dataSnapshot.child("idEvent").getValue(String.class);
                    String eventgroupName =  dataSnapshot.child("groupName").getValue(String.class);
                    String idUser =  dataSnapshot.child("idUser").getValue(String.class);
                    String eventDescription =  dataSnapshot.child("eventDescription").getValue(String.class);
                    String eventDate =  dataSnapshot.child("eventDate").getValue(String.class);
                    String eventdateTimeSearch =  dataSnapshot.child("dateTimeSearch").getValue(String.class);
                   final LastSearchdetail  searchdetail = new LastSearchdetail( eventName, eventLocationidplace, eventPicture, eventId,eventgroupName, idUser, eventDescription , eventDate, eventdateTimeSearch );
                    name.setText(eventgroupName);
                   recibevalues(eventgroupName);
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
*/
