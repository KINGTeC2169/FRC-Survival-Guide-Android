package com.kingtec2169.survivalguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

// Created by Ryan Zoeller

public class PageLayoutTextOnly implements PageLayout {
    private static final int numParameters = 4;

    @Override
    public int getNumParameters() {
        return numParameters;
    }

    @Override
    public void setupView(@NonNull View rootView, @NonNull Page content) {
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
        page.tags = contentResources[3];
    }
}
