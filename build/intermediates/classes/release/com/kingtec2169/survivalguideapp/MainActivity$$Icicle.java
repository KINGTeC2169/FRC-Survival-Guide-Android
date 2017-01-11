// Generated code from Icepick. Do not modify!
package com.kingtec2169.survivalguideapp;
import android.os.Bundle;
import android.os.Parcelable;
import icepick.StateHelper;
public class MainActivity$$Icicle implements StateHelper<Bundle> {

  private static final String BASE_KEY = "com.kingtec2169.survivalguideapp.MainActivity$$Icicle.";
  private final StateHelper<Bundle> parent = (StateHelper<Bundle>) StateHelper.NO_OP;

  public Bundle restoreInstanceState(Object obj, Bundle state) {
    MainActivity target = (MainActivity) obj;
    if (state == null) {
      return null;
    }
    Bundle savedInstanceState = state;

    target.contentId = (com.kingtec2169.survivalguideapp.ContentIdHolder) savedInstanceState.getParcelable(BASE_KEY + "contentId");
    target.expandedItems =  savedInstanceState.getBooleanArray(BASE_KEY + "expandedItems");

    return parent.restoreInstanceState(target, savedInstanceState);
  }
  public Bundle saveInstanceState(Object obj, Bundle state) {
    MainActivity target = (MainActivity) obj;
    parent.saveInstanceState(target, state);
    Bundle outState = state;

    outState.putParcelable(BASE_KEY + "contentId", target.contentId);
    outState.putBooleanArray(BASE_KEY + "expandedItems", target.expandedItems);

    return outState;
  }
}