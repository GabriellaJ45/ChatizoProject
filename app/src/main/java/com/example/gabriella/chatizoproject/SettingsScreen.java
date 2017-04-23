package com.example.gabriella.chatizoproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by yungbena on 4/23/17.
 */

public class SettingsScreen extends Fragment {
    public static SettingsScreen newInstance() {
        SettingsScreen frag = new SettingsScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_settings_screen, container, false);
    }
}
