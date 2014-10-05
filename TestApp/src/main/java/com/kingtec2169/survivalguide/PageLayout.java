package com.kingtec2169.survivalguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

// Provides an interface for loading a page layout representation from XML to t

public interface PageLayout {
    public int getNumParameters();
    public void setupView(@NonNull View rootView, @NonNull Page content);
    public void setupPage(@NonNull Page page, @NonNull String[] contentResources, @NonNull Context context);
}
