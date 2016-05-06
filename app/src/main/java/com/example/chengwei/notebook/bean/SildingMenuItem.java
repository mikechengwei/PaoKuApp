package com.example.chengwei.notebook.bean;

/**
 * Created by admin on 2016/1/20.
 */
public class SildingMenuItem {
    private int imageView;
    private String text;

    public SildingMenuItem(int imageView, String text) {

        this.imageView = imageView;
        this.text = text;
    }
    public SildingMenuItem(String text) {


        this.text = text;
    }

    public int getImageView() {

        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
