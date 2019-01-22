package com.example.pc.github.domain.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Item {

    public final int id;

    @SerializedName("name")
    public final String name;

    @SerializedName("description")
    public final String description;

    @SerializedName("stargazers_count")
    public final int stars;

    @SerializedName("html_url")
    public final String url;

    @SerializedName("owner")
    @NonNull
    public final Owner owner;

    public Item(int id, String name, String description, Owner owner, int stars, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.stars = stars;
        this.url = url;
    }
}
