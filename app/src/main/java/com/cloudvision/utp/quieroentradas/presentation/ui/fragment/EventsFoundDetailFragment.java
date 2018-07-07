package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudvision.utp.quieroentradas.R;
import java.util.Objects;

/**
 * Created by Walberth Gutierrez Telles on 01,July,2018
 */
public class EventsFoundDetailFragment extends Fragment {
    private static final String TAG = "EventsFoundDetailFragment";
    private String idLocation;
    private String latitud;
    private String longitud;
    private String eventName;
    private String eventGroup;
    private String eventPlaceDescription;
    private String eventDescription;
    private ImageView imgDetailEventFound;
    private TextView txtEventPlaceDescription, txtEventDescription;
    //private String keyEventSearch;

    public EventsFoundDetailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null) {
            Bundle mBundle = getArguments();
            idLocation = mBundle.getString("idLocation");
            latitud = mBundle.getString("latitud");
            longitud = mBundle.getString("longitud");
            eventName = mBundle.getString("eventName");
            eventGroup = mBundle.getString("eventGroup");
            //keyEventSearch = mBundle.getString("keyEventSearch");
            eventPlaceDescription = mBundle.getString("eventPlaceDescription");
            eventDescription = mBundle.getString("eventDescription");
        }

        return inflater.inflate(R.layout.fragment_events_found_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgDetailEventFound = view.findViewById(R.id.imgDetailEventFound);
        txtEventPlaceDescription = view.findViewById(R.id.txtEventPlaceDescription);
        txtEventDescription = view.findViewById(R.id.txtEventDescription);
        Button btnComments = view.findViewById(R.id.btnComments);
        Button btnMapLocation = view.findViewById(R.id.btnMapLocation);

        txtEventPlaceDescription.setText(eventPlaceDescription);
        txtEventDescription.setText(eventDescription);

        btnComments.setOnClickListener(new btnViewComments());
        btnMapLocation.setOnClickListener(new btnViewLocationInMap());
    }

    class btnViewComments implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            Bundle data = new Bundle();
            data.putString("idLocation", idLocation);
            android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CommentFragment commentFragment = new CommentFragment();
            commentFragment.setArguments(data);
            fragmentTransaction.replace(R.id.content, commentFragment).commit();
        }
    }

    class btnViewLocationInMap implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle data = new Bundle();
            data.putString("latitud", latitud);
            data.putString("longitud", longitud);
            data.putString("eventName", eventName);
            data.putString("eventGroup", eventGroup);
            android.support.v4.app.FragmentManager fragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LocationFragment locationFragment = new LocationFragment();
            locationFragment.setArguments(data);
            fragmentTransaction.replace(R.id.content, locationFragment).commit();
        }
    }
}
