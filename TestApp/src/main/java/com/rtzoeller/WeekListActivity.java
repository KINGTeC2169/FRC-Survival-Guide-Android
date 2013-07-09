package com.rtzoeller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;

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
        setContentView(R.layout.activity_week_twopane);


        mTwoPane = getResources().getBoolean(R.bool.has_two_panes);

        if (savedInstanceState != null) {
            // Load the week id that we should display
            id = savedInstanceState.getString(ARG_ID);
        }
        WeekListFragment fragment = new WeekListFragment();
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.week_list_container, fragment)
            .commit();

        if (mTwoPane) {
            findViewById(R.id.week_detail_container).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.week_detail_container).setVisibility(View.GONE);
        }


        onItemSelected(id); // Set the detail fragment to display the correct id
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

                Bundle arguments = new Bundle();
                arguments.putString(WeekDetailFragment.ARG_ITEM_ID, id);
                WeekDetailFragment fragment = new WeekDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.week_list_container, fragment)
                        .commit();
                setTitle(WeekContent.ITEM_MAP.get(id).name);
                getActionBar().setDisplayHomeAsUpEnabled(true);

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
        state.putString(ARG_ID, id); // Save the id of the week we are displaying
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                WeekListFragment fragment = new WeekListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.week_list_container, fragment)
                        .commit();

                id = null;
                getActionBar().setDisplayHomeAsUpEnabled(false);
                setTitle(R.string.app_name);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
