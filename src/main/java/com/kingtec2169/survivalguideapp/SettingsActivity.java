package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// This activity handles the loading and displaying of the app settings using a PreferenceFragment.

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_CONFIRM_EXIT = "confirm_exit";
    public static final String KEY_USE_DRAWER = "use_drawer";
    public static final String KEY_CLOSE_DRAWER_ON_CLICK = "close_drawer_on_click";
    //public static final String KEY_LANGUAGE_SPANISH =  "translate_spanish";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        // Load a fragment showing the preferences
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SurvivalGuidePreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }


    public static class SurvivalGuidePreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}