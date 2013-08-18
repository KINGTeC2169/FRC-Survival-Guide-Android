package com.rtzoeller;

/**
 * Created by rtzoeller on 6/24/13.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        group = getArguments() != null ? getArguments().getInt(KEY_GROUP) : 0;
        child = getArguments() != null ? getArguments().getInt(KEY_CHILD) : 0;
        position = getArguments() != null ? getArguments().getInt(KEY_POSITION) : 1;
    }

    static PageFragment newInstance(int group, int child, int position) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_GROUP, group);
        args.putInt(KEY_CHILD, child);
        frag.setArguments(args);

        return(frag);
    }

    static String getTitle(Context context, int group, int child, int position) {
        PageContent content = new PageContent(context);
        return(content.get(group, child, position).title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Page pageContent = PageContent.get(group, child, position);

        View result = inflater.inflate(R.layout.fragment_pager_item, container, false);
        ((ImageView)result.findViewById(R.id.pageImage)).setImageResource(pageContent.imageResourceId);
        ((TextView)result.findViewById(R.id.pageDescription)).setText(pageContent.text);

        return(result);
    }
}