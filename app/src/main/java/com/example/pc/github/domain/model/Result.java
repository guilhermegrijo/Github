package com.example.pc.github.domain.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {


    @SerializedName("items")
    private List<Item> items = null;


    public List<Item> getItems() {
        return items;
    }
}