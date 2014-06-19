package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This class acts as a holder for the list items in the ExpandableListNavigationFragment.

public class NavigationListItem {
    public String name;
    public String description;
    public String id;

    public NavigationListItem() {
        super();
    }

    public NavigationListItem(String id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
