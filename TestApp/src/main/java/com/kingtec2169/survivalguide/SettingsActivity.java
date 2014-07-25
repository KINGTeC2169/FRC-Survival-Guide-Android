package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This activity handles the loading and displaying of the app settings. Although this
// activity uses deprecated methods, there is not a clean solution using fragments.
// The PreferenceFragment, added in API level 11, does not have a support version.

// TODO: Now that support for pre-ICS devices has been dropped, rewrite this to use a PreferenceFragment

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity {
    public static final String KEY_CONFIRM_EXIT = "confirm_exit";
    public static final String KEY_USE_DRAWER = "use_drawer";
    public static final String KEY_CLOSE_DRAWER_ON_CLICK = "close_drawer_on_click";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
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
}