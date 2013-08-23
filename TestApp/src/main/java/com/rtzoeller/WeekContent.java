package com.rtzoeller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class WeekContent {
    // Stores the items for the Week list

    public static List<List<Week>> CHILDREN = new ArrayList<List<Week>>();
    public static List<Week> PARENTS = new ArrayList<Week>();

    static {
        // Add the 6 weeks
        Week[] parents = {new Week("0", "Build Season", ""), new Week("1", "Competition", ""), new Week("2", "Off-Season", "")};
        addItem(parents[0], new Week("0", "Week 1", "Designing and Planning"));
        addItem(parents[0], new Week("1", "Week 2", "Prototyping and Early Building"));
        addItem(parents[0], new Week("2", "Week 3", "Putting It All Together"));
        addItem(parents[0], new Week("3", "Week 4", "Wiring and Testing"));
        addItem(parents[0], new Week("4", "Week 5", "Programming and Tinkering"));
        addItem(parents[0], new Week("5", "Week 6", "A Last Goodbye"));

        addItem(parents[1], new Week("0", "The Pits", "A Home for Your Robot"));
        addItem(parents[1], new Week("1", "Scouting", "Your Eyes in the Sky"));
        addItem(parents[1], new Week("2", "Chairman's", "The Core of FIRST"));
        addItem(parents[1], new Week("3", "Spirit", "Loud and Proud"));
        addItem(parents[1], new Week("4", "Competing in the Game", "The Exciting Part!"));

        addItem(parents[2], new Week("0", "Fundraising", "Providing Financial Stability"));
        addItem(parents[2], new Week("1", "Tournaments", "Letting Your Robot Live Again"));
        addItem(parents[2], new Week("2", "Outreach", "Spreading the Word"));
        addItem(parents[2], new Week("3", "Preparing for Next Year", "A New Beginning"));
    }

    private static void addItem(Week parent, Week child) {

        if (!PARENTS.contains(parent)) { // Make sure we haven't added this parent already
            PARENTS.add(parent); // TODO: Replace this check with a constant or logarithmic time algorithm
        }

        while (CHILDREN.size() <= Integer.parseInt(parent.id)) { // If we haven't created an array for this data point
            CHILDREN.add(new ArrayList<Week>()); // Add an empty array which we will populate in the future
        }
        CHILDREN.get(Integer.parseInt(parent.id)).add(child);

    }

}
