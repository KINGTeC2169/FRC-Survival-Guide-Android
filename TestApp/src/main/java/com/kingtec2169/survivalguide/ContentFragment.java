package com.kingtec2169.survivalguide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    /**
     * The dummy content this fragment is presenting.
     */
    private NavigationListItem mItem;
    private ViewPager mViewPager = null;
    private int mSetPage;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContentFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_CHILD_ID)) {
            mItem = NavigationListContent.CHILDREN.get(getArguments().getInt(ARG_GROUP_ID)).get(getArguments().getInt(ARG_CHILD_ID));
        }
        if (getArguments().containsKey(ARG_PAGE_ID)) {
            mSetPage = getArguments().getInt(ARG_PAGE_ID);
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
