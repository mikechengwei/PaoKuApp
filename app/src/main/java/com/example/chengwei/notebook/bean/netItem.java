package com.example.chengwei.notebook.bean;

/**
 * Created by chengwei on 3/26/16.
 */
public class netItem {
    private String id;
    private String context;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "TestItem{" +
                "id='" + id + '\'' +
                ", context='" + context + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public void setContext(String context) {
        this.context = context;
    }







    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

}
