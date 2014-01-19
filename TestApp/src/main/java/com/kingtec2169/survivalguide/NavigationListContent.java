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
// This class acts as a holder for the list items in the ExpandableListNavigationFragment.

import java.util.ArrayList;
import java.util.List;

public class NavigationListContent {
    // The children of the ExpandableListView, holds the contents of each group
    public static final List<List<NavigationListItem>> CHILDREN = new ArrayList<List<NavigationListItem>>();
    // The parents of the ExpandableListView, holds the head item of each group
    public static final List<NavigationListItem> PARENTS = new ArrayList<NavigationListItem>();

    static {
        // Populate the list of parents
        NavigationListItem[] parents = {new NavigationListItem("0", "Build Season", ""), new NavigationListItem("1", "Competition", ""), new NavigationListItem("2", "Off-Season", "")};
        // Add children to the first parent
        addItem(parents[0], new NavigationListItem("0", "Week 1", "Designing and Planning"));
        addItem(parents[0], new NavigationListItem("1", "Week 2", "Prototyping and Early Building"));
        addItem(parents[0], new NavigationListItem("2", "Week 3", "Putting It All Together"));
        addItem(parents[0], new NavigationListItem("3", "Week 4", "Wiring and Testing"));
        addItem(parents[0], new NavigationListItem("4", "Week 5", "Programming and Tinkering"));
        addItem(parents[0], new NavigationListItem("5", "Week 6", "A Last Goodbye"));
        // Add children to the second parent
        addItem(parents[1], new NavigationListItem("0", "The Pits", "A Home for Your Robot"));
        addItem(parents[1], new NavigationListItem("1", "Scouting", "Your Eyes in the Sky"));
        addItem(parents[1], new NavigationListItem("2", "Chairman's", "The Core of FIRST"));
        addItem(parents[1], new NavigationListItem("3", "Spirit", "Loud and Proud"));
        // Add children to the third parent
        addItem(parents[2], new NavigationListItem("0", "Fundraising", "Providing Financial Stability"));
        addItem(parents[2], new NavigationListItem("1", "Tournaments", "Letting Your Robot Live Again"));
        addItem(parents[2], new NavigationListItem("2", "Outreach", "Spreading the Word"));
        addItem(parents[2], new NavigationListItem("3", "Preparing for Next Year", "A New Beginning"));
    }

    private static void addItem(NavigationListItem parent, NavigationListItem child) {

        if (!PARENTS.contains(parent)) { // Make sure we haven't added this parent already
            PARENTS.add(parent); // TODO: Replace this check with a constant or logarithmic time algorithm
        }

        while (CHILDREN.size() <= Integer.parseInt(parent.id)) { // If we haven't created an array for this data point
            CHILDREN.add(new ArrayList<NavigationListItem>()); // Add an empty array which we will populate in the future
        }
        CHILDREN.get(Integer.parseInt(parent.id)).add(child);

    }
}
