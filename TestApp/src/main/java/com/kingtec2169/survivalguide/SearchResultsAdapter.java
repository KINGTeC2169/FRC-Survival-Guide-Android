package com.kingtec2169.survivalguide;

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
    public ArrayList<int[]> results;

    public SearchResultsAdapter(Context context, int textViewResourceId, ArrayList<int[]> results) {
        super(context, textViewResourceId, results);
        this.context = context;
        this.results = results;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            // There isn't a cached view, so we need to inflate one
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(android.R.layout.simple_list_item_1, null);

            // Find the TextView we want to modify and bind it to our holder class
            holder = new ViewHolder();
            holder.title = (TextView) view.findViewById(android.R.id.text1);

            view.setTag(holder);
        } else {
            // There is a cached view, we should recycle it
            holder = (ViewHolder) view.getTag();
        }

        // Get which data we should show
        int[] key = results.get(position);
        if (key != null) {
            // Update the context of our content
            PageContent content = new PageContent(context);
            // Get the data from our content
            Page result = content.get(key[0], key[1], key[2]);
            // Bind the data to a TextView
            holder.title.setText(result.title);
        }

        return view;
    }

    private static class ViewHolder {
        // Acts as a cache for our content
        TextView title;
    }
}
