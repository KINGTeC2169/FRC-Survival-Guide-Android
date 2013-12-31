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

        // Swap in content for the placeholders
        if(content[0] != null) {
            if(content[1] != null) {
                // There is two pages of content
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.left, content[0])
                        .replace(R.id.right, content[1])
                        .commit();
            } else {
                // There is one page of content
                result.findViewById(R.id.right).setVisibility(View.GONE);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.left, content[0])
                        .commit();
            }
        }

        return result;
    }

    public void setLeft(Fragment left) {
        content[0] = left;
    }

    public void setRight(Fragment right) {
        content[1] = right;
    }
}
