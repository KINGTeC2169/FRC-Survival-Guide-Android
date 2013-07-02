package com.rtzoeller;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class Page {
    public int textResourceId;
    public int titleResourceId;
    public int imageResourceId;

    public Page() {
        super();
    }

    public Page(int titleResourceId, int textResourceId, int imageResourceId) {
        super();
        this.titleResourceId = titleResourceId;
        this.textResourceId = textResourceId;
        this.imageResourceId = imageResourceId;
    }
}
