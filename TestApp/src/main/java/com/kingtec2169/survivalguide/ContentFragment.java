package com.kingtec2169.survivalguide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

/**
 * A fragment representing a single Week detail screen.
 * This fragment is contained in a {@link MainActivity}
 */
public class ContentFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_GROUP_ID = "group_id";
    public static final String ARG_CHILD_ID = "child_id";
    public static final String ARG_PAGE_ID = "page_id";
    public static final String ARG_COMBINE_CONTENT_ID = "combine_content_id";

    private ViewPager mViewPager = null;
    private int mSetPage = 0;

    private boolean combineContent;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Should we show two pages full of content
        if (getArguments().containsKey(ARG_COMBINE_CONTENT_ID)) {
            combineContent = getArguments().getBoolean(ARG_COMBINE_CONTENT_ID);
        }
        // Is there a specific page we should display
        if (getArguments().containsKey(ARG_PAGE_ID)) {
            mSetPage = getArguments().getInt(ARG_PAGE_ID);
            if (combineContent) {
                mSetPage /= 2;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.pager, container, false);
        ViewPager pager = (ViewPager)result.findViewById(R.id.pager);
        pager.setAdapter(buildAdapter());
        pager.setCurrentItem(mSetPage);
        // If the device we are on supports View operations such as resizing and setting alpha,
        // then apply these effects to the pages. On versions older than Honeycomb, these transformations
        // do not exist.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            pager.setPageTransformer(true, new ZoomOutPageTransformer());
        }

        // If there is content showing show the tabs.
        // If no content is visible attempting to show tabs
        // will crash the app.
        if(pager.getAdapter().getCount() != 0) {
            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip)result.findViewById(R.id.tabs);
            tabs.setViewPager(pager);
        }

        mViewPager = pager;
        return(result);
    }

    private android.support.v4.view.PagerAdapter buildAdapter() {
        return(new PagerAdapter(getActivity(), getChildFragmentManager(), getArguments().getInt(ARG_GROUP_ID), getArguments().getInt(ARG_CHILD_ID), combineContent));
    }

    public int getPage() {
        if (mViewPager != null) {
            return mViewPager.getCurrentItem();
        } else {
            return mSetPage;
        }
    }

    public Bundle saveState() {
        // Save the state of the fragment so we can recreate it later
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_GROUP_ID, getArguments().getInt(ARG_GROUP_ID));
        bundle.putInt(ARG_CHILD_ID, getArguments().getInt(ARG_CHILD_ID));
        bundle.putInt(ARG_PAGE_ID, getPage());
        return bundle;
    }

    public static Bundle createBundle(int group, int child, int page, boolean combineContent) {
        // Create a bundle to be passed to this fragment
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_GROUP_ID, group);
        bundle.putInt(ARG_CHILD_ID, child);
        bundle.putInt(ARG_PAGE_ID, page);
        bundle.putBoolean(ARG_COMBINE_CONTENT_ID, combineContent);
        return bundle;
    }
}
