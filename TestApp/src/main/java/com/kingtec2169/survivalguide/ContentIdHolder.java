package com.kingtec2169.survivalguide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rtzoeller on 8/29/14.
 */
public class ContentIdHolder implements Parcelable {
    private int groupPosition;
    private int childPosition;
    private int page;

    public ContentIdHolder(int groupPosition, int childPosition, int page) {
        this.groupPosition = groupPosition;
        this.childPosition = childPosition;
        this.page = page;
    }

    public ContentIdHolder(Parcel in) {
        int[] data = new int[3];
        in.readIntArray(data);

        this.groupPosition = data[0];
        this.childPosition = data[1];
        this.page = data[2];
    }

    public static ContentIdHolder newInstance(ContentIdHolder contentId) {
        return new ContentIdHolder(contentId.groupPosition, contentId.childPosition, contentId.page);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeIntArray(new int[]{this.groupPosition, this.childPosition, this.page});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ContentIdHolder createFromParcel(Parcel in) {
            return new ContentIdHolder(in);
        }

        public ContentIdHolder[] newArray(int size) {
            return new ContentIdHolder[size];
        }
    };

    public int getPage() {
        return page;
    }

    public ContentIdHolder setPage(int page) {
        this.page = page;
        return this;
    }

    public int getGroupPosition() {
        return groupPosition;
    }

    public ContentIdHolder setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
        return this;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public ContentIdHolder setChildPosition(int childPosition) {
        this.childPosition = childPosition;
        return this;
    }

    @Override
    public String toString() {
        return "ContentIdHolder{" +
                "groupPosition=" + groupPosition +
                ", childPosition=" + childPosition +
                ", page=" + page +
                '}';
    }
}
