package com.kingtec2169.survivalguide;

/**
 * Created by rtzoeller on 6/24/13.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    Context context = null;
    int group;
    int child;

    public PagerAdapter(Context context, FragmentManager mgr, int group, int child) {
        super(mgr);
        this.context = context;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getCount() {
        // Return the number of pages bound to a specific id
        // The number of pages are stored in an array and we can
        // retrieve them by their id
        return PageContent.numPages[group][child];
    }

    @Override
    public Fragment getItem(int position) {
        // Returns a PageFragment holding the content based off of its id and position
        return(PageFragment.newInstance(group, child, position));
    }

    @Override
    public String getPageTitle(int position) {
        // Returns a title from the content it is displaying
        return(PageFragment.getTitle(context, group, child, position));
    }
}