package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudvision.utp.quieroentradas.R;

/**
 * Created by Walberth Gutierrez Telles on 09,June,2018
 */
public class SearchDetailFragment extends Fragment {


    public SearchDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_detail, container, false);
    }

}
