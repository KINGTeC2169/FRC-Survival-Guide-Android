package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// The main activity of the FRC Survival Guide, this activity handles the loading and
// displaying of all content and is responsible for selecting the appropriate layouts
// based on screen sizes and preferences.

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

import icepick.Icepick;
import icepick.Icicle;

public class MainActivity extends ActionBarActivity
        implements ExpandableListNavigationFragment.Callbacks, SearchResultsFragment.HandlesPaging {

    // Is the current view displaying more than one fragment?
    // True on small tablets in landscape and on large tablets in either orientation
    private ActivityConfigurations state;
    // Is there any content selected to display
    public boolean mHasContent = false;
    /** The state information to feed forward to our {@link ContentFragment} **/
    @Icicle
    public ContentIdHolder contentId = new ContentIdHolder(-1, -1, 0);
    /** What items are expanded in our {@link ExpandableListNavigationFragment} **/
    @Icicle
    public boolean[] expandedItems = new boolean[NavigationListContent.PARENTS.size()];
    // Tags for retrieving fragments from the SupportFragmentManager
    private static final String ARG_DETAIL_TAG = "detail_fragment";
    private static final String ARG_LIST_TAG = "list_fragment";
    private static final String ARG_SELECT_TAG = "selector_fragment";
    private static final String ARG_SEARCH_TAG = "search_fragment";
    // Toggle for the DrawerLayout
    private ActionBarDrawerToggle drawerToggle = null;
    // Cache for our settings so we can avoid looking them up later
    // We recreate the activity from scratch after leaving settings,
    // so we can get away with only checking the settings once
    private boolean confirmExit;
    private boolean useDrawer;
    private boolean autoCloseDrawer;
    // Should we combine two pages of content into one view?
    // This happens only on large (10") tablets
    private boolean combineContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);

        // Set the default values for the settings page
        // Only sets the values the first time the app is launched after install
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        // Update our cache to match the settings
        refreshSettings();

        // Find out which layout we should display through
        state = getLayoutConfiguration();

        // Check if we should combine content
        combineContent = getResources().getBoolean(R.bool.combine_content);

        if (state == ActivityConfigurations.DRAWER) {
            setContentView(R.layout.activity_week_drawer);
        } else {
            setContentView(R.layout.activity_week_twopane);
        }

        if (savedInstanceState == null && state == ActivityConfigurations.DRAWER) {
            /* We don't have content stored from before we
            (re)created the activity, so we should show the
            Nav. Drawer if possible to prompt the user to pick
            something. */
            ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
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

        /** Simulate a click event so the {@link ContentFragment} shows the correct content **/
        onChildClick(contentId.getGroupPosition(), contentId.getChildPosition());
    }

    private ActivityConfigurations getLayoutConfiguration() {
        // Determine what layout to use
        if (useDrawer) {
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

        // Get the page currently being displayed
        ContentFragment fragment = (ContentFragment)getSupportFragmentManager().findFragmentByTag(ARG_DETAIL_TAG);
        if (fragment != null) {
            contentId.setPage(fragment.getPage());
        }

        Icepick.saveInstanceState(this, state);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (state) {
                    /** The Up button shown in the ActionBar.
                     *  Inflates/Shows a {@link ExpandableListNavigationFragment} when selected. **/
                    case DRAWER:
                        if (((DrawerLayout)findViewById(R.id.drawer_layout)).isDrawerOpen(Gravity.START)) {
                            ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
                        } else {
                            ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
                        }
                        return true;
                    case ONE_PANE:
                        // Remove the content we have loaded
                        mHasContent = false;
                        contentId.setPage(0);
                        // Reset the action bar to the main level configuration
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        // Show the correct title
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
                        .setNeutralButton("Dismiss", null)
                        .setTitle(R.string.about_name)
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
                    if (((DrawerLayout)findViewById(R.id.drawer_layout)).isDrawerOpen(Gravity.START)) {
                        // Close it if it is
                        ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
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
                        contentId.setPage(0);
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
                        contentId.setPage(0);
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
        getSupportActionBar().setIcon(R.drawable.ic_kt);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                if (state == ActivityConfigurations.DRAWER) {
                    ((DrawerLayout)findViewById(R.id.drawer_layout)).openDrawer(Gravity.START);
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

    private ExpandableListNavigationFragment listInflate(int resourceId) {
        Bundle arguments = new Bundle();
        arguments.putBooleanArray(ExpandableListNavigationFragment.ARG_EXPANDED_ITEMS, expandedItems);
        ExpandableListNavigationFragment fragment = new ExpandableListNavigationFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_LIST_TAG)
                .commit();
        return fragment;
    }

    private ContentFragment detailInflate(int resourceId, Bundle bundle) {
        ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_DETAIL_TAG)
                .commit();
        return fragment;
    }

    private PromptSelectContentFragment selectorInflate(int resourceId) {
        PromptSelectContentFragment fragment = new PromptSelectContentFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(resourceId, fragment, ARG_SELECT_TAG)
                .commit();
        return fragment;
    }

    public boolean onChildClick(int group, int child) {
        contentId.setGroupPosition(group);
        contentId.setChildPosition(child);

        // Check if we actually have content to display
        mHasContent = contentId.getGroupPosition() != -1 && contentId.getChildPosition() != -1;

        switch (state) {
            case DRAWER:
                if (mHasContent) {
                    // Launch the selected item in the main pane
                    detailInflate(R.id.week_detail_container, ContentFragment.createBundle(contentId.getGroupPosition(), contentId.getChildPosition(), contentId.getPage(), combineContent));
                } else {
                    // Show the prompt
                    selectorInflate(R.id.week_detail_container);
                    // Reset the saved page
                    contentId.setPage(0);
                }
                break;
            case TWO_PANE:
                if (mHasContent) {
                    // Launch the selected item in the right pane
                    detailInflate(R.id.week_detail_container, ContentFragment.createBundle(contentId.getGroupPosition(), contentId.getChildPosition(), contentId.getPage(), combineContent));
                } else {
                    // Show the prompt
                    selectorInflate(R.id.week_detail_container);
                    // Reset the saved page
                    contentId.setPage(0);
                }
                break;
            case ONE_PANE:
                if (mHasContent) {
                    // Launch the selected item in the current pane
                    detailInflate(R.id.week_list_container, ContentFragment.createBundle(contentId.getGroupPosition(), contentId.getChildPosition(), contentId.getPage(), combineContent));
                    // Reconfigure the action bar
                    setTitle(NavigationListContent.CHILDREN.get(contentId.getGroupPosition()).get(contentId.getChildPosition()).name);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            if (autoCloseDrawer) {
                ((DrawerLayout)findViewById(R.id.drawer_layout)).closeDrawer(Gravity.START);
            }
        }

        if (!(parent == null && v == null)) {
            // Reset our page id to 0 because we are updating the content.
            // If they both equal null that indicates that we are returning a search results
            // click, so we shouldn't clear the page id.
            contentId.setPage(0);
        }

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

    @Override
    public void goToPage(ContentIdHolder contentId) {
        this.contentId.setPage(contentId.getPage()); // Set the desired page
        // Call onChildClick with null arguments to indicate that the method should not
        // reset the page to 0
        this.onChildClick(null, null, contentId.getGroupPosition(), contentId.getChildPosition(), 0);
    }

    private void refreshSettings() {
        // Get the settings from the SharedPreferences config
        // Important: make sure the default values here match the default values declared in XML
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        confirmExit = sharedPref.getBoolean(SettingsActivity.KEY_CONFIRM_EXIT, false);
        useDrawer = sharedPref.getBoolean(SettingsActivity.KEY_USE_DRAWER, false);
        autoCloseDrawer = sharedPref.getBoolean(SettingsActivity.KEY_CLOSE_DRAWER_ON_CLICK, true);
    }
}
