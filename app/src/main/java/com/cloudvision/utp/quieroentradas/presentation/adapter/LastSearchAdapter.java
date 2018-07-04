package com.cloudvision.utp.quieroentradas.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.LastSearchdetail;
import com.cloudvision.utp.quieroentradas.domain.model.LastSearch;
import com.cloudvision.utp.quieroentradas.presentation.ui.Detailsearchlas;
import com.cloudvision.utp.quieroentradas.presentation.ui.helpers.SelectableAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Walberth Gutierrez Telles on 07,June,2018
 */
public class LastSearchAdapter  extends SelectableAdapter<LastSearchAdapter.ViewHolder> {
    private static final String TAG = "LastSearchAdapter";
    private Context context;
    private List<LastSearch> eventSearchList;
    public String  date, name;
    private DatabaseReference mFirebaseDatabase = FirebaseDatabase.getInstance().getReference("eventSearch");
    public LastSearchAdapter(RecyclerView recyclerView, List<LastSearch> eventSearchList, Context context) {
        super(recyclerView);
        this.context = context;
        this.eventSearchList = eventSearchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lastsearch, parent,false);
        return new LastSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(eventSearchList.get(getItemCount() - 1 - position));
    }

    @Override
    public int getItemCount() {
        return eventSearchList.size();
    }

    public class ViewHolder extends SelectableAdapter.ViewHolder{
        private ImageView eventImage;
        private TextView eventName, eventDate;
        private Button btnEventDetail;

        public ViewHolder(View itemView) {
            super(itemView);

            eventImage = itemView.findViewById(R.id.eventImage);
            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            btnEventDetail = itemView.findViewById(R.id.btnEventDetail);

            btnEventDetail.setOnClickListener(new btnSeeEventDetail());
        }

        public void bind(LastSearch lastSearch){
            eventName.setText(lastSearch.getGroupName());
            eventDate.setText(convertTime( Long.parseLong(lastSearch.getDateTimeSearched())));
        }
    }

    class btnSeeEventDetail implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context.getApplicationContext(), "Yo want to review the detail", Toast.LENGTH_LONG).show();
            Long tiempo= Long.parseLong(date) ;
            Log.d(TAG, "onClick: Go to the other fragment");
            mFirebaseDatabase.orderByChild("dateTimeSearch").equalTo(tiempo).addListenerForSingleValueEvent(new ValueEventListener()

            {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        Log.d("coment", "ez");
                        // LastSearchdetail lastSearchdetail = snapshot.getValue(LastSearchdetail.class);
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
                            // final LastSearchdetail searchdetail = new LastSearchdetail( eventName, eventLocationidplace, eventPicture, eventId,eventgroupName, idUser, eventDescription , eventDate, eventdateTimeSearch );
                            LastSearchdetail lastSearchdetail = new LastSearchdetail(eventName, eventLocationidplace, eventPicture, eventId, eventgroupName, idUser, eventDescription, eventDate,eventdateTimeSearch );
                       /* LastSearchdetail lastSearchdetail = new LastSearchdetail();
                        lastSearchdetail.setEventName(eventName);
                        lastSearchdetail.setEventLocationidplace(eventLocationidplace);
                        lastSearchdetail.setEventPicture(eventPicture);
                        lastSearchdetail.setEventId(eventId);
                        lastSearchdetail.setEventgroupName(eventgroupName);
                        lastSearchdetail.setIdUser(idUser);
                        lastSearchdetail.setEventDescription(eventDescription);
                        lastSearchdetail.setEventDate(eventDate);
                        lastSearchdetail.setEventdateTimeSearch(eventdateTimeSearch);
                        ;*/
                            Log.d( "an_id" , eventDate);
                            Intent i = new Intent(context, Detailsearchlas.class);
                            i.putExtra("eventDate", String.valueOf(dataSnapshot.child("groupName").getValue(String.class)));
                            i.putExtra("eventName",eventgroupName );
                            i.putExtra("eventlugar",eventLocationidplace );
                            i.putExtra("eventid",eventId );
                            i.putExtra("eventlugar",eventLocationidplace );



                            context.startActivity(i);
                        }}else {
                        Log.d("gg", "gg wp");
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            //  getLastSearchItemDetail(date);

        }
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return format.format(date);
    }
}
