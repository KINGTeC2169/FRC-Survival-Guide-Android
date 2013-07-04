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
        return PageContent.numPages[id - 1];
    }

    @Override
    public Fragment getItem(int position) {
        return(PageFragment.newInstance(id, position));
    }

    @Override
    public String getPageTitle(int position) {
        return(PageFragment.getTitle(context, id, position));
    }
}