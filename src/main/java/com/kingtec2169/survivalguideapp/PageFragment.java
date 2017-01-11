package com.kingtec2169.survivalguideapp;

// Created by Ryan Zoeller/Matthew Zwinger of FIRST FRC team 2169.
// This fragment binds the content to the selected layout and its views. A reference to the
// selected content is passed in through a bundle, which is then looked up from the PageContent
// class.

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {
    private static final String KEY_CONTENT_ID = "contentId";
    public ContentIdHolder contentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the index of the content we want to show
        if (getArguments() != null) {
            contentId = getArguments().getParcelable(KEY_CONTENT_ID);
        } else {
            contentId = new ContentIdHolder(0, 0, 0);
        }
    }

    static PageFragment newInstance(ContentIdHolder contentId) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();

        // Pass the index of the content to show to the fragment
        args.putParcelable(KEY_CONTENT_ID, contentId);
        frag.setArguments(args);

        return(frag);
    }

    static String getTitle(Context context, int group, int child, int position) {
        // Use the index to retrieve the correct content and its title
        PageContent.refresh(context);
        return(PageContent.get(group, child, position).title);
    }

    static String getTitle(Context context, ContentIdHolder contentId) {
        // Use the index to retrieve the correct content and its title
        PageContent.refresh(context);
        return(PageContent.get(contentId).title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PageContent.refresh(getActivity());
        Page content = PageContent.get(contentId);

        View result = inflater.inflate(content.layoutResourceId, container, false);
        PageLayout pageLayout;
        switch (content.layoutResourceId) {
            case R.layout.fragment_pager_item:
                pageLayout = new PageLayoutTextAndImage();
                break;
            case R.layout.fragment_pager_item_no_image:
                pageLayout = new PageLayoutTextOnly();
                break;
            default:
                pageLayout = new PageLayoutTextOnly(); // Fallback to a simple implementation
                break;
        }

        pageLayout.setupView(result, content);

        return(result);
    }
}