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
// This class acts as a holder for the pages used to display content. It is responsible for
// parsing which layout to use for a page and keeping that content in memory, to avoid reading
// repeatedly from XML.

import android.content.Context;

public class Page {
    // How many items this entry should have
    public int expectedLength;
    // Information loaded from XML
    public int layoutResourceId;
    public String text;
    public String title;
    public int imageResourceId;
    public String tags;

    public Page(Context context, String[] contentResources) { // Accepts a calling context and an array of the page data
        super();

        if(contentResources.length >= 1) {
            // Make sure there is an entry for the layout of the expected view
            // This will tell us how long of an XML array we should expect

            // Get the layout resource id from the name stored in XML
            layoutResourceId = context.getResources().getIdentifier(contentResources[0], "layout", context.getPackageName());
            // Look up the expected length for this layout
            expectedLength = getExpectedLength(layoutResourceId);

            // Make sure the expected length matches what we are actually working with
            if(contentResources.length == expectedLength) {
                // Store the XML data
                switch (layoutResourceId) {
                    case R.layout.fragment_pager_item:
                        this.title = contentResources[1];
                        this.text = contentResources[2];
                        this.imageResourceId = context.getResources().getIdentifier(contentResources[3], "drawable", context.getPackageName());
                        this.tags = contentResources[4];
                        break;
                }
            }
        } else {
            // Something is wrong with the XML data
            expectedLength = 0;
        }
    }

    public Page(Context context, int arrayResourceId) { // Accepts a calling context and a resource id for the array of the page data
        this(context, context.getResources().getStringArray(arrayResourceId));
    }

    public static int getExpectedLength(int layoutResourceId) {
        // These numbers should include the layout tag in their length
        switch (layoutResourceId) {
            case R.layout.fragment_pager_item:
                return 5;
            default:
                // Something is wrong
                return 0;
        }
    }

}
