package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This class is an adapter used to bind fragments holding content to the
// ViewPager. If the device is a 10" tablet two pages of content are to be shown,
// so the PageFragmentContainer replaces the singular PageFragment.

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    Context context = null;
    final int group;
    final int child;
    final boolean combineContent;

    public PagerAdapter(Context context, FragmentManager mgr, int group, int child, boolean combineContent) {
        super(mgr);
        this.context = context;
        this.group = group;
        this.child = child;
        this.combineContent = combineContent;
    }

    @Override
    public int getCount() {
        // Return the number of pages bound to a specific id
        // The number of pages are stored in an array and we can
        // retrieve them by their id
        if (combineContent) {
            // Add one so that if we have an unpaired page it doesn't get hidden
            return (PageContent.numPages[group][child] + 1)/ 2;
        } else {
            return PageContent.numPages[group][child];
        }
    }

    @Override
    public Fragment getItem(int position) {
        // Returns a PageFragment holding the content based off of its id and position
        PageFragmentContainer container = new PageFragmentContainer();
        if(combineContent) {
            // Set the left page of content
            // This page is guaranteed to exist by the ViewPager
            container.setLeft(PageFragment.newInstance(group, child, position * 2));

            // Don't show non-existent content
            // The right page may not exist if the left pane is the last entry
            if((position * 2 + 1) < PageContent.numPages[group][child]) {
                container.setRight(PageFragment.newInstance(group, child, position * 2 + 1));
            }
            return(container);
        } else {
            // We only are showing one page of content because this is a small device
            return PageFragment.newInstance(group, child, position);
        }
    }

    @Override
    public String getPageTitle(int position) {
        // Returns a title from the content it is displaying

        // Only show two titles if we are combining content
        if(combineContent) {
            // Check if we are showing the last page
            if(position * 2 + 1 < PageContent.numPages[group][child]) {
                return PageFragment.getTitle(context, group, child, position * 2) + " | "
                        + PageFragment.getTitle(context, group, child, position * 2 + 1);
            } else {
                return PageFragment.getTitle(context, group, child, position * 2);
            }
        } else {
            // We are not on a large tablet, so titles are not combined
            return PageFragment.getTitle(context, group, child, position);
        }
    }
}