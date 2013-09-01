package com.kingtec2169.survivalguide;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    // Stores the items for the Pages
    public static Map<Key, Page> PAGE_MAP = new HashMap<Key, Page>();
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
        if (PAGE_MAP.isEmpty()) { // If no content has been loaded set the loaded variable to reflect that
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
        return PAGE_MAP.get(new Key(group, child, position));
    }

    private static void load (int group, int child) {
        loaded_group = group;
        loaded_child = child;
        switch (group) {
            case 0:
                switch (child) {
                    case 0:
                        PAGE_MAP.put(new Key(0, 0, 0), new Page(mContext, R.array.brainstorming));
                        PAGE_MAP.put(new Key(0, 0, 1), new Page(mContext, R.array.approaching_the_game));
                        PAGE_MAP.put(new Key(0, 0, 2), new Page(mContext, R.array.game_rules));
                        break;
                    case 1:
                        PAGE_MAP.put(new Key(0, 1, 0), new Page(mContext, R.array.getting_materials));
                        PAGE_MAP.put(new Key(0, 1, 1), new Page(mContext, R.array.prototyping));
                        PAGE_MAP.put(new Key(0, 1, 2), new Page(mContext, R.array.chassis_building));
                        break;
                }
                break;
        }
    }

    private static void unload (int group, int child) {
        loaded_group = -1;
        loaded_child = -1;
        switch (group) {
            case 0:
                switch (child) {
                    case 0:
                        PAGE_MAP.remove(new Key(0, 0, 0));
                        PAGE_MAP.remove(new Key(0, 0, 1));
                        PAGE_MAP.remove(new Key(0, 0, 2));
                        break;
                    case 1:
                        PAGE_MAP.remove(new Key(0, 1, 0));
                        PAGE_MAP.remove(new Key(0, 1, 1));
                        PAGE_MAP.remove(new Key(0, 1, 2));
                        break;
                }
                break;
        }
    }

    // A Key implementation allowing us to do a 3d lookup using a HashMap
    static class Key {

        private final int x;
        private final int y;
        private final int z;

        public Key(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (x != key.x) return false;
            if (y != key.y) return false;
            if (z != key.z) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            result = 31 * result + z;
            return result;
        }
    }
}
