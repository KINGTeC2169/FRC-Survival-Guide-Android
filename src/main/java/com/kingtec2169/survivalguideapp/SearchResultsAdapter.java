package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// This adapter connects content returned from searching the app to a
// ListView. This adapter also acts as a holder to cache the text, so that the
// titles are not read from XML on every re-draw. This is especially important as
// getting the titles potentially requires PageContent to re-load content from
// multiple blocks, causing heavy performance penalties.

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultsAdapter extends ArrayAdapter<ContentIdHolder> {
    private final Context context;
    public final ArrayList<ContentIdHolder> results;

    public SearchResultsAdapter(Context context, @LayoutRes int textViewResourceId, ArrayList<ContentIdHolder> results) {
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
        ContentIdHolder contentId = results.get(position);
        if (contentId != null) {
            // Update the context of our content
            PageContent.refresh(context);
            // Get the data from our content
            Page result = PageContent.get(contentId);
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
