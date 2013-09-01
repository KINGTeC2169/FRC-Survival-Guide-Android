package com.kingtec2169.survivalguide;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    // Stores the items for the Pages
    public static List<Page> PAGES = new ArrayList<Page>();
    // Number of pages in each week
    public static final int numPages[][] = {
            {3,3,0,0,0,0},
            {0,0,0,0,0},
            {0,0,0,0}};
    // Which content is currently in memory
    private static int loaded_group;
    private static int loaded_child;
    // The context we should load our resources from
    private static Context mContext;

    // Load the pages from the application resources
    PageContent(Context context) {
        if (PAGES.isEmpty()) { // If no content has been loaded set the loaded variable to reflect that
            loaded_group = -1;
            loaded_child = -1;
        }

        mContext = context; // Update our context for loading resources
    }

    public static Page get (int group, int child, int position) {
        if (loaded_group != group || loaded_child != child) {
            unload(loaded_group, loaded_child);
            load(group, child);
        }
        return PAGES.get(position);
    }

    private static void load (int group, int child) {
        loaded_group = group;
        loaded_child = child;
        switch (group) {
            case 0:
                switch (child) {
                    case 0:
                        PAGES.add(new Page(mContext, R.array.brainstorming));
                        PAGES.add(new Page(mContext, R.array.approaching_the_game));
                        PAGES.add(new Page(mContext, R.array.game_rules));
                        break;
                    case 1:
                        PAGES.add(new Page(mContext, R.array.getting_materials));
                        PAGES.add(new Page(mContext, R.array.prototyping));
                        PAGES.add(new Page(mContext, R.array.chassis_building));
                        break;
                }
                break;
        }
    }

    private static void unload (int group, int child) {
        loaded_group = -1;
        loaded_child = -1;
        // Clear array to be refilled with new data
        PAGES.clear();
    }
}
