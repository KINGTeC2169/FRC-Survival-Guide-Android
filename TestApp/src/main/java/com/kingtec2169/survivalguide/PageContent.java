package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This class is responsible for loading and storing content from XML as it is requested.
// It loads content into memory in blocks, with all of the content shown in
// the ViewPager being loaded at once. When a new block of content is loaded, the old block
// is unloaded.

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class PageContent {
    // Stores the items for the Pages
    public static final List<Page> PAGES = new ArrayList<Page>();
    // Number of pages in each week
    public static final int numPages[][] = {
            {3, 3, 4, 2, 2, 3},
            {4, 2, 4, 3},
            {4, 1, 3, 1}};
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
                            PAGES.add(new Page(mContext, R.array.robot_assembly));
                            PAGES.add(new Page(mContext, R.array.beginning_wiring));
                            PAGES.add(new Page(mContext, R.array.thinking_about_programming));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.early_programming));
                            PAGES.add(new Page(mContext, R.array.finishing_electrical));
                            break;
                        case 4:
                            PAGES.add(new Page(mContext, R.array.main_programming));
                            PAGES.add(new Page(mContext, R.array.drive_practice));
                            break;
                        case 5:
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
                        case 1:
                            PAGES.add(new Page(mContext, R.array.scouting_match));
                            PAGES.add(new Page(mContext, R.array.scouting_pits));
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
        } else {
            switch (group) {
                case 0:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.brainstormingSpanish));
                            PAGES.add(new Page(mContext, R.array.approaching_the_gameSpanish));
                            PAGES.add(new Page(mContext, R.array.game_rulesSpanish));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.getting_materialsSpanish));
                            PAGES.add(new Page(mContext, R.array.prototypingSpanish));
                            PAGES.add(new Page(mContext, R.array.chassis_buildingSpanish));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.module_buildingSpanish));
                            PAGES.add(new Page(mContext, R.array.robot_assemblySpanish));
                            PAGES.add(new Page(mContext, R.array.beginning_wiringSpanish));
                            PAGES.add(new Page(mContext, R.array.thinking_about_programmingSpanish));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.early_programmingSpanish));
                            PAGES.add(new Page(mContext, R.array.finishing_electricalSpanish));
                            break;
                        case 4:
                            PAGES.add(new Page(mContext, R.array.main_programmingSpanish));
                            PAGES.add(new Page(mContext, R.array.drive_practiceSpanish));
                            break;
                        case 5:
                            PAGES.add(new Page(mContext, R.array.tweaking_programmingSpanish));
                            PAGES.add(new Page(mContext, R.array.late_build_changesSpanish));
                            PAGES.add(new Page(mContext, R.array.bag_and_tagSpanish));
                            break;
                    }
                    break;
                case 1:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.pits_designSpanish));
                            PAGES.add(new Page(mContext, R.array.pits_organizationSpanish));
                            PAGES.add(new Page(mContext, R.array.pits_crewSpanish));
                            PAGES.add(new Page(mContext, R.array.pits_judgesSpanish));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.scouting_matchSpanish));
                            PAGES.add(new Page(mContext, R.array.scouting_pitsSpanish));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.chairmans_aboutSpanish));
                            PAGES.add(new Page(mContext, R.array.chairmans_writingSpanish));
                            PAGES.add(new Page(mContext, R.array.chairmans_speechSpanish));
                            PAGES.add(new Page(mContext, R.array.chairmans_videoSpanish));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.spirit_importanceSpanish));
                            PAGES.add(new Page(mContext, R.array.spirit_team_imageSpanish));
                            PAGES.add(new Page(mContext, R.array.spirit_swagSpanish));
                            break;
                    }
                    break;
                case 2:
                    switch (child) {
                        case 0:
                            PAGES.add(new Page(mContext, R.array.fundraising_obtaining_sponsorsSpanish));
                            PAGES.add(new Page(mContext, R.array.fundraising_grantsSpanish));
                            PAGES.add(new Page(mContext, R.array.fundraising_major_eventsSpanish));
                            PAGES.add(new Page(mContext, R.array.fundraising_minor_eventsSpanish));
                            break;
                        case 1:
                            PAGES.add(new Page(mContext, R.array.tournaments_about));
                            break;
                        case 2:
                            PAGES.add(new Page(mContext, R.array.outreach_aboutSpanish));
                            PAGES.add(new Page(mContext, R.array.outreach_mentoringSpanish));
                            PAGES.add(new Page(mContext, R.array.outreach_demonstrationsSpanish));
                            break;
                        case 3:
                            PAGES.add(new Page(mContext, R.array.preparing_for_next_yearSpanish));
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
