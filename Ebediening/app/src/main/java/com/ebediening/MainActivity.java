package com.ebediening;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebediening.Fragment.FavoriteFragment;
import com.ebediening.Fragment.Fragment_Home;
import com.ebediening.Fragment.OdrHistoryFragment;
import com.ebediening.Fragment.ProfileFragment;
import com.ebediening.Fragment.ReservationFragment;
import com.ebediening.Fragment.NotificationFragment;
import com.ebediening.Fragment.TrackOdrFragment;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Preferences;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,NotificationFragment.OnFragmentInteractionListener,TrackOdrFragment.OnFragmentInteractionListener,OdrHistoryFragment.OnFragmentInteractionListener,FavoriteFragment.OnFragmentInteractionListener {
    ImageView menu_nav;
    GoogleSignInClient mGoogleSignInClient;
    public static TextView text_heading;
    public static ImageView image_heading;//, share, logout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestProfile()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        image_heading = (ImageView) findViewById(R.id.image_heading);
//        share = (ImageView) findViewById(R.id.share);
//        share.setOnClickListener(this);
//        share.setVisibility(View.GONE);
//        logout = (ImageView) findViewById(R.id.logout);
//        logout.setOnClickListener(this);
//        logout.setVisibility(View.GONE);
        text_heading = (TextView) findViewById(R.id.text_heading);
        image_heading.setVisibility(View.VISIBLE);
        text_heading.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        menu_nav = (ImageView) findViewById(R.id.menu_nav);
        menu_nav.setOnClickListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View hView = navigationView.getHeaderView(0);
        CircleImageView nav_image = (CircleImageView) hView.findViewById(R.id.nav_user_image);
        if (!Preferences.getInstance().getUserPhotoPath().equalsIgnoreCase(null)
                && !Preferences.getInstance().getUserPhotoPath().equalsIgnoreCase("null")
                && !Preferences.getInstance().getUserPhotoPath().equalsIgnoreCase("")) {
            Picasso.with(MainActivity.this).load(Preferences.getInstance().getUserPhotoPath()).into(nav_image);
        } else {
            Picasso.with(MainActivity.this).load(R.drawable.white_img).resize(80,80).into(nav_image);
        }
        TextView nav_name = (TextView) hView.findViewById(R.id.nav_name);
        nav_name.setText(Preferences.getInstance().getFirstName()+" "+Preferences.getInstance().getLastName());
        TextView nav_email = (TextView) hView.findViewById(R.id.nav_email);
        nav_email.setText(Preferences.getInstance().getEmail());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment_Home frag_home = new Fragment_Home();
        fragmentTransaction.add(R.id.main_content, frag_home);
        fragmentTransaction.commit();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            Fragment_Home frag_home = new Fragment_Home();
            fragmentTransaction.replace(R.id.main_content, frag_home);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_track) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Notifications");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            TrackOdrFragment notificationFragment = new TrackOdrFragment();
            fragmentTransaction.replace(R.id.main_content, notificationFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_order_history) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Reservation");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            OdrHistoryFragment frag_home = new OdrHistoryFragment();
            fragmentTransaction.replace(R.id.main_content, frag_home);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_favo) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Favorites");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            FavoriteFragment frag_home = new FavoriteFragment();
            fragmentTransaction.replace(R.id.main_content, frag_home);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_reserv) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Reservation");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ReservationFragment frag_home = new ReservationFragment();
            fragmentTransaction.replace(R.id.main_content, frag_home);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_pro) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Profile");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            ProfileFragment frag_home = new ProfileFragment();
            fragmentTransaction.replace(R.id.main_content, frag_home);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_notification) {
            image_heading.setVisibility(View.GONE);
            text_heading.setVisibility(View.VISIBLE);
            text_heading.setText("Notifications");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            NotificationFragment notificationFragment = new NotificationFragment();
            fragmentTransaction.replace(R.id.main_content, notificationFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_logout) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            LoginManager.getInstance().logOut();
                            Preferences.getInstance().setLogIn(false);
                            Preferences.getInstance().clearUserDetails();
                            finish();
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_nav:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;

//            case R.id.share:
//
//                break;
//
//            case R.id.logout:
//                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
