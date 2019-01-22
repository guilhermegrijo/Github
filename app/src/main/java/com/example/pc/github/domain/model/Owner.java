package com.example.pc.github.domain.model;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class Owner {

    @SerializedName("login")
    public final String login;

    public Owner(@NonNull String login) {
        this.login = login;
    }
}