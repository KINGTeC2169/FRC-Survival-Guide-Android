// Copyright 2014 FRC team 2169 KING TeC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This fragment is responsible for searching for the correct content and
// displaying it as a List. Searching is done by forcing all titles, content and tags
// to lowercase and looking for a direct match of the lowercase input.

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsFragment extends ListFragment {
    public static final String ARG_SEARCH_ID = "search_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_SEARCH_ID)) {
            new CreateArrayListTask().execute(getArguments().getString(ARG_SEARCH_ID));
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        int[] item = ((SearchResultsAdapter)l.getAdapter()).results.get(position);
        MainActivity activity = ((MainActivity)getActivity());
        activity.page = item[2];
        // Call onChildClick with null arguments to indicate that the method should not
        // reset the page to 0
        activity.onChildClick(null, null, item[0], item[1], 0);
    }

    private class CreateArrayListTask extends AsyncTask<String, Void, ArrayList<int[]>> {
        private Context context;

        @Override
        protected void onPreExecute() {
            attemptContextUpdate();
        }
        @Override
        protected ArrayList<int[]> doInBackground(String...keywords) {
            ArrayList<int[]> results = new ArrayList<int[]>();
            PageContent content = new PageContent(getActivity());
            for (String keyword : keywords) {
                String lowerKeyword = keyword.toLowerCase();
                for (int i = 0; i < NavigationListContent.CHILDREN.size(); i++) {
                    for (int j = 0; j < NavigationListContent.CHILDREN.get(i).size(); j++) {
                        for (int k = 0; k < content.numPages[i][j]; k++) {
                            Page page = content.get(i, j, k);
                            if (page != null) {
                                if (page.title.toLowerCase().contains(lowerKeyword)
                                        || page.text.toLowerCase().contains(lowerKeyword)
                                        || page.tags.toLowerCase().contains(lowerKeyword)) {
                                    int[] result = {i, j, k};
                                    results.add(result);
                                }
                            }
                        }
                    }
                }
            }
            attemptContextUpdate();
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<int[]> results) {
            attemptContextUpdate();
            setListAdapter(new SearchResultsAdapter(context, android.R.layout.simple_list_item_1, results));
        }

        private void attemptContextUpdate() {
            if(getActivity() != null)
                context = getActivity();
        }
    }
}
