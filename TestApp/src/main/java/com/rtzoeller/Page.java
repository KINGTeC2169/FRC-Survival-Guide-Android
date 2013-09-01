package com.rtzoeller;

import android.content.Context;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class Page {
    // Holder class for the Pages in the ViewPager
    public String text;
    public String title;
    public int imageResourceId;

    public Page() { // Default constructor, elements would be set later
        super();
    }

    public Page(Context context, String[] contentResources) { // Accepts a calling context and an array of the page data
        super();
        if(contentResources.length == 3) { // Make sure we have received a valid array
            this.title = contentResources[0];
            this.text = contentResources[1];
            this.imageResourceId = context.getResources().getIdentifier(contentResources[2], "drawable", context.getPackageName());
        }
    }

    public Page(Context context, int arrayResourceId) { // Accepts a calling context and a resource id for the array of the page data
        this(context, context.getResources().getStringArray(arrayResourceId));

    }

    public Page(String title, String text, int imageResourceId) {  // Accepts two strings and an image resource id
        super();
        this.title = title;
        this.text = text;
        this.imageResourceId = imageResourceId;
    }
}
