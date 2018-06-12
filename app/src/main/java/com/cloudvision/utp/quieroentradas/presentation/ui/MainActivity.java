package com.cloudvision.utp.quieroentradas.presentation.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudvision.utp.quieroentradas.R;
import com.cloudvision.utp.quieroentradas.data.datasource.session.UserSessionManager;
import com.cloudvision.utp.quieroentradas.data.model.User;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.LastSearchFragment;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.SearchFragment;
import com.cloudvision.utp.quieroentradas.presentation.ui.fragment.StadisticFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG  = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LastSearchFragment lastSearchFragment;
    private FirebaseAuth auth;
    private FirebaseUser user;
    public static UserSessionManager session;
    public static User userResponse;
    private TextView fullname, email;
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navView = navigationView.getHeaderView(0);
        fullname = (TextView) navView.findViewById(R.id.niv_header_fullname);
        email = (TextView) navView.findViewById(R.id.niv_header_email);
        circleImageView = (CircleImageView) navView.findViewById(R.id.niv_header_photo);

        userResponse = getSessionSharedPreferences();
        Glide.with(this).load(Uri.parse(userResponse.getSexUri())).into(circleImageView);
        fullname.setText(new StringBuilder(userResponse.getName()).append(" ").append(userResponse.getLastName()).toString());
        email.setText(userResponse.getEmail());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);

        drawerLayout.setDrawerListener(toggle);

        toggle.syncState();

        Bundle data = new Bundle();
        data.putString("userCode", Objects.requireNonNull(user.getEmail()).substring(0,7));

        //TODO
        lastSearchFragment = new LastSearchFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        lastSearchFragment.setArguments(data);
        fragmentTransaction.add(R.id.content, lastSearchFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nitMySearching:
                LastSearchFragment lastSearchFragment = new LastSearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, lastSearchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.nitSearch:
                SearchFragment searchFragment = new SearchFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, searchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.nitRanking:
                StadisticFragment stadisticFragment = new StadisticFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content, stadisticFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.nitProfile:
                Toast.makeText(getApplicationContext(), "profile", Toast.LENGTH_LONG).show();
                break;
            case R.id.nitLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public User getSessionSharedPreferences() {
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) finish();
        Map<String, Object> user = session.getUserDetails();
        Log.d(TAG, "uiemail init d " +  user.get(UserSessionManager.KEY_SEX_URI));
        return new User((String) user.get(UserSessionManager.KEY_ID),
                (String) user.get(UserSessionManager.KEY_NAME),
                (String) user.get(UserSessionManager.KEY_LASTNAME),
                (String) user.get(UserSessionManager.KEY_EMAIL),
                (Long) user.get(UserSessionManager.KEY_CREATED_USER),
                (String) user.get(UserSessionManager.KEY_USERNAME),
                (String) user.get(UserSessionManager.KEY_SEX_NAME),
                (String) user.get(UserSessionManager.KEY_SEX_URI));
    }
}