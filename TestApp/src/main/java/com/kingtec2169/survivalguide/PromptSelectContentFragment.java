// Copyright 2014 FRC team 2169 KING TeC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This fragment displays a line of text to the user. This fragment is used in
// the FRC Survival Guide to tell the user to select content if none is selected.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PromptSelectContentFragment extends Fragment {

    private static final List<String> PROMPT_LIST = new ArrayList<String>();

    static {
        PROMPT_LIST.add("Pick an item to learn about!");
        PROMPT_LIST.add("Go ahead, click an item and get started!");
        PROMPT_LIST.add("Well, what are you waiting for?");
    }

    public PromptSelectContentFragment() { // Empty public constructor mandated by the fragment manager
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