package com.rtzoeller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class WeekContent {
    // Stores the items for the Week list
    public static List<Week> ITEMS = new ArrayList<Week>();
    public static Map<String, Week> ITEM_MAP = new HashMap<String, Week>();

    public static List<List<Week>> CHILDREN = new ArrayList<List<Week>>();
    public static List<Week> PARENTS = new ArrayList<Week>();

    static {
        // Add the 6 weeks
        Week[] parents = {new Week("0", "Build Season", ""), new Week("1","At Competition", "")};
        addItem(parents[0], new Week("0", "Week 1", "Designing and Planning"));
        addItem(parents[0], new Week("1","Week 2","Prototyping and Early Building"));
        addItem(parents[0], new Week("2","Week 3","Building Modules"));
        addItem(parents[0], new Week("3","Week 4","Assembling the Robot"));
        addItem(parents[0], new Week("4","Week 5","Wiring and Testing"));
        addItem(parents[0], new Week("5","Week 6","Programming and Driving"));

        addItem(parents[1], new Week("0","Setting up the Pit", "A Home for your Robot"));
    }

    private static void addItem(Week parent, Week child) {
        // TODO: Clean up old ListView implementation
        ITEMS.add(child);
        ITEM_MAP.put(child.id, child);

        if (!PARENTS.contains(parent)) { // Make sure we haven't added this parent already
            PARENTS.add(parent); // TODO: Replace this check with a constant or logarithmic time algorithm
        }

        while (CHILDREN.size() <= Integer.parseInt(parent.id)) { // If we haven't created an array for this data point
            CHILDREN.add(new ArrayList<Week>()); // Add an empty array which we will populate in the future
        }
        CHILDREN.get(Integer.parseInt(parent.id)).add(child);

    }

}
