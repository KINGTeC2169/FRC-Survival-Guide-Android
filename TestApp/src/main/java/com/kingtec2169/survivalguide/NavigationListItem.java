package com.kingtec2169.survivalguide;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class NavigationListItem {
    // Holder class for week data
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
