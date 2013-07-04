package com.rtzoeller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rtzoeller on 7/3/13.
 */
public class PromptSelectWeekFragment extends Fragment {

    public PromptSelectWeekFragment() { // Empty public constructor mandated by the fragment manager
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prompt_select_week, container, false);
        ((TextView)view.findViewById(R.id.prompt)).setText("Pick a week to learn about!");
        return view;
    }
}