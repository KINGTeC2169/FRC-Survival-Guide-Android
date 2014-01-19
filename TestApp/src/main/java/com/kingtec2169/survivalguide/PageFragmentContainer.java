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
// This fragment acts as a holder for content on 10" tablets. It has two containers which
// are replaced with fragments when it is bound to the display. If only one fragment is passed
// in, that fragment will fill the entire screen.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragmentContainer extends Fragment {
    // Holds the left and right fragments/content
    private Fragment[] content = new Fragment[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_combined_content, container, false);

        // TODO: Replace the container hiding with a separate layout to avoid redraws
        // Swap in content for the placeholders
        if (content[0] != null) {
            if (content[1] != null) {
                /* There are two pages of content, so we should
                show both of them */
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.left, content[0])
                        .replace(R.id.right, content[1])
                        .commit();
            } else {
                /* There is only one page of content, so we should
                hide one of the containers so that the content stretches to fill the screen */
                result.findViewById(R.id.right).setVisibility(View.GONE);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.left, content[0])
                        .commit();
            }
        }

        return result;
    }

    public void setLeft(Fragment left) {
        // Set the left container index to a fragment
        // This will not actually display the fragment
        // That is handled in onCreateView
        content[0] = left;
    }

    public void setRight(Fragment right) {
        // Set the right container index to a fragment
        // This will not actually display the fragment
        // That is handled in onCreateView
        content[1] = right;
    }
}
