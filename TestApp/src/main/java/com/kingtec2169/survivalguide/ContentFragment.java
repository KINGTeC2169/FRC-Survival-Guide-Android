package com.kingtec2169.survivalguide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private ViewPager mViewPager = null;
    private int mSetPage = 0;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Is there a specific page we should display
        if (getArguments().containsKey(ARG_PAGE_ID)) {
            mSetPage = getArguments().getInt(ARG_PAGE_ID);
        }

        // If we have access to an ActionBar,
        // display tabs representing the pages available
        ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        if (getArguments().containsKey(ARG_GROUP_ID) && getArguments().containsKey(ARG_CHILD_ID) && actionBar != null) {
            int group = getArguments().getInt(ARG_GROUP_ID);
            int child = getArguments().getInt(ARG_CHILD_ID);

            // TODO: Move ActionBar calls to the activity
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            actionBar.removeAllTabs();

            ActionBar.TabListener tabListener = new ActionBar.TabListener() {
                @Override
                public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                    // When the tab is selected, switch to the
                    // corresponding page in the ViewPager.
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }
                }

                @Override
                public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                }

                @Override
                public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

                }
            };

            // Add a tab for each page of the set
            // Using the titles from each page.
            for (int i = 0; i < PageContent.numPages[group][child]; i++) {
                Log.e("TitleOf", "" + group + " " + child + " " + i);
                actionBar.addTab(actionBar.newTab()
                        .setText(PageFragment.getTitle(getActivity(), group, child, i))
                        .setTabListener(tabListener));
            }

            // Don't set the page of a blank pager
            // doing so will crash the app
            if (PageContent.numPages[group][child] != 0) {
                // Select the correct page
                // This becomes necessary when using the Search function in the app
                actionBar.setSelectedNavigationItem(mSetPage);
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

        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between tabs, set the correct tab as selected
                super.onPageSelected(position);
                if (getActivity() != null) {
                    ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
                    if (actionBar!= null) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
            }
        });
        mViewPager = pager;
        return(result);
    }

    private android.support.v4.view.PagerAdapter buildAdapter() {
        return(new PagerAdapter(getActivity(), getChildFragmentManager(), getArguments().getInt(ARG_GROUP_ID), getArguments().getInt(ARG_CHILD_ID)));
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

    public static Bundle createBundle(int group, int child, int page) {
        // Create a bundle to be passed to this fragment
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_GROUP_ID, group);
        bundle.putInt(ARG_CHILD_ID, child);
        bundle.putInt(ARG_PAGE_ID, page);
        return bundle;
    }
}
