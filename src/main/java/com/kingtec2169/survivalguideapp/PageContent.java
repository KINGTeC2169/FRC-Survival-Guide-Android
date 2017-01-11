package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// This class is responsible for loading and storing content from XML as it is requested.
// It loads content into memory in blocks, with all of the content shown in
// the ViewPager being loaded at once. When a new block of content is loaded, the old block
// is unloaded.

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class    PageContent {
    // Stores the items for the Pages
    public static final List<Page> PAGES = new ArrayList<Page>();
    // Number of pages in each week
    public static final int numPages[][] = {
            {8, 5, 7, 6, 3, 3, 1},
            {1, 3, 4, 4},
            {1, 3, 5, 3, 5},

    };
    // Which content is currently in memory
    private static int loaded_group;
    private static int loaded_child;
    // The context we should load our resources from
    private static Context mContext;

    public static void refresh(Context context) {
        if (PAGES.isEmpty()) { // If no content has been loaded set the loaded variable to reflect that
            loaded_group = -1;
            loaded_child = -1;
        }

        mContext = context; // Update our context for loading resources
    }

    public static Page get(int group, int child, int position) {
        if (loaded_group != group || loaded_child != child) {
            unload();
            load(group, child);
        }
        return PAGES.get(position);
    }

    public static Page get(ContentIdHolder contentId) {
        return PageContent.get(contentId.getGroupPosition(), contentId.getChildPosition(), contentId.getPage());
    }

    private static void load(int group, int child) {
        loaded_group = group;
        loaded_child = child;
        if (MainActivity.getLanguage() == false) {
            switch (group) {
                case 0:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.mech_intro));
                            PAGES.add(new Page(mContext, R.array.mech_tools));
                            PAGES.add(new Page(mContext, R.array.mech_protoype));
                            PAGES.add(new Page(mContext, R.array.mech_week1));
                            PAGES.add(new Page(mContext, R.array.mech_week2));
                            PAGES.add(new Page(mContext, R.array.mech_week3));
                            PAGES.add(new Page(mContext, R.array.mech_week4_comp));
                            PAGES.add(new Page(mContext, R.array.mech_comp));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.prog_intro));
                            PAGES.add(new Page(mContext, R.array.prog_week1));
                            PAGES.add(new Page(mContext, R.array.prog_week2_4));
                            PAGES.add(new Page(mContext, R.array.prog_week5_6));
                            PAGES.add(new Page(mContext, R.array.prog_appdev));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.elect_intro));
                            PAGES.add(new Page(mContext, R.array.elect_tools));
                            PAGES.add(new Page(mContext, R.array.elect_components));
                            PAGES.add(new Page(mContext, R.array.elect_week1));
                            PAGES.add(new Page(mContext, R.array.elect_week2));
                            PAGES.add(new Page(mContext, R.array.elect_week3_comp));
                            PAGES.add(new Page(mContext, R.array.elect_comp));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.cad_intro));
                            PAGES.add(new Page(mContext, R.array.cad_software));
                            PAGES.add(new Page(mContext, R.array.cad_sync));
                            PAGES.add(new Page(mContext, R.array.cad_week1));
                            PAGES.add(new Page(mContext, R.array.cad_week2));
                            PAGES.add(new Page(mContext, R.array.cad_week4));
                            break;
                        case 4:
                            PAGES.add(new Page(mContext, R.array.strat_intro));
                            PAGES.add(new Page(mContext, R.array.strat_findingstrat));
                            PAGES.add(new Page(mContext, R.array.strat_scouting));
                            break;
                        case 5:
                            PAGES.add(new Page(mContext, R.array.spirit_intro));
                            PAGES.add(new Page(mContext, R.array.spirit_season));
                            PAGES.add(new Page(mContext, R.array.spirit_comp));
                            break;
                        case 6:
                            PAGES.add(new Page(mContext, R.array.pr));
                            break;
                    }
                    break;
                case 1:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.comp_intro));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.inspect_intro));
                            PAGES.add(new Page(mContext, R.array.inspect_prep));
                            PAGES.add(new Page(mContext, R.array.inspect_remember));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.comp_prep));
                            PAGES.add(new Page(mContext, R.array.qualifications));
                            PAGES.add(new Page(mContext, R.array.eliminations));
                            PAGES.add(new Page(mContext, R.array.higher_lvls_comp));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.disc_prep));
                            PAGES.add(new Page(mContext, R.array.disc_quals));
                            PAGES.add(new Page(mContext, R.array.disc_elims));
                            PAGES.add(new Page(mContext, R.array.disc_higher_lvls));
                            break;


                    }
                    break;
                case 2:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.drive_team));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.bagandtag_stop_build));
                            PAGES.add(new Page(mContext, R.array.bagandtag_howto));
                            PAGES.add(new Page(mContext, R.array.bagandtag_what_to_bag));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.mentors));
                            PAGES.add(new Page(mContext, R.array.mentors_purpose));
                            PAGES.add(new Page(mContext, R.array.mentors_meetings));
                            PAGES.add(new Page(mContext, R.array.mentors_who_can_be));
                            PAGES.add(new Page(mContext, R.array.mentors_find));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.safety_safe));
                            PAGES.add(new Page(mContext, R.array.safety_bring));
                            PAGES.add(new Page(mContext, R.array.safety_awards));
                            break;
                        case 4:
                            PAGES.add(new Page(mContext, R.array.fundraising_intro));
                            PAGES.add(new Page(mContext, R.array.fundraising_fees));
                            PAGES.add(new Page(mContext, R.array.fundraising_sponsors));
                            PAGES.add(new Page(mContext, R.array.fundraising_events));
                            PAGES.add(new Page(mContext, R.array.fundraising_grants));
                            break;
                    }
                    break;
            }
        }
    }



    private static void unload () {
        loaded_group = -1;
        loaded_child = -1;
        // Clear array to be refilled with new data
        PAGES.clear();
    }
}
