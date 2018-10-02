package com.example.kirmi.ks1807;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.kirmi.ks1807.R;

public class NavBarMain extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbarmain);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch(item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_diary:
                fragment = new DiaryFragment();
                break;
            case R.id.nav_resources:
                fragment = new ResourcesFragment();
                break;
            case R.id.nav_progress:
                fragment = new ProgressFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
