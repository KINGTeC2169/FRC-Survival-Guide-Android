package com.rtzoeller;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by rtzoeller on 7/11/13.
 */
public class SettingsFragment extends PreferenceFragment {
    public static final String KEY_CONFIRM_EXIT = "confirm_exit";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
