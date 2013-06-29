package com.rtzoeller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class WeekContent {
    public static List<Week> ITEMS = new ArrayList<Week>();
    public static Map<String, Week> ITEM_MAP = new HashMap<String, Week>();

    static {
        // Add the 6 weeks
        addItem(new Week("1","Week 1","Designing and Planning"));
        addItem(new Week("2","Week 2","Prototyping and Early Building"));
        addItem(new Week("3","Week 3","Building Modules"));
        addItem(new Week("4","Week 4","Assembling the Robot"));
        addItem(new Week("5","Week 5","Wiring and Testing"));
        addItem(new Week("6","Week 6","Programming and Driving"));
    }

    private static void addItem(Week item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
