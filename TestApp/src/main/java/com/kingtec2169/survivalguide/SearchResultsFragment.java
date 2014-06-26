package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This fragment is responsible for searching for the correct content and
// displaying it as a List. Searching is done by forcing all titles, content and tags
// to lowercase and looking for a direct match of the lowercase input.

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsFragment extends ListFragment {

    public interface HandlesPaging {
        void goToPage(int group, int child, int page);
    }

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
        HandlesPaging activity = (HandlesPaging)getActivity();
        activity.goToPage(item[0], item[1], item[2]);
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
