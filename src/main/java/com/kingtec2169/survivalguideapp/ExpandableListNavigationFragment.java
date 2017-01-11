package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// A fragment containing a list of headers and content groups in an ExpandableList. This
// fragment requires managing activities to implement a series of callbacks used to pass
// click events.

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListNavigationFragment extends Fragment implements OnChildClickListener, OnGroupExpandListener, OnGroupCollapseListener {
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    public static final String ARG_EXPANDED_ITEMS = "expanded_items";
    private boolean[] setExpandedItems;

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id);
        // Callbacks to record which groups are expanded
        public void onGroupExpand(int groupPosition);
        public void onGroupCollapse(int groupPosition);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static final Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            return true;
        }

        @Override
         public void onGroupExpand(int groupPosition) {
        }

        @Override
        public void onGroupCollapse(int groupPosition) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExpandableListNavigationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setExpandedItems = new boolean[NavigationListContent.PARENTS.size()];
        if (getArguments().containsKey(ARG_EXPANDED_ITEMS)) {
            setExpandedItems = getArguments().getBooleanArray(ARG_EXPANDED_ITEMS);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_expandable_week_list, container, false);

        ExpandableListView expandableListView = (ExpandableListView)result.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(buildAdapter());
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupExpandListener(this);
        expandableListView.setOnGroupCollapseListener(this);
        for (int i = 0; i < setExpandedItems.length; i++) {
            if (setExpandedItems[i]) {
                expandableListView.expandGroup(i);
            }
        }
        return result;
    }

    private SimpleExpandableListAdapter buildAdapter() {
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

        for (int i = 0; i < NavigationListContent.PARENTS.size(); i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            NavigationListItem parent = NavigationListContent.PARENTS.get(i);

            curGroupMap.put(NAME, parent.name);
            curGroupMap.put(DESCRIPTION, parent.description);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < NavigationListContent.CHILDREN.get(Integer.parseInt(parent.id)).size(); j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);

                NavigationListItem child = NavigationListContent.CHILDREN.get(i).get(j);
                curChildMap.put(NAME, child.name);
                curChildMap.put(DESCRIPTION, child.description);
            }
            childData.add(children);
        }

        return new SimpleExpandableListAdapter(getActivity(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                //android.R.layout.simple_expandable_list_item_2,
                R.layout.expandable_list_view_row,
                new String[] { NAME, DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2 });
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        mCallbacks.onChildClick(parent, v, groupPosition, childPosition, id);
        return true;
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        mCallbacks.onGroupExpand(groupPosition);
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        mCallbacks.onGroupCollapse(groupPosition);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }
}
