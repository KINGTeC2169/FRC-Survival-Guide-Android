package com.kingtec2169.survivalguide;

import java.util.List;

/**
* Created by rtzoeller on 7/8/14.
*/
public class ViewModel {
    private int groupId;
    private int childId;
    private String text1;
    private String text2;
    private boolean isExpanded;
    private List<ViewModel> children;

    public ViewModel(int groupId, int childId, String text1, String text2, List<ViewModel> children) {
        this.groupId = groupId;
        this.childId = childId;
        this.text1 = text1;
        this.text2 = text2;
        this.isExpanded = false;
        this.children = children;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text) {
        this.text1 = text;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text) {
        this.text2 = text;
    }

    public boolean isGroupHeader() {
        return this.children != null;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public List<ViewModel> getChildren() {
        return children;
    }

    public void setChildren(List<ViewModel> children) {
        this.children = children;
    }

}
