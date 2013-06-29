package com.rtzoeller;

/**
 * Created by rtzoeller on 6/27/13.
 */
public class Page {
    public String text;
    public String title;
    public int imageResourceId;

    public Page() {
        super();
    }

    public Page(String title, String text, int imageResourceId) {
        super();
        this.title = title;
        this.text = text;
        this.imageResourceId = imageResourceId;
    }
}
