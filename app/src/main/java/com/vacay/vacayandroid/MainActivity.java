package com.vacay.vacayandroid;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////start tool bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Start on events page
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new EventsFragement()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }


    private EventsFragement getFragmentWithBundle(Bundle bundle) {
        EventsFragement eventsFragement = new EventsFragement();
        eventsFragement.setArguments(bundle);

        return eventsFragement;
    }

    android.support.v4.app.Fragment currentEvent;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragement()).commit();
                break;
            case R.id.nav_home:
                currentEvent = new EventsFragement();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentEvent).commit();
                break;
            case R.id.losangeles_filter:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.CITY, Constants.LA);
                currentEvent = getFragmentWithBundle(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentEvent).commit();
                Toast.makeText(this, "LA Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nyc_filter:
                Bundle bundleNY = new Bundle();
                bundleNY.putString(Constants.CITY, Constants.NY);
                currentEvent = getFragmentWithBundle(bundleNY);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentEvent).commit();
                Toast.makeText(this, "NYC Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sf_filter:
                Bundle bundleSF = new Bundle();
                bundleSF.putString(Constants.CITY, Constants.SF);
                currentEvent = getFragmentWithBundle(bundleSF);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currentEvent).commit();
                Toast.makeText(this, "SF Events", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cheap_filter:
                if(currentEvent != null) {
                    ((EventsFragement)currentEvent).setFilter(Constants.CHEAP);
                    Toast.makeText(this, "Applied filter", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.medium_filter:
                if(currentEvent != null) {
                    ((EventsFragement)currentEvent).setFilter(Constants.MEDIUM);
                    Toast.makeText(this, "Applied filter", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.expensive_filter:
                if(currentEvent != null) {
                    ((EventsFragement)currentEvent).setFilter(Constants.EXPENSIVE);
                    Toast.makeText(this, "Applied filter", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
