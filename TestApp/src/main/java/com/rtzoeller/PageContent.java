package com.rtzoeller;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    public final static Map<Key, Page> PAGE_MAP = new HashMap<Key, Page>();

    static {
        PAGE_MAP.put(new Key(1, 0), new Page("Kickoff", "Kickoff!!!", R.drawable.ic_kt));
        PAGE_MAP.put(new Key(1, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_kt));
        PAGE_MAP.put(new Key(1, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_kt));

        PAGE_MAP.put(new Key(2, 0), new Page("Kickoff", "This is a test message about Kickoff", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(2, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(2, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_launcher));

        PAGE_MAP.put(new Key(3, 0), new Page("Kickoff", "This is a test message about Kickoff", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(3, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(3, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_launcher));

        PAGE_MAP.put(new Key(4, 0), new Page("Kickoff", "This is a test message about Kickoff", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(4, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(4, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_launcher));

        PAGE_MAP.put(new Key(5, 0), new Page("Kickoff", "This is a test message about Kickoff", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(5, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(5, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_launcher));

        PAGE_MAP.put(new Key(6, 0), new Page("Kickoff", "This is a test message about Kickoff", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(6, 1), new Page("Approaching The Game", "This is a test message about Approaching The Game", R.drawable.ic_launcher));
        PAGE_MAP.put(new Key(6, 2), new Page("Robot Design", "This is a test message about Robot Design", R.drawable.ic_launcher));
    }

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
