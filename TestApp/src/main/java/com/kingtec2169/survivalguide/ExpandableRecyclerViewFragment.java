package com.kingtec2169.survivalguide;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 7/21/14.
 */
public class ExpandableRecyclerViewFragment extends Fragment {

    public interface Callbacks {
        public boolean onChildClick(RecyclerView parent, View v, int groupPosition, int childPosition);
        public void onGroupExpand(int groupPosition);
        public void onGroupCollapse(int groupPosition);
    }

    private Callbacks mCallbacks;

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public static final String ARG_EXPANDED_ITEMS = "expanded_items";

    static List<ViewModel> items;

    static {
        List<ViewModel> buildSeasonChildren = new ArrayList<ViewModel>();
        buildSeasonChildren.add(new ViewModel(0, 0, "Week 1", "Designing and Planning", null));
        buildSeasonChildren.add(new ViewModel(0, 1, "Week 2", "Prototyping and Early Building", null));
        buildSeasonChildren.add(new ViewModel(0, 2, "Week 3", "Putting It All Together", null));
        buildSeasonChildren.add(new ViewModel(0, 3, "Week 4", "Wiring and Testing", null));
        buildSeasonChildren.add(new ViewModel(0, 4, "Week 5", "Programming and Tinkering", null));
        buildSeasonChildren.add(new ViewModel(0, 5, "Week 6", "A Last Goodbye", null));

        List<ViewModel> competitionChildren = new ArrayList<ViewModel>();
        competitionChildren.add(new ViewModel(1, 0, "The Pits", "A Home for Your Robot", null));
        competitionChildren.add(new ViewModel(1, 1, "Scouting", "Your Eyes in the Sky", null));
        competitionChildren.add(new ViewModel(1, 2, "Chairman's", "The Core of FIRST", null));
        competitionChildren.add(new ViewModel(1, 3, "Spirit", "Loud and Proud", null));

        List<ViewModel> offSeasonChildren = new ArrayList<ViewModel>();
        offSeasonChildren.add(new ViewModel(2, 0, "Fundraising", "Providing Financial Stability", null));
        offSeasonChildren.add(new ViewModel(2, 1, "Tournaments", "Letting Your Robot Live Again", null));
        offSeasonChildren.add(new ViewModel(2, 2, "Outreach", "Spreading the Word", null));
        offSeasonChildren.add(new ViewModel(2, 3, "Preparing for Next Year", "A New Beginning", null));

        items = new ArrayList<ViewModel>();
        items.add(new ViewModel(0, -1, "Build Season", "", buildSeasonChildren));
        items.add(new ViewModel(1, -1, "Competition", "", competitionChildren));
        items.add(new ViewModel(2, -1, "Off-Season", "", offSeasonChildren));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // TODO: Handle loading of already expanded items i.e. from rotation

        View rootView = inflater.inflate(R.layout.fragment_expandable_week_list, container, false);

        final TestRecyclerViewAdapter adapter = new TestRecyclerViewAdapter(items, R.layout.two_line_row);
        final RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ViewModel>() {
            @Override public void onItemClick(View view, ViewModel viewModel) {
                if (viewModel.isGroupHeader()) {
                    // We should change the elements displayed
                    if (viewModel.isExpanded()) {
                        // Collapse
                        viewModel.setExpanded(false);
                        mCallbacks.onGroupCollapse(viewModel.getGroupId());
                        for (ViewModel child : viewModel.getChildren()) {
                            adapter.remove(child);
                        }
                    } else {
                        // Expand
                        viewModel.setExpanded(true);
                        mCallbacks.onGroupExpand(viewModel.getGroupId());
                        // Position of the header item
                        int basePosition = recyclerView.getChildPosition(view);
                        for (int i = 0; i < viewModel.getChildren().size(); i++) {
                            adapter.add(viewModel.getChildren().get(i), basePosition + 1 + i);
                        }
                    }
                } else {
                    // We should signal that new content has been selected
                    mCallbacks.onChildClick(recyclerView, view, viewModel.getGroupId(), viewModel.getChildId());
                }
            }
        });

        return rootView;
    }
}
