package com.example.navigation;

public class Image {
    String title;
    String path;
    String date;

    public Image(String title, String path, String date) {
        this.title = title;
        this.path = path;
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}