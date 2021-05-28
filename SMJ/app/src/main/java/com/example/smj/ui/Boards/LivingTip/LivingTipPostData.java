package com.example.smj.ui.Boards.LivingTip;

import android.net.Uri;

import com.example.smj.data.entity.Member.MemberData;

public class LivingTipPostData {
    private int id;
    private String category;
    private String title;
    private String contents;
    private String writer;
    private String[] date;
    private String email;
    private String imageOne;
    private String imageTwo;
    private String imageThree;

    public LivingTipPostData(int id, String category, String title, String contents, String writer, String[] date, String email, String imageOne, String imageTwo, String imageThree) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.writer = writer;
        this.date = date;
        this.email = email;
        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        this.imageThree = imageThree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getImageOne() {
        return imageOne;
    }

    public void setImageOne(String imageOne) {
        this.imageOne = imageOne;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
