package com.rtzoeller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by rtzoeller on 6/24/13.
 */
public class WeekAdapter extends ArrayAdapter<Week> {
    Context context;
    int layoutResourceId;
    Week data[] = null;

    public WeekAdapter(Context context, int layoutResourceId, Week[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeekHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeekHolder();
            holder.name = (TextView)row.findViewById(R.id.weekName);
            holder.description = (TextView)row.findViewById(R.id.weekDescription);
            row.setTag(holder);
        }
        else {
            holder = (WeekHolder)row.getTag();
        }

        Week week = data[position];
        holder.name.setText(week.name);
        holder.description.setText(week.description);

        return row;
    }

    static class WeekHolder {
        TextView name;
        TextView description;
    }
}
