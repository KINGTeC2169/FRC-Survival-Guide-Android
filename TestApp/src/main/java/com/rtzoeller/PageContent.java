package com.rtzoeller;

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
    public static final int numPages[] = {3,1,0,0,0,0};

    // Load the pages from the application resources
    PageContent(Context context) {
        if (PAGE_MAP.isEmpty()) { // Only fill the map once
            PAGE_MAP.put(new Key(1, 0), new Page(context, R.array.brainstorming));
            PAGE_MAP.put(new Key(1, 1), new Page(context, R.array.approaching_the_game));
            PAGE_MAP.put(new Key(1, 2), new Page(context, R.array.game_rules));

            PAGE_MAP.put(new Key(2, 0), new Page(context, R.array.prototyping));
        }
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
