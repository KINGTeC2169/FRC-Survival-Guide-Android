package com.kingtec2169.survivalguide;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rtzoeller on 8/29/14.
 */
public class ContentIdHolder implements Parcelable {
    public int groupPosition;
    public int childPosition;
    public int page;

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
}
