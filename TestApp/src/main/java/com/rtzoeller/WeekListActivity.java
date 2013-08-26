package com.rtzoeller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class WeekListActivity extends SherlockFragmentActivity
        implements WeekExpandableListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean mHasContent = false;
    private int groupPosition;
    private int childPosition;
    private int page;
    private boolean[] expandedItems;
    private static final String ARG_DETAIL_TAG = "detail_fragment";
    private static final String ARG_LIST_TAG = "list_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_twopane);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mTwoPane = getResources().getBoolean(R.bool.has_two_panes);

        // Resize expandedItems to match the number of groups
        expandedItems = new boolean[WeekContent.PARENTS.size()];

        if (savedInstanceState != null) {
            // Load the page that we should display
            groupPosition = savedInstanceState.getInt(WeekDetailFragment.ARG_GROUP_ID);
            childPosition = savedInstanceState.getInt(WeekDetailFragment.ARG_CHILD_ID);
            page = savedInstanceState.getInt(WeekDetailFragment.ARG_PAGE_ID);

            if (savedInstanceState.containsKey(WeekExpandableListFragment.ARG_EXPANDED_ITEMS)) {
                expandedItems = savedInstanceState.getBooleanArray(WeekExpandableListFragment.ARG_EXPANDED_ITEMS);
            }
        } else {
            groupPosition = -1;
            childPosition = -1;
        }

        // Instantiate the list fragment
        // We do this after we have loaded our array of expandedItems
        listInflate(R.id.week_list_container);

        if (mTwoPane) {
            findViewById(R.id.week_detail_container).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.week_detail_container).setVisibility(View.GONE);
        }

        onChildClick(groupPosition, childPosition); // Set the detail fragment to display the correct id

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        WeekDetailFragment fragment = (WeekDetailFragment)getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG);

        int pageNumber;
        int groupPosition;
        int childPosition;
        if (fragment != null) {
            pageNumber = fragment.getPage();
            groupPosition = this.groupPosition;
            childPosition = this.childPosition;
        } else {
            pageNumber = 0;
            groupPosition = -1;
            childPosition = -1;
        }
        state.putInt(WeekDetailFragment.ARG_PAGE_ID, pageNumber); // Save the page of the week we are displaying
        state.putInt(WeekDetailFragment.ARG_GROUP_ID, groupPosition); // Save the groupPosition of the week we are displaying
        state.putInt(WeekDetailFragment.ARG_CHILD_ID, childPosition); // Save the childPosition of the week we are displaying

        if (expandedItems != null) {
          state.putBooleanArray(WeekExpandableListFragment.ARG_EXPANDED_ITEMS, expandedItems);
        }
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

                mHasContent = false;
                page = 0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setTitle(R.string.app_name);

                listInflate(R.id.week_list_container);

                return true;
            case R.id.about:
                new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo))
                        .setView(this.getLayoutInflater().inflate(R.layout.dialog_about, null))
                        .create().show();
                return true;
            case R.id.contactUs:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"apps@kingtec2169.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Survival Guide");
                try {
                    startActivity(Intent.createChooser(intent, "Contact Us"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        boolean listVisible = getSupportFragmentManager().findFragmentByTag(ARG_LIST_TAG) != null;
        boolean detailVisible = getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG) != null;
        if(listVisible && detailVisible) {
            page = 0;
            mHasContent = false;
            onChildClick(-1, -1);
        } else if(!listVisible){
            mHasContent = false;
            page = 0;
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setTitle(R.string.app_name);

            listInflate(R.id.week_list_container);
        } else if(!detailVisible){
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean confirmExit = sharedPref.getBoolean(SettingsActivity.KEY_CONFIRM_EXIT, true);

            if(confirmExit) {
                new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo))
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                WeekListActivity.super.onBackPressed();
                            }
                        }).create().show();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private WeekExpandableListFragment listInflate(int resourceId) {
        Bundle arguments = new Bundle();
        arguments.putBooleanArray(WeekExpandableListFragment.ARG_EXPANDED_ITEMS, expandedItems);
        WeekExpandableListFragment fragment = new WeekExpandableListFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_LIST_TAG)
                .commit();
        return fragment;
    }

    private WeekDetailFragment detailInflate(int resourceId) {
        Bundle arguments = new Bundle();
        arguments.putInt(WeekDetailFragment.ARG_GROUP_ID, groupPosition); // Save the groupPosition of the week we are displaying
        arguments.putInt(WeekDetailFragment.ARG_CHILD_ID, childPosition); // Save the childPosition of the week we are displaying
        arguments.putInt(WeekDetailFragment.ARG_PAGE_ID, page);
        WeekDetailFragment fragment = new WeekDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_DETAIL_TAG)
                .commit();
        return fragment;
    }

    public boolean onChildClick(int groupPosition, int childPosition) {
        mHasContent = !(groupPosition == -1 || childPosition == -1);
        if (this.groupPosition != groupPosition) {
            page = 0;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        } else if (this.childPosition != childPosition) {
            page = 0;
            this.childPosition = childPosition;
        }
        if (mHasContent) {
            // Make sure we have a week selected and we aren't just
            // feeding through a null value from our saved state
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                detailInflate(R.id.week_detail_container);

            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.

                detailInflate(R.id.week_list_container);
                setTitle(WeekContent.CHILDREN.get(groupPosition).get(childPosition).name);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }
        } else if (mTwoPane) {
            // We have no week to display but we can still show the user a screen
            // prompting them to pick a week
            PromptSelectWeekFragment fragment = new PromptSelectWeekFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.week_detail_container, fragment)
                    .commit();
            page = 0;
        }
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return onChildClick(groupPosition, childPosition);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        expandedItems[groupPosition] = true;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        expandedItems[groupPosition] = false;
    }
}
