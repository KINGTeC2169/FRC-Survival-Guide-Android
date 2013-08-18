package com.rtzoeller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rtzoeller on 8/17/13.
 */
public class WeekExpandableListFragment extends Fragment {
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeekExpandableListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_expandable_week_list, container, false);
        ((ExpandableListView)result.findViewById(R.id.expandableListView)).setAdapter(buildAdapter());
        return result;
    }

    private SimpleExpandableListAdapter buildAdapter() {
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

//        for (int i = 0; i < WeekContent.ITEMS.size(); i++) {
//            Map<String, String> curGroupMap = new HashMap<String, String>();
//            groupData.add(curGroupMap);
//            curGroupMap.put(NAME, WeekContent.ITEMS.get(i).name);
//            curGroupMap.put(DESCRIPTION, (i % 2 == 0) ? "This group is even" : "This group is odd");
//
//            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
//            for (int j = 0; j < 5; j++) {
//                Map<String, String> curChildMap = new HashMap<String, String>();
//                children.add(curChildMap);
//                // curChildMap.put(NAME, "Child " + j);
//                curChildMap.put(DESCRIPTION, (j % 2 == 0) ? "Hello " + j: "Good Morning "+ j);
//            }
//            childData.add(children);
//        }

        for (int i = 0; i < WeekContent.PARENTS.size(); i++) {
            Map<String, String> curGroupMap = new HashMap<String, String>();
            groupData.add(curGroupMap);
            Week parent = WeekContent.PARENTS.get(i);

            curGroupMap.put(NAME, parent.name);
            curGroupMap.put(DESCRIPTION, parent.description);

            List<Map<String, String>> children = new ArrayList<Map<String, String>>();
            for (int j = 0; j < WeekContent.CHILDREN.get(Integer.parseInt(parent.id)).size(); j++) {
                Map<String, String> curChildMap = new HashMap<String, String>();
                children.add(curChildMap);

                Week child = WeekContent.CHILDREN.get(i).get(j);
                curChildMap.put(NAME, child.name);
                curChildMap.put(DESCRIPTION, child.description);
            }
            childData.add(children);
        }

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(getActivity(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                new String[] { NAME, DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2 },
                childData,
                android.R.layout.simple_expandable_list_item_2,
                new String[] { NAME, DESCRIPTION},
                new int[] { android.R.id.text1, android.R.id.text2 });

        return adapter;
    }

}
