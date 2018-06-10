package com.cloudvision.utp.quieroentradas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MySearchsFragment extends Fragment {

    private static final String ARG_SECTION_TITLE = "section_title";

    public MySearchsFragment() {
    }

    public static MySearchsFragment newInstance(String sectionTitle) {
        MySearchsFragment fragment = new MySearchsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_searchs, container, false);
        String title = getArguments().getString(ARG_SECTION_TITLE);
        TextView txtTitle = (TextView) view.findViewById(R.id.title);
        txtTitle.setText(title);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
