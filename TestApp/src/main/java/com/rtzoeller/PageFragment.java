package com.rtzoeller;

/**
 * Created by rtzoeller on 6/24/13.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PageFragment extends Fragment {
    private static final String KEY_POSITION = "position";
    private static final String KEY_ID = "id";

    int id;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments() != null ? getArguments().getInt(KEY_ID) : 1;
        position = getArguments() != null ? getArguments().getInt(KEY_POSITION) : 1;
    }

    static PageFragment newInstance(int id, int position) {
        PageFragment frag = new PageFragment();
        Bundle args = new Bundle();

        args.putInt(KEY_POSITION, position);
        args.putInt(KEY_ID, id);
        frag.setArguments(args);

        return(frag);
    }

    static String getTitle(int id, int position) {
        return(PageContent.PAGE_MAP.get(new PageContent.Key(id, position)).title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Page pageContent = PageContent.PAGE_MAP.get(new PageContent.Key(id, position));

        View result = inflater.inflate(R.layout.fragment_pager_item, container, false);
        ((ImageView)result.findViewById(R.id.pageImage)).setImageResource(pageContent.imageResourceId);
        ((TextView)result.findViewById(R.id.pageDescription)).setText(pageContent.text);

        return(result);
    }
}