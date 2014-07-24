package com.kingtec2169.survivalguide;

import android.view.View;

/**
 * Created by rtzoeller on 7/9/14.
 */
public interface OnRecyclerViewItemClickListener<Model> {
    public void onItemClick(View view, Model model);
}