package com.kingtec2169.survivalguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import android.support.v7.app.ActionBarActivity;

public class WeekListActivity extends ActionBarActivity
        implements WeekExpandableListFragment.Callbacks {

    // Is the current view displaying more than one fragment?
    // True on small tablets in landscape and on large tablets in either orientation
    private ActivityConfigurations state;
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
    // Toggle for the DrawerLayout
    private ActionBarDrawerToggle drawerToggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the default values for the settings page
        // Only sets the values the first time the app is launched after install
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        state = getLayoutConfiguration();

        if (state == ActivityConfigurations.DRAWER) {
            setContentView(R.layout.activity_week_drawer);
        } else {
            setContentView(R.layout.activity_week_twopane);
        }

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
        } else if (state == ActivityConfigurations.DRAWER) {
            /* We don't have content stored from before we
            (re)created the activity, so we should show the
            Nav. Drawer if possible to prompt the user to pick
            something. */
            ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT);
        }

        // Instantiate the list fragment
        // We do this after we have loaded our array of expandedItems
        listInflate(R.id.week_list_container);

        if (state == ActivityConfigurations.TWO_PANE) {
            // Show the second fragment pane if we are on a big enough screen
            findViewById(R.id.week_detail_container).setVisibility(View.VISIBLE);
        } else if (state == ActivityConfigurations.ONE_PANE) {
            // Hide the second fragment pane if we shouldn't display two fragments (for small screens)
            findViewById(R.id.week_detail_container).setVisibility(View.GONE);
        } else if (state == ActivityConfigurations.DRAWER) {
            // Show the second fragment pane, we are using a navigation drawer
            findViewById(R.id.week_detail_container).setVisibility(View.VISIBLE);
            // Prevent the drawer from catching its own button presses
            DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            drawerLayout.setFocusableInTouchMode(false);

            drawerToggle = new ActionBarDrawerToggle(
                    this,                  /* host Activity */
                    drawerLayout,         /* DrawerLayout object */
                    R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                    R.string.drawer_open,  /* "open drawer" description */
                    R.string.drawer_close  /* "close drawer" description */
            );
            drawerLayout.setDrawerListener(drawerToggle);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
    }

        /** Simulate a click event so the {@link WeekDetailFragment} shows the correct content **/
        onChildClick(groupPosition, childPosition);
    }

    private ActivityConfigurations getLayoutConfiguration() {
        // Determine what layout to use
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(SettingsActivity.KEY_USE_DRAWER, true)) {
            return ActivityConfigurations.DRAWER;
        } else if (getResources().getBoolean(R.bool.has_two_panes)) {
            return ActivityConfigurations.TWO_PANE;
        } else {
            return ActivityConfigurations.ONE_PANE;
        }
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
                switch (state) {
                    /** The Up button shown in the ActionBar.
                     *  Inflates/Shows a {@link WeekExpandableListFragment} when selected. **/
                    case DRAWER:
                        if (((DrawerLayout)findViewById(R.id.drawer_layout)).isDrawerOpen(Gravity.LEFT)) {
                            ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.LEFT);
                        } else {
                            ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT);
                        }
                        return true;
                    case ONE_PANE:
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
                }
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
        } else {
            switch (state) {
                case DRAWER:
                    // Is the drawer currently open?
                    if (((DrawerLayout)findViewById(R.id.drawer_layout)).isDrawerOpen(Gravity.LEFT)) {
                        // Close it if it is
                        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.LEFT);
                    } else {
                        // It is closed so we should try to leave
                        confirmExit();
                    }
                    break;
                case TWO_PANE:
                    if (detailVisible) {
                        /* We are in two pane mode showing content,
                        so we should remove that content from view */
                        // Remove the content
                        page = 0;
                        mHasContent = false;
                        // Simulate a list click event to refresh the display
                        onChildClick(-1, -1);
                    } else if (listVisible) {
                        /* We are currently not showing content. We should
                        try to exit */
                        confirmExit();
                    }
                    break;
                case ONE_PANE:
                    if (detailVisible) {
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
                    } else if (listVisible) {
                        // We are showing the list, we should try to leave
                        confirmExit();
                    }
                    break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                if (state == ActivityConfigurations.DRAWER) {
                    ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.LEFT);
                }

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

        switch (state) {
            case DRAWER:
                if (mHasContent) {
                    // Launch the selected item in the main pane
                    detailInflate(R.id.week_detail_container, WeekDetailFragment.createBundle(groupPosition, childPosition, page));
                } else {
                    // Show the prompt
                    selectorInflate(R.id.week_detail_container);
                    // Reset the saved page
                    page = 0;
                }
                break;
            case TWO_PANE:
                if (mHasContent) {
                    // Launch the selected item in the right pane
                    detailInflate(R.id.week_detail_container, WeekDetailFragment.createBundle(groupPosition, childPosition, page));
                } else {
                    // Show the prompt
                    selectorInflate(R.id.week_detail_container);
                    // Reset the saved page
                    page = 0;
                }
                break;
            case ONE_PANE:
                if (mHasContent) {
                    // Launch the selected item in the current pane
                    detailInflate(R.id.week_list_container, WeekDetailFragment.createBundle(groupPosition, childPosition, page));
                    // Reconfigure the action bar
                    setTitle(WeekContent.CHILDREN.get(groupPosition).get(childPosition).name);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else {
                    // We don't want to do anything because the only pane visible
                    // is the list we are currently displaying
                }
                break;
        }
        return true;

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        /* Strip any unnecessary data and call a variant of this method.
        This method is called by the ExpandableListView,
        and the 2-argument variant is what our activity uses. */

        // Are we using drawer based navigation
        if (state == ActivityConfigurations.DRAWER) {
            // See if we need to close the drawer and act on it
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPref.getBoolean(SettingsActivity.KEY_CLOSE_DRAWER_ON_CLICK, true)) {
                ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.LEFT);
            }
        }
        // Reset our page id to 0 because we are updating the content
        page = 0;
        // Update our display with the new click data
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

    private void confirmExit() {
        // Check if we are set to confirm on exit
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean confirmExit = sharedPref.getBoolean(SettingsActivity.KEY_CONFIRM_EXIT, true);
        // If we need to confirm exit do so, otherwise leave the app
        if (confirmExit){
            new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo))
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                            // Do nothing if no is clicked
                    .setNegativeButton(android.R.string.no, null)
                            // Call the parent behavior if yes is pressed (exit the app)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).create().show();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (drawerToggle != null) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }
}
