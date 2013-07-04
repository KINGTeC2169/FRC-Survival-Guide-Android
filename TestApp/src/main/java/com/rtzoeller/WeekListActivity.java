package com.rtzoeller;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * An activity representing a list of Weeks. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WeekDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link WeekListFragment} and the item details
 * (if present) is a {@link WeekDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link WeekListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class WeekListActivity extends FragmentActivity
        implements WeekListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private String id = null;
    private static final String ARG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_list);



        if (findViewById(R.id.week_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((WeekListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.week_list))
                    .setActivateOnItemClick(true);
        }

        if (savedInstanceState != null) {
            id = savedInstanceState.getString(ARG_ID);

        }
        onItemSelected(id);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link WeekListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        this.id = id;
        if (id != null) {
            // Make sure we have a week selected and we aren't just
            // feeding through a null value from our saved state
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                Bundle arguments = new Bundle();
                arguments.putString(WeekDetailFragment.ARG_ITEM_ID, id);
                WeekDetailFragment fragment = new WeekDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.week_detail_container, fragment)
                        .commit();

            } else  {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                Intent detailIntent = new Intent(this, WeekDetailActivity.class);
                detailIntent.putExtra(WeekDetailFragment.ARG_ITEM_ID, id);
                startActivity(detailIntent);
            }
        } else if (mTwoPane) {
            // We have no week to display but we can still show the user a screen
            // prompting them to pick a week
            PromptSelectWeekFragment fragment = new PromptSelectWeekFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.week_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(ARG_ID, id);
    }
}
