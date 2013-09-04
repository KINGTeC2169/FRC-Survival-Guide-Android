package com.kingtec2169.survivalguide;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rtzoeller on 8/27/13.
 */
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
        WeekListActivity activity = ((WeekListActivity)getActivity());
        activity.page = item[2];
        activity.onChildClick(item[0], item[1]);
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
                for (int i = 0; i < WeekContent.CHILDREN.size(); i++) {
                    for (int j = 0; j < WeekContent.CHILDREN.get(i).size(); j++) {
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
