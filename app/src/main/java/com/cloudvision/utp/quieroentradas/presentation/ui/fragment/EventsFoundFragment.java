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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.datasource.rest.VolleyController;
import com.cloudvision.utp.quieroentradas.data.model.EventSearch;
import com.cloudvision.utp.quieroentradas.data.model.EventsFound;
import com.cloudvision.utp.quieroentradas.data.model.Place;
import com.cloudvision.utp.quieroentradas.presentation.adapter.EventsFoundAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    //private LinearLayout linearLayoutEventsFound;

    public interface VolleyCallback{
        void onSuccessResponse(String result);
    }

    public EventsFoundFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        //linearLayoutEventsFound = view.findViewById(R.id.linearLayoutEventsFound)
        eventsFoundList = new ArrayList<>();
        recyclerEventsFound = view.findViewById(R.id.recyclerEventsFound);
        recyclerEventsFound.setLayoutManager(new LinearLayoutManager(getContext()));

        wsSongClickCallback(groupName);
    }

    public void wsSongClickCallback(String artistName) {
        final String URL_TO_CONSUME = URL_WS + "Radiohead";/*artistName*/
        progressBarEventsFound.setVisibility(View.VISIBLE);
        //linearLayoutEventsFound.setVisibility(View.VISIBLE);
        getSongClickInformation(URL_TO_CONSUME,
                new EventsFoundFragment.VolleyCallback(){
                    @Override
                    public void onSuccessResponse(String result) {
                        try{
                            String groupName = null;
                            JSONArray jsonArray = new JSONObject(result).getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childObject = jsonArray.getJSONObject(i);

                                JSONArray performanceArray = childObject.optJSONArray("performance");

                                for (int j = 0; j < performanceArray.length(); j++) {
                                    JSONObject performaceValues = performanceArray.getJSONObject(j);

                                    groupName = performaceValues.optString("displayName");
                                }
                                //-----------------------------------EventSearch
                                EventSearch eventSearch = new EventSearch();
                                eventSearch.setIdUser(user.getUid());
                                eventSearch.setDateTimeSearch(new Date().getTime());
                                eventSearch.setEventName(childObject.optString("displayName"));
                                eventSearch.setEventDate(childObject.optJSONObject("start").optString("date"));
                                eventSearch.setEventPicture("picture here");
                                eventSearch.setEventDescription(childObject.optString("displayName"));
                                eventSearch.setGroupName(groupName);

                                /*Sending to firebase the eventSearchs*/
                                String keyEventSearch = FirebaseDatabase.getInstance().getReference().child("eventSearch").push().getKey();
                                FirebaseDatabase.getInstance().getReference().child("eventSearch").child(Objects.requireNonNull(keyEventSearch)).setValue(eventSearch);

                                //------------------------------------Places
                                Place place = new Place();
                                place.setName(childObject.optJSONObject("venue").optString("displayName"));

                                String locationDisplayName = childObject.optJSONObject("venue").optJSONObject("metroArea").optString("displayName");
                                String countryDisplayName = childObject.optJSONObject("venue").optJSONObject("metroArea").optJSONObject("country").optString("displayName");
                                String location = locationDisplayName + ", " + countryDisplayName;

                                place.setDirection(location);
                                place.setLatitud(childObject.optJSONObject("venue").optString("lat"));
                                place.setLongitud(childObject.optJSONObject("venue").optString("lng"));
                                place.setEventid(keyEventSearch);
                                FirebaseDatabase.getInstance().getReference().child("places").push().setValue(place);

                                //--------------------------------------EventsFound
                                EventsFound eventsFound = new EventsFound();
                                eventsFound.setEventName(eventSearch.getEventName());
                                eventsFound.setEventLocation(place.getDirection());
                                eventsFound.setEventPicture("PICTURE SOMEWHERE");
                                eventsFoundList.add(eventsFound);

                                Log.d(TAG, "onSuccessResponse: " + childObject.optString("displayName"));
                            }

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
    }

    private void getSongClickInformation(String url, final EventsFoundFragment.VolleyCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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
                        progressBarEventsFound.setVisibility(View.GONE);
                    }
                });
        VolleyController.getInstance(getActivity()).addToRequestQueue(request);
    }
}
