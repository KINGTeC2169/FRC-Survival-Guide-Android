package com.kingtec2169.survivalguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// Created by Ryan Zoeller

public class PageLayoutTextAndImage implements PageLayout {
    private static final int numParameters = 5;

    @Override
    public int getNumParameters() {
        return numParameters;
    }

    @Override
    public void setupView(@NonNull View rootView, @NonNull Page content) {
        ((ImageView)rootView.findViewById(R.id.pageImage)).setImageResource(content.imageResourceId);
        TextView textView = ((TextView)rootView.findViewById(R.id.pageDescription));
        // Parse the text with an HTML parser to recognize links and formatting
        textView.setText(Html.fromHtml(content.text));
        // Make links clickable
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void setupPage(@NonNull Page page, @NonNull String[] contentResources, @NonNull Context context) {
        page.title = contentResources[1];
        page.text = contentResources[2];
        page.imageResourceId = context.getResources().getIdentifier(contentResources[3], "drawable", context.getPackageName());
        page.tags = contentResources[4];
    }
}
