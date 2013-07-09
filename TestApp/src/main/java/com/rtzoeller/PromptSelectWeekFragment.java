package com.rtzoeller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rtzoeller on 7/3/13.
 */
public class PromptSelectWeekFragment extends Fragment {

    private static final List<String> PROMPT_LIST = new ArrayList<String>();

    static {
        PROMPT_LIST.add("Pick a week to learn about!");
        PROMPT_LIST.add("Go ahead, click a week and get started!");
        PROMPT_LIST.add("Well, what are you waiting for?");
    }

    public PromptSelectWeekFragment() { // Empty public constructor mandated by the fragment manager
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Randomly pick a prompt to display
        Random random = new Random();
        String content = PROMPT_LIST.get(random.nextInt(PROMPT_LIST.size()));

        // Inflate the view and display the prompt
        View view = inflater.inflate(R.layout.fragment_prompt_select_week, container, false);
        ((TextView)view.findViewById(R.id.prompt)).setText(content);
        return view;
    }
}