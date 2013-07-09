package com.rtzoeller;

/**
 * Created by rtzoeller on 6/24/13.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    Context context = null;
    int id;

    public PagerAdapter(Context context, FragmentManager mgr, int id) {
        super(mgr);
        this.context = context;
        this.id = id;
    }

    @Override
    public int getCount() {
        // Return the number of pages bound to a specific id
        // The number of pages are stored in an array and we can
        // retrieve them by their id
        // The id's are 1 based while the array is 0 based so we
        // need to subtract 1.
        return PageContent.numPages[id - 1];
    }

    @Override
    public Fragment getItem(int position) {
        // Returns a PageFragment holding the content based off of its id and position
        return(PageFragment.newInstance(id, position));
    }

    @Override
    public String getPageTitle(int position) {
        // Returns a title from the content it is displaying
        return(PageFragment.getTitle(context, id, position));
    }
}