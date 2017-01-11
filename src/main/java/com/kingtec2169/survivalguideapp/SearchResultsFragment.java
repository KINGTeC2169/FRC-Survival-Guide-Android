package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// This fragment is responsible for searching for the correct content and
// displaying it as a List. Searching is done by forcing all titles, content and tags
// to lowercase and looking for a direct match of the lowercase input.

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchResultsFragment extends ListFragment {

    public interface HandlesPaging {
        void goToPage(ContentIdHolder contentId);
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
        ContentIdHolder item = ((SearchResultsAdapter)l.getAdapter()).results.get(position);
        HandlesPaging activity = (HandlesPaging)getActivity();
        activity.goToPage(item);
    }

    private class CreateArrayListTask extends AsyncTask<String, Void, ArrayList<ContentIdHolder>> {
        private Context context;

        @Override
        protected void onPreExecute() {
            attemptContextUpdate();
        }
        @Override
        protected ArrayList<ContentIdHolder> doInBackground(String...keywords) {
            ArrayList<ContentIdHolder> results = new ArrayList<ContentIdHolder>();
            PageContent.refresh(getActivity());
            for (String keyword : keywords) {
                String lowerKeyword = keyword.toLowerCase();
                for (int i = 0; i < NavigationListContent.CHILDREN.size(); i++) {
                    for (int j = 0; j < NavigationListContent.CHILDREN.get(i).size(); j++) {
                        for (int k = 0; k < PageContent.numPages[i][j]; k++) {
                            Page page = PageContent.get(i, j, k);
                            if (page != null) {
                                if (page.title.toLowerCase().contains(lowerKeyword)
                                        || page.text.toLowerCase().contains(lowerKeyword)
                                        || page.tags.toLowerCase().contains(lowerKeyword)) {
                                    results.add(new ContentIdHolder(i, j, k));
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
        protected void onPostExecute(ArrayList<ContentIdHolder> results) {
            attemptContextUpdate();
            setListAdapter(new SearchResultsAdapter(context, android.R.layout.simple_list_item_1, results));
        }

        private void attemptContextUpdate() {
            if(getActivity() != null)
                context = getActivity();
        }
    }
}
