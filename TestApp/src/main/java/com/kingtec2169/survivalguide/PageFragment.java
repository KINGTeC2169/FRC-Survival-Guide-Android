package com.kingtec2169.survivalguide;

// Created by Ryan Zoeller of FIRST FRC team 2169.
// This fragment binds the content to the selected layout and its views. A reference to the
// selected content is passed in through a bundle, which is then looked up from the PageContent
// class.

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PageFragment extends Fragment {
    private static final String KEY_POSITION = "position";
    private static final String KEY_GROUP = "group";
    private static final String KEY_CHILD = "child";

    int group;
    int child;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the index of the content we want to show
        group = getArguments() != null ? getArguments().getInt(KEY_GROUP) : 0;
        child = getArguments() != null ? getArguments().getInt(KEY_CHILD) : 0;
        position = getArguments() != null ? getArguments().getInt(KEY_POSITION) : 1;
    }

    static PageFragment newInstance(int group, int child, int position) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();

        // Pass the index of the content to show to the fragment
        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_GROUP, group);
        args.putInt(KEY_CHILD, child);
        frag.setArguments(args);

        return(frag);
    }

    static String getTitle(Context context, int group, int child, int position) {
        // Use the index to retrieve the correct content and its title
        PageContent content = new PageContent(context);
        return(content.get(group, child, position).title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PageContent pageContent = new PageContent((getActivity()));
        Page content = pageContent.get(group, child, position);

        View result = inflater.inflate(content.layoutResourceId, container, false);
        switch (content.layoutResourceId) {
            case R.layout.fragment_pager_item:
                ((ImageView)result.findViewById(R.id.pageImage)).setImageResource(content.imageResourceId);
                TextView textView =((TextView)result.findViewById(R.id.pageDescription));
                // Parse the text with an HTML parser to recognize links and formatting
                textView.setText(Html.fromHtml(content.text));
                // Make links clickable
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                break;
            default:
                break;
        }

        return(result);
    }
}