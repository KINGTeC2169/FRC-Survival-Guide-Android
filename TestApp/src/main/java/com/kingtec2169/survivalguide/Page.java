package com.kingtec2169.survivalguide;

import android.content.Context;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class Page {
    // Holder class for the Pages in the ViewPager
    public int layoutResourceId;
    public String text;
    public String title;
    public int imageResourceId;
    public String tags;

    public Page(Context context, String[] contentResources) { // Accepts a calling context and an array of the page data
        super();
        if(contentResources.length == 5) { // Make sure we have received a valid array
            this.layoutResourceId = context.getResources().getIdentifier(contentResources[0], "layout", context.getPackageName());
            this.title = contentResources[1];
            this.text = contentResources[2];
            this.imageResourceId = context.getResources().getIdentifier(contentResources[3], "drawable", context.getPackageName());
            this.tags = contentResources[4];
        }
    }

    public Page(Context context, int arrayResourceId) { // Accepts a calling context and a resource id for the array of the page data
        this(context, context.getResources().getStringArray(arrayResourceId));

    }

}
