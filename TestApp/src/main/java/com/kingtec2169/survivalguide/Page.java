package com.kingtec2169.survivalguide;

import android.content.Context;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class Page {
    // Holder class for the Pages in the ViewPager
    public String text;
    public String title;
    public int imageResourceId;
    public String tags;

    public Page(Context context, String[] contentResources) { // Accepts a calling context and an array of the page data
        super();
        if(contentResources.length == 4) { // Make sure we have received a valid array
            this.title = contentResources[0];
            this.text = contentResources[1];
            this.imageResourceId = context.getResources().getIdentifier(contentResources[2], "drawable", context.getPackageName());
            this.tags = contentResources[3];
        }
    }

    public Page(Context context, int arrayResourceId) { // Accepts a calling context and a resource id for the array of the page data
        this(context, context.getResources().getStringArray(arrayResourceId));

    }

}
