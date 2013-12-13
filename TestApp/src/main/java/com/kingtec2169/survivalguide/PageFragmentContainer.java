package com.kingtec2169.survivalguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rtzoeller on 12/11/13.
 */
public class PageFragmentContainer extends Fragment {
    private Fragment[] content = new Fragment[2];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_combined_content, container, false);

        // Make sure there is a fragment in every entry to prevent null pointer exceptions
        // by creating fragments where none exist
        // TODO: Make content expand to fill the frame if only one fragment exists
        for (int i = 0; i < content.length; i++) {
            if (content[i] == null)
                content[i] = new Fragment();
        }

        // Swap in the content for the placeholders
        getChildFragmentManager().beginTransaction()
                .replace(R.id.left, content[0])
                .replace(R.id.right, content[1])
                .commit();

        return result;
    }

    public void setLeft(Fragment left) {
        content[0] = left;
    }

    public void setRight(Fragment right) {
        content[1] = right;
    }
}
