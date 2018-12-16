package com.mylovin.web.model;

public class Comment {
    private String rowKey;
    private String title;
    private String star;
    private String comment;

    public Comment(String rowKey, String title, String star, String comment) {
        this.rowKey = rowKey;
        this.title = title;
        this.star = star;
        this.comment = comment;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
