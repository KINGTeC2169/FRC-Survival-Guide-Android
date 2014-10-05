package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This class acts as a holder for the pages used to display content. It is responsible for
// parsing which layout to use for a page and keeping that content in memory, to avoid reading
// repeatedly from XML.

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;

public class Page {
    // How many items this entry should have
    public int expectedLength;
    // Information loaded from XML
    public int layoutResourceId;
    public String text;
    public String title;
    public int imageResourceId;
    public String tags;

    public Page(Context context, String[] contentResources) { // Accepts a calling context and an array of the page data
        super();

        if(contentResources.length >= 1) {
            // Make sure there is an entry for the layout of the expected view
            // This will tell us how long of an XML array we should expect

            // Get the layout resource id from the name stored in XML
            layoutResourceId = context.getResources().getIdentifier(contentResources[0], "layout", context.getPackageName());
            // Look up the expected length for this layout
            expectedLength = getExpectedLength(layoutResourceId);

            // Make sure the expected length matches what we are actually working with
            if(contentResources.length == expectedLength) {
                PageLayout pageLayout;
                // Store the XML data
                switch (layoutResourceId) {
                    case R.layout.fragment_pager_item:
                        pageLayout = new PageLayoutTextAndImage();
                        break;
                    case R.layout.fragment_pager_item_no_image:
                        pageLayout = new PageLayoutTextOnly();
                        break;
                    default:
                        pageLayout = new PageLayoutTextOnly(); // Use a simple definition
                }
                pageLayout.setupPage(this, contentResources, context);
            }
        } else {
            // Something is wrong with the XML data
            expectedLength = 0;
        }
    }

    public Page(Context context, @ArrayRes int arrayResourceId) { // Accepts a calling context and a resource id for the array of the page data
        this(context, context.getResources().getStringArray(arrayResourceId));
    }

    public static int getExpectedLength(@IdRes int layoutResourceId) {
        PageLayout pageLayout;
        // These numbers should include the layout tag in their length
        switch (layoutResourceId) {
            case R.layout.fragment_pager_item:
                pageLayout = new PageLayoutTextAndImage();
                break;
            case R.layout.fragment_pager_item_no_image:
                pageLayout = new PageLayoutTextOnly();
                break;
            default:
                // Something is wrong
                return 0;
        }
        return pageLayout.getNumParameters();
    }

}
