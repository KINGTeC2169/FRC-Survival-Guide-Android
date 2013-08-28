package com.rtzoeller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rtzoeller on 8/27/13.
 */
public class SearchResultsAdapter extends ArrayAdapter<int[]> {
    private Context context;
    private ArrayList<int[]> results;

    public SearchResultsAdapter(Context context, int textViewResourceId, ArrayList<int[]> results) {
        super(context, textViewResourceId, results);
        this.context = context;
        this.results = results;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }

        int[] key = results.get(position);
        if (key != null) {
            PageContent content = new PageContent(context);
            Page result = content.get(key[0], key[1], key[2]);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            if (textView != null) {
               textView.setText(result.title);
            }
        }

        return view;
    }
}
