package com.kingtec2169.survivalguide;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class PageContent {
    // Stores the items for the Pages
    public static final List<Page> PAGES = new ArrayList<Page>();
    // Number of pages in each week
    public static final int numPages[][] = {
            {3,3,2,1,1,4},
            {4,0,4,3,0},
            {4,1,3,1}};
    // Which content is currently in memory
    private static int loaded_group;
    private static int loaded_child;
    // The context we should load our resources from
    private static Context mContext;

    // Load the pages from the application resources
    PageContent(Context context) {
        if (PAGES.isEmpty()) { // If no content has been loaded set the loaded variable to reflect that
            loaded_group = -1;
            loaded_child = -1;
        }

        mContext = context; // Update our context for loading resources
    }

    public static Page get (int group, int child, int position) {
        if (loaded_group != group || loaded_child != child) {
            unload();
            load(group, child);
        }
        return PAGES.get(position);
    }

    private static void load (int group, int child) {
        loaded_group = group;
        loaded_child = child;
        switch (group) {
            case 0:
                switch (child) {
                    case 0:
                        PAGES.add(new Page(mContext, R.array.brainstorming));
                        PAGES.add(new Page(mContext, R.array.approaching_the_game));
                        PAGES.add(new Page(mContext, R.array.game_rules));
                        break;
                    case 1:
                        PAGES.add(new Page(mContext, R.array.getting_materials));
                        PAGES.add(new Page(mContext, R.array.prototyping));
                        PAGES.add(new Page(mContext, R.array.chassis_building));
                        break;
                    case 2:
                        PAGES.add(new Page(mContext, R.array.module_building));
                        PAGES.add(new Page(mContext, R.array.beginning_wiring));
                        break;
                    case 3:
                        PAGES.add(new Page(mContext, R.array.early_programming));
                        break;
                    case 4:
                        PAGES.add(new Page(mContext, R.array.main_programming));
                        break;
                    case 5:
                        PAGES.add(new Page(mContext, R.array.drive_practice));
                        PAGES.add(new Page(mContext, R.array.tweaking_programming));
                        PAGES.add(new Page(mContext, R.array.late_build_changes));
                        PAGES.add(new Page(mContext, R.array.bag_and_tag));
                        break;
                }
                break;
            case 1:
                switch (child) {
                    case 0:
                        PAGES.add(new Page(mContext, R.array.pits_design));
                        PAGES.add(new Page(mContext, R.array.pits_organization));
                        PAGES.add(new Page(mContext, R.array.pits_crew));
                        PAGES.add(new Page(mContext, R.array.pits_judges));
                        break;
                    case 2:
                        PAGES.add(new Page(mContext, R.array.chairmans_about));
                        PAGES.add(new Page(mContext, R.array.chairmans_writing));
                        PAGES.add(new Page(mContext, R.array.chairmans_speech));
                        PAGES.add(new Page(mContext, R.array.chairmans_video));
                        break;
                    case 3:
                        PAGES.add(new Page(mContext, R.array.spirit_importance));
                        PAGES.add(new Page(mContext, R.array.spirit_team_image));
                        PAGES.add(new Page(mContext, R.array.spirit_swag));
                        break;
                }
                break;
            case 2:
                switch (child) {
                    case 0:
                        PAGES.add(new Page(mContext, R.array.fundraising_obtaining_sponsors));
                        PAGES.add(new Page(mContext, R.array.fundraising_grants));
                        PAGES.add(new Page(mContext, R.array.fundraising_major_events));
                        PAGES.add(new Page(mContext, R.array.fundraising_minor_events));
                        break;
                    case 1:
                        PAGES.add(new Page(mContext, R.array.tournaments_about));
                        break;
                    case 2:
                        PAGES.add(new Page(mContext, R.array.outreach_about));
                        PAGES.add(new Page(mContext, R.array.outreach_mentoring));
                        PAGES.add(new Page(mContext, R.array.outreach_demonstrations));
                        break;
                    case 3:
                        PAGES.add(new Page(mContext, R.array.preparing_for_next_year));
                        break;
                }
                break;
        }
    }

    private static void unload () {
        loaded_group = -1;
        loaded_child = -1;
        // Clear array to be refilled with new data
        PAGES.clear();
    }
}
