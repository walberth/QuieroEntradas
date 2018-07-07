package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.datasource.rest.VolleyController;
import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.domain.model.CloudVisionElement;
import com.cloudvision.utp.quieroentradas.domain.model.EventsFound;
import com.cloudvision.utp.quieroentradas.data.model.Place;
import com.cloudvision.utp.quieroentradas.presentation.adapter.EventsFoundAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Walberth Gutierrez Telles on 25,June,2018
 */
public class EventsFoundFragment extends Fragment {
    private static final String TAG = "EventsFoundFragment";
    protected static final String URL_WS = "https://api.songkick.com/api/3.0/events.json?apikey=kfTVgy9Id6UPKvWa&artist_name=";
    private RecyclerView recyclerEventsFound;
    private List<EventsFound> eventsFoundList;
    private EventsFoundAdapter eventsFoundAdapter;
    private String groupName;
    private FirebaseUser user;
    private ProgressBar progressBarEventsFound;
    private String keyUserImageSearch;
    private List<CloudVisionElement> cloudVisionElementList;

    public interface VolleyCallback{
        void onSuccessResponse(String result);
    }

    public EventsFoundFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            groupName = mBundle.getString("groupName");
            keyUserImageSearch = mBundle.getString("keyUserImageSearch");
        }

        return inflater.inflate(R.layout.fragment_events_found, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        progressBarEventsFound = view.findViewById(R.id.progressBarEventsFound);
        eventsFoundList = new ArrayList<>();
        recyclerEventsFound = view.findViewById(R.id.recyclerEventsFound);
        cloudVisionElementList = new ArrayList<>();
        recyclerEventsFound.setLayoutManager(new LinearLayoutManager(getContext()));

        wsSongClickCallback();
    }

    public void wsSongClickCallback() {
        //for(CloudVisionElement element : cloudVisionElementList) {
            //Log.d(TAG, "wsSongClickCallback: ELEMENT SENDED " + element.getDescriptionFounded() + " with score " + element.getScoreFounded());

        progressBarEventsFound.setVisibility(View.VISIBLE);
        //TODO: PROBE FOR LOGO ELEMENT
        if(groupName != null) {
            String[] elements = groupName.split("\n");

            for (String element : elements) {
                String[] values = element.split(" : ");

                CloudVisionElement cloudVisionElement = new CloudVisionElement();
                cloudVisionElement.setDescriptionFounded(values[0]);
                cloudVisionElement.setScoreFounded(Float.parseFloat(values[1]));
                cloudVisionElementList.add(cloudVisionElement);
            }
        }

        getSongClickInformation(URL_WS + /*element.getDescriptionFounded() + */"radiohead&page=1&per_page=10",
                new EventsFoundFragment.VolleyCallback(){
                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            String groupName = null;
                            JSONArray jsonArray = new JSONObject(result).getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event");
                            final List<Place> songClickPlaces = new ArrayList<>();
                            final Set<String> idPlacesNotRepeatedList;
                            final List<String> idPlaceList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject locationChildObject = jsonArray.getJSONObject(i);
                                idPlaceList.add(locationChildObject.optJSONObject("venue").optString("id"));
                            }

                            idPlacesNotRepeatedList = new HashSet<>(idPlaceList);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject childObject = jsonArray.getJSONObject(i);

                                JSONArray performanceArray = childObject.optJSONArray("performance");

                                for (int j = 0; j < performanceArray.length(); j++) {
                                    JSONObject performaceValues = performanceArray.getJSONObject(j);

                                    groupName = performaceValues.optString("displayName");
                                }

                                //Get the direction
                                String locationDisplayName = childObject.optJSONObject("venue").optJSONObject("metroArea").optString("displayName");
                                String countryDisplayName = childObject.optJSONObject("venue").optJSONObject("metroArea").optJSONObject("country").optString("displayName");
                                String direction = locationDisplayName + ", " + countryDisplayName;

                                /*NEW IMPLEMENTATION*/
                                //-----------------------------------EventSearch
                                final String idEvent = FirebaseDatabase.getInstance().getReference().child("eventSearch").push().getKey();

                                EventSearch eventSearch = new EventSearch();
                                eventSearch.setIdUser(user.getUid());
                                eventSearch.setDateTimeSearch(new Date().getTime());
                                eventSearch.setEventName(childObject.optString("displayName"));
                                eventSearch.setEventDate(childObject.optJSONObject("start").optString("date"));
                                eventSearch.setEventPicture("picture here");
                                eventSearch.setEventDescription(childObject.optString("displayName"));
                                eventSearch.setGroupName(groupName);
                                eventSearch.setUid(idEvent);
                                eventSearch.setIdEvent(childObject.optString("id"));
                                eventSearch.setUserSearchKey(keyUserImageSearch);

                                if(idPlacesNotRepeatedList.contains(childObject.optJSONObject("venue").optString("id"))) {
                                    eventSearch.setIdPlace(childObject.optJSONObject("venue").optString("id"));
                                    eventSearch.setLatitud(childObject.optJSONObject("venue").optString("lat"));
                                    eventSearch.setLongitud(childObject.optJSONObject("venue").optString("lng"));
                                    eventSearch.setPlaceName(childObject.optJSONObject("venue").optString("displayName"));
                                    eventSearch.setPlaceDirection(direction);
                                }

                                FirebaseDatabase.getInstance().getReference().child("eventSearch").child(Objects.requireNonNull(idEvent)).setValue(eventSearch);

                                //------------------------------------Places
                                Place place = new Place();
                                place.setName(childObject.optJSONObject("venue").optString("displayName"));
                                place.setDirection(direction);
                                place.setLatitud(childObject.optJSONObject("venue").optString("lat"));
                                place.setLongitud(childObject.optJSONObject("venue").optString("lng"));
                                place.setUid(childObject.optJSONObject("venue").optString("id"));
                                songClickPlaces.add(place);

                                //--------------------------------------EventsFound
                                EventsFound eventsFound = new EventsFound();
                                eventsFound.setEventName(eventSearch.getEventName());
                                eventsFound.setEventLocation(place.getDirection());
                                eventsFound.setEventId(idEvent);
                                eventsFound.setEventSongClickId(childObject.optString("id"));
                                eventsFound.setEventLocationId(childObject.optJSONObject("venue").optString("id"));
                                eventsFound.setEventPicture("PICTURE SOMEWHERE");
                                eventsFound.setLatitud(childObject.optJSONObject("venue").optString("lat"));
                                eventsFound.setLongitud(childObject.optJSONObject("venue").optString("lng"));
                                eventsFound.setEventGroup(groupName);
                               // eventsFound.setUserSearchKey();
                                eventsFoundList.add(eventsFound);
                            }

                            //-----------------------------------Inserting Places
                            final List<Place> firebasePlaces = new ArrayList<>();

                            FirebaseDatabase.getInstance().getReference().child("places").orderByChild("uid").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.getValue() != null) {
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            Place placeValue = data.getValue(Place.class);
                                            firebasePlaces.add(placeValue);
                                        }

                                        sendingPlacesToFirebase(firebasePlaces, songClickPlaces);
                                    } else {
                                        Set<Place> notRepeatedPlaces = new HashSet<>(songClickPlaces);

                                        for (Place valuesToInsert : notRepeatedPlaces) {
                                            FirebaseDatabase.getInstance().getReference().child("places").push().setValue(valuesToInsert);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //-----------------------------------Update UserSearch
                            FirebaseDatabase.getInstance()
                                    .getReference()
                                    .child("userSearch")
                                    .child(Objects.requireNonNull(keyUserImageSearch))
                                    .child("groupName")
                                    .setValue(groupName);

                            eventsFoundAdapter = new EventsFoundAdapter(recyclerEventsFound, eventsFoundList, getContext());
                            recyclerEventsFound.setHasFixedSize(true);
                            recyclerEventsFound.setAdapter(eventsFoundAdapter);

                            progressBarEventsFound.setVisibility(View.GONE);
                        } catch (JSONException ex) {
                            Log.e(TAG, "onSuccessResponse: error " + ex.getMessage());
                            ex.printStackTrace();
                        }
                    }
                });
    //}
    }

    private void sendingPlacesToFirebase(List<Place> firebasePlaces, List<Place> songClickPlaces){
        Set<Place> notRepeatedPlaces = new HashSet<>(firebasePlaces);
        List<Place> validatePlacesInLocal = new ArrayList<>();

        for(Place data : notRepeatedPlaces) {
            if(!songClickPlaces.contains(data) && !validatePlacesInLocal.contains(data)) {
                FirebaseDatabase.getInstance().getReference().child("places").push().setValue(data);

                validatePlacesInLocal.add(data);
            }
        }
    }

    private void getSongClickInformation(String url, final EventsFoundFragment.VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG, "onResponse: REPONSE FOR REQUEST " + response);

                    callback.onSuccessResponse(response);
                } catch (Exception ex) {
                    Log.e(TAG, "onResponse: error throw " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: error throw " + error.getMessage());
                        Toast.makeText(getActivity(), "No se pudo encontrar ningún evento de este grupo", Toast.LENGTH_LONG).show();
                        progressBarEventsFound.setVisibility(View.GONE);
                    }
                });
        VolleyController.getInstance(getActivity()).addToRequestQueue(request);
    }
}
