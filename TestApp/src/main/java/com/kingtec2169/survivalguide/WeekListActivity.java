package com.kingtec2169.survivalguide;

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
import com.actionbarsherlock.widget.SearchView;

public class WeekListActivity extends SherlockFragmentActivity
        implements WeekExpandableListFragment.Callbacks {

    // Is the current view displaying more than one fragment?
    // True on small tablets in landscape and on large tablets in either orientation
    private boolean mTwoPane;
    // Is there any content selected to display
    private boolean mHasContent = false;
    /** The page number to feed forward to our {@link WeekDetailFragment} **/
    public int page;
    /** What items are expanded in our {@link WeekExpandableListFragment} **/
    private boolean[] expandedItems;
    // Tags for retrieving fragments from the SupportFragmentManager
    private static final String ARG_DETAIL_TAG = "detail_fragment";
    private static final String ARG_LIST_TAG = "list_fragment";
    private static final String ARG_SELECT_TAG = "selector_fragment";
    private static final String ARG_SEARCH_TAG = "search_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_twopane);

        // Set the default values for the settings page
        // Only sets the values the first time the app is launched after install
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Check whether or not we have room for two fragments to display
        mTwoPane = getResources().getBoolean(R.bool.has_two_panes);

        // Resize expandedItems to match the number of groups
        // All elements default to false so if we can't load data
        // the array is still initialized properly
        expandedItems = new boolean[WeekContent.PARENTS.size()];

        // Set our ViewPager data to a default
        // If we have saved data to load these will get overwritten
        int groupPosition = -1;
        int childPosition = -1;
        page = 0;

        // Attempt to load saved data
        if (savedInstanceState != null) {
            // Load the saved ViewPager data
            if (savedInstanceState.containsKey(WeekDetailFragment.ARG_GROUP_ID) &&
                    savedInstanceState.containsKey(WeekDetailFragment.ARG_CHILD_ID) &&
                    savedInstanceState.containsKey(WeekDetailFragment.ARG_PAGE_ID)) {
                groupPosition = savedInstanceState.getInt(WeekDetailFragment.ARG_GROUP_ID);
                childPosition = savedInstanceState.getInt(WeekDetailFragment.ARG_CHILD_ID);
                page = savedInstanceState.getInt(WeekDetailFragment.ARG_PAGE_ID);
            }
            // Load the array of expanded list items
            if (savedInstanceState.containsKey(WeekExpandableListFragment.ARG_EXPANDED_ITEMS)) {
                expandedItems = savedInstanceState.getBooleanArray(WeekExpandableListFragment.ARG_EXPANDED_ITEMS);
            }
        }

        // Instantiate the list fragment
        // We do this after we have loaded our array of expandedItems
        listInflate(R.id.week_list_container);

        if (mTwoPane) {
            // Show the second fragment pane if we are on a big enough screen
            findViewById(R.id.week_detail_container).setVisibility(View.VISIBLE);
        } else {
            // Hide the second fragment pane if we shouldn't display two fragments (for small screens)
            findViewById(R.id.week_detail_container).setVisibility(View.GONE);
        }

        /** Simulate a click event so the {@link WeekDetailFragment} shows the correct content **/
        onChildClick(groupPosition, childPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        WeekDetailFragment fragment = (WeekDetailFragment)getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG);

        // Attach the current display information to our Activity state
        if (fragment != null) {
            state.putAll(fragment.saveState());
        }

        // Attach what items are loaded to our Activity state
        // If we don't pass any data through, upon loading the array will be initialized to all false values
        // Which is the desired behavior
        if (expandedItems != null) {
          state.putBooleanArray(WeekExpandableListFragment.ARG_EXPANDED_ITEMS, expandedItems);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /** The Up button shown in the ActionBar.
                 *  Inflates a {@link WeekExpandableListFragment} when selected. **/
                // Remove the content we have loaded
                mHasContent = false;
                page = 0;
                // Reset the action bar to the main level configuration
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                setTitle(R.string.app_name);
                /* Inflates the list into the list container
                This button can only be pressed when mTwoPane is false, so we won't ever re-inflate
                the list unnecessarily. */
                listInflate(R.id.week_list_container);
                return true;
            case R.id.about:
                /** The About KING TeC item in the options menu.
                 *  Launches a dialog telling the user about KING TeC **/
                new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo))
                        .setView(this.getLayoutInflater().inflate(R.layout.dialog_about, null))
                        .create().show();
                return true;
            case R.id.contactUs:
                /** The Contact Us item in the options menu.
                 *  Launches an email selection dialog handled by the OS **/
                // Build the email information
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"apps@kingtec2169.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Survival Guide");
                // Try to pass the email information to the OS
                try {
                    startActivity(Intent.createChooser(intent, "Contact Us"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.settings:
                /** The Settings item in the options menu.
                 *  Launches a {@link SettingsActivity} when selected. **/
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Check which fragments are visible
        boolean listVisible = getSupportFragmentManager().findFragmentByTag(ARG_LIST_TAG) != null && getSupportFragmentManager().findFragmentByTag(ARG_LIST_TAG).isVisible();
        boolean detailVisible = getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG) != null && getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG).isVisible();
        boolean searchVisible = getSupportFragmentManager().findFragmentByTag(ARG_SEARCH_TAG) != null && getSupportFragmentManager().findFragmentByTag(ARG_SEARCH_TAG).isVisible();
        if(searchVisible) {
            /* We are showing the search results and we should stop */
            listInflate(R.id.week_list_container);
        } else if(listVisible && detailVisible && mTwoPane) {
            /* We are in two pane mode showing content,
            so we should remove that content from view */
            // Remove the content
            page = 0;
            mHasContent = false;
            // Simulate a list click event to refresh the display
            onChildClick(-1, -1);
        } else if(detailVisible && !listVisible) {
            /* We are in one pane mode and the content is visible.
            We should return to showing a list of items when pressed */
            // Remove the content
            mHasContent = false;
            page = 0;
            // Reset the action bar to the main level configuration
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            setTitle(R.string.app_name);
            // Inflate the list
            listInflate(R.id.week_list_container);
        } else if(listVisible) {
            /* We are currently not showing content. We should
            check to see if we prompt before exiting and then potentially exit. */
            // Check if we are set to confirm on exit
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean confirmExit = sharedPref.getBoolean(SettingsActivity.KEY_CONFIRM_EXIT, true);

            if(confirmExit) {
                // If we need to confirm, start a dialog asking the user
                new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo))
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit?")
                        // Do nothing if no is clicked
                        .setNegativeButton(android.R.string.no, null)
                        // Call the parent behavior if yes is pressed (exit the app)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                WeekListActivity.super.onBackPressed();
                            }
                        }).create().show();
            } else {
                // If we have ended up in some unrecognized state, leave the app
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                Bundle arguments = new Bundle();
                arguments.putString(SearchResultsFragment.ARG_SEARCH_ID, newText);
                SearchResultsFragment fragment = new SearchResultsFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.week_list_container, fragment, ARG_SEARCH_TAG)
                        .commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

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

    private WeekDetailFragment detailInflate(int resourceId, Bundle bundle) {
        WeekDetailFragment fragment = new WeekDetailFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_DETAIL_TAG)
                .commit();
        return fragment;
    }

    private PromptSelectWeekFragment selectorInflate(int resourceId) {
        PromptSelectWeekFragment fragment = new PromptSelectWeekFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_SELECT_TAG)
                .commit();
        return fragment;
    }

    public boolean onChildClick(int groupPosition, int childPosition) {
        // Check if we actually have content to display
        mHasContent = !(groupPosition == -1 || childPosition == -1);
        // Handle the click event appropriately
        if (mHasContent) {
            // Make sure we have a week selected and we aren't just
            // feeding through a null value from our saved state
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.
                detailInflate(R.id.week_detail_container, WeekDetailFragment.createBundle(groupPosition, childPosition, page));
            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                detailInflate(R.id.week_list_container, WeekDetailFragment.createBundle(groupPosition, childPosition, page));
                setTitle(WeekContent.CHILDREN.get(groupPosition).get(childPosition).name);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        } else if (mTwoPane) {
            // We have no week to display but we can still show the user a screen
            // prompting them to pick a week
            selectorInflate(R.id.week_detail_container);
            // Set the saved page to 0
            page = 0;
        }
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        /* Strip any unnecessary data and call a variant of this method.
        This method is called by the ExpandableListView,
        and the 2-argument variant is what our activity uses. */
        // Reset our page id to 0 because we are updating the content
        page = 0;
        return onChildClick(groupPosition, childPosition);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
        // Update the array of which items are expanded
        expandedItems[groupPosition] = true;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
        // Update the array of which items are expanded
        expandedItems[groupPosition] = false;
    }
}
