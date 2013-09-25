package com.kingtec2169.survivalguide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class NavigationListContent {
    /** This class holds the content for the {@link ExpandableListNavigationFragment} **/
    // The children of the ExpandableListView, holds the contents of each group
    public static List<List<NavigationListItem>> CHILDREN = new ArrayList<List<NavigationListItem>>();
    // The parents of the ExpandableListView, holds the head item of each group
    public static List<NavigationListItem> PARENTS = new ArrayList<NavigationListItem>();

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
        addItem(parents[1], new NavigationListItem("4", "Competing in the Game", "The Exciting Part!"));
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
