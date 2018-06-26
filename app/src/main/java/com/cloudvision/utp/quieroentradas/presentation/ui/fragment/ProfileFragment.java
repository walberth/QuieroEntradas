package com.cloudvision.utp.quieroentradas.presentation.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.model.User;
import com.cloudvision.utp.quieroentradas.helpers.enums.UserSexEnum;
import com.cloudvision.utp.quieroentradas.helpers.utils.Dates;
import com.cloudvision.utp.quieroentradas.presentation.ui.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG  = "ProfileFragment";
    private TextView email, fullname, username, sex, creationDate;
    private CircleImageView circleImageView;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        User userResponse = MainActivity.userResponse;
        circleImageView = (CircleImageView) view.findViewById(R.id.photo);
        Glide.with(this).load(Uri.parse(userResponse.getSexUri())).into(circleImageView);
        email = (TextView) view.findViewById(R.id.email);
        email.setText(userResponse.getEmail().toUpperCase());
        fullname = (TextView) view.findViewById(R.id.fullname);
        fullname.setText(new StringBuilder(userResponse.getName()).append(" ").append(userResponse.getLastName()).toString().toUpperCase());
        username = (TextView) view.findViewById(R.id.username);
        username.setText(userResponse.getUsername().toUpperCase());
        sex = (TextView) view.findViewById(R.id.sex);
        sex.setText(UserSexEnum.getSexLabelByName(userResponse.getSexName()));
        creationDate = (TextView) view.findViewById(R.id.creationDate);
        creationDate.setText(Dates.getDaysFromDateParamToToday(Dates.getDateByLongValue(userResponse.getUserCreatedTime())));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.nit_profile));
    }
}
