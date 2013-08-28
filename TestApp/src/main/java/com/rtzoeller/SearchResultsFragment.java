package com.rtzoeller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rtzoeller on 8/27/13.
 */
public class SearchResultsFragment extends SherlockListFragment {
    public static final String ARG_SEARCH_ID = "search_id";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey(ARG_SEARCH_ID)) {
            new CreateArrayListTask().execute(getArguments().getString(ARG_SEARCH_ID));
        }
    }

    private class CreateArrayListTask extends AsyncTask<String, Void, ArrayList<int[]>> {
        private final ProgressDialog dialog = new ProgressDialog(getSherlockActivity());
        private Context context;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Fetching results...");
            this.dialog.show();

            attemptContextUpdate();
        }
        @Override
        protected ArrayList<int[]> doInBackground(String...keywords) {
            ArrayList<int[]> results = new ArrayList<int[]>();
            PageContent content = new PageContent(getSherlockActivity());
            for (String keyword : keywords) {
                for (int i = 0; i < WeekContent.CHILDREN.size(); i++) {
                    for (int j = 0; j < WeekContent.CHILDREN.get(i).size(); j++) {
                        for (int k = 0; k < content.numPages[i][j]; k++) {
                            Page page = content.get(i, j, k);
                            if (page != null && page.text != null && page.title != null) {
                                if (page.title.contains(keyword.toLowerCase()) || page.text.contains(keyword.toLowerCase())) {
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
            if(this.dialog.isShowing())
                this.dialog.dismiss();
            attemptContextUpdate();
            setListAdapter(new SearchResultsAdapter(context, android.R.layout.simple_list_item_1, results));
        }

        private void attemptContextUpdate() {
            if(getSherlockActivity() != null)
                context = getSherlockActivity();
        }
    }

}
