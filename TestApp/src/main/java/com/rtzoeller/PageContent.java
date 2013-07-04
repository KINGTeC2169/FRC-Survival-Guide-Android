package com.rtzoeller;

import android.content.Context;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    public static Map<Key, Page> PAGE_MAP = new HashMap<Key, Page>();
    public static final int numPages[] = {3,0,0,0,0,0};

    PageContent(Context context) {
        PAGE_MAP.put(new Key(1, 0), new Page(context, R.array.brainstorming));
        PAGE_MAP.put(new Key(1, 1), new Page(context, R.array.approaching_the_game));
        PAGE_MAP.put(new Key(1, 2), new Page(context, R.array.game_rules));

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
