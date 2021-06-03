package com.example.smj.data.entity.board;

import com.google.gson.annotations.SerializedName;

public class boardCategory {
    private int id;
    private String name;

    public boardCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
