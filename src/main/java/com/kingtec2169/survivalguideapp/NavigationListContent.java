package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
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
        NavigationListItem[] parents = {new NavigationListItem("0", "Departments", ""), new NavigationListItem("1", "Competitions", ""), new NavigationListItem("2", "Other Topics", "")};
        // Add children to the first parent
        addItem(parents[0], new NavigationListItem("0", "Mechanical", ""));
        addItem(parents[0], new NavigationListItem("1", "Programming", ""));
        addItem(parents[0], new NavigationListItem("2", "Electrical", ""));
        addItem(parents[0], new NavigationListItem("3", "CAD", ""));
        addItem(parents[0], new NavigationListItem("4", "Strategy", ""));
        addItem(parents[0], new NavigationListItem("5", "Spirit", ""));
        addItem(parents[0], new NavigationListItem("6", "Public Relations", ""));
        // Add children to the second parent
        addItem(parents[1], new NavigationListItem("0", "Introduction into Competitions", ""));
        addItem(parents[1], new NavigationListItem("1", "Inspections", ""));
        addItem(parents[1], new NavigationListItem("2", "Regional Competitions", ""));
        addItem(parents[1], new NavigationListItem("3", "District Competitions", ""));

        // Add children to the third parent
        addItem(parents[2], new NavigationListItem("0", "Drive Team", ""));
        addItem(parents[2], new NavigationListItem("1", "Stop Build Day", ""));
        addItem(parents[2], new NavigationListItem("2", "Mentors", ""));
        addItem(parents[2], new NavigationListItem("3", "Safety", ""));
        addItem(parents[2], new NavigationListItem("4", "Fundraising", ""));


    }

    private static void addItem(NavigationListItem parent, NavigationListItem child) {

        if (!PARENTS.contains(parent)) { // Make sure we haven't added this parent already
            PARENTS.add(parent);
        }
 while (CHILDREN.size() <= 100) { //Integer.parseInt(parent.id)) {
      //  if (CHILDREN.get(Integer.parseInt(parent.id)).size() <1 ) { // If we haven't created an array for this data point
            CHILDREN.add(new ArrayList<NavigationListItem>()); // Add an empty array which we will populate in the future
        }
        CHILDREN.get(Integer.parseInt(parent.id)).add(child);

    }
}
