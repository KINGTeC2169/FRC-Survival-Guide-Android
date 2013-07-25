package com.rtzoeller;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    // Stores the items for the Pages
    public static Map<Key, Page> PAGE_MAP = new HashMap<Key, Page>();
    // Number of pages in each week
    public static final int numPages[] = {3,1,0,0,0,0};
    // Which content is currently in memory
    private static int loaded;
    // The context we should load our resources from
    private static Context mContext;

    // Load the pages from the application resources
    PageContent(Context context) {
        if (PAGE_MAP.isEmpty()) { // If no content has been loaded set the loaded variable to reflect that
            loaded = -1;
        }

        mContext = context; // Update our context for loading resources
    }

    public static Page get (int id, int position) {
        if (loaded != id) {
            unload(loaded);
            load(id);
        }
        return PAGE_MAP.get(new Key(id, position));
    }

    private static void load (int id) {
        loaded = id;
        switch (id) {
            case 0:
                PAGE_MAP.put(new Key(0, 0), new Page(mContext, R.array.brainstorming));
                PAGE_MAP.put(new Key(0, 1), new Page(mContext, R.array.approaching_the_game));
                PAGE_MAP.put(new Key(0, 2), new Page(mContext, R.array.game_rules));
                break;
            case 1:
                PAGE_MAP.put(new Key(1, 0), new Page(mContext, R.array.prototyping));
                break;
        }
        Log.d("com.rtzoeller", "Load " + Integer.toString(id));
    }

    private static void unload (int id) {
        loaded = -1;
        switch (id) {
            case 0:
                PAGE_MAP.remove(new Key(0, 0));
                PAGE_MAP.remove(new Key(0, 1));
                PAGE_MAP.remove(new Key(0, 2));
                break;
            case 1:
                PAGE_MAP.remove(new Key(1, 0));
                break;
        }
        Log.d("com.rtzoeller", "Unload " + Integer.toString(id));
    }

    // A Key implementation allowing us to do a 2d lookup using a HashMap
    static class Key {

        private final int x;
        private final int y;

        public Key(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (x != key.x) return false;
            if (y != key.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
