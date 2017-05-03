package com.example.gabriella.chatizoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by yungbena on 4/22/17.
 */

public class AfterLoginController extends AppCompatActivity {


    private String userid;
    private Bundle bundle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        userid = getIntent().getStringExtra("USERID");
        bundle = new Bundle();
        bundle.putString("USERID", userid);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        String title = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_contacts:
                                selectedFragment = ContactsScreen.newInstance();
                                title = "Contacts";
                                break;
                            case R.id.navigation_messages:
                                selectedFragment = MessagesScreen.newInstance();
                                title = "Messages";
                                break;
                            case R.id.navigation_settings:
                                selectedFragment = SettingsScreen.newInstance();
                                title = "Settings";
                                break;
                        }
                        if(selectedFragment != null) {
                            selectedFragment.setArguments(bundle);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout, selectedFragment);
                            getSupportActionBar().setTitle(title);
                            transaction.commit();

                        }
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        Fragment primaryFrag = ContactsScreen.newInstance();
        primaryFrag.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, primaryFrag);
        getSupportActionBar().setTitle("Contacts");
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public String getUserid() {
        return userid;
    }
}
