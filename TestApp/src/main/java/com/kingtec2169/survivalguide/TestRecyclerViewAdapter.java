package com.kingtec2169.survivalguide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rtzoeller on 7/8/14.
 */
public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private List<ViewModel> items;
    private OnRecyclerViewItemClickListener<ViewModel> itemClickListener;
    private int itemLayout;

    public TestRecyclerViewAdapter(List<ViewModel> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ViewModel item = items.get(i);
        viewHolder.itemView.setTag(item);
        viewHolder.text1.setText(item.getText1());
        viewHolder.text2.setText(item.getText2());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(ViewModel item, int position) {
        items.add(position, item);
        notifyItemInserted(position);

    }

    public void remove(ViewModel item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            ViewModel model = (ViewModel) view.getTag();
            itemClickListener.onItemClick(view, model);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<ViewModel> listener) {
        this.itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;

        private ViewHolder(View view) {
            super(view);
            this.text1 = (TextView) view.findViewById(R.id.text1);
            this.text2 = (TextView) view.findViewById(R.id.text2);
        }
    }

}
