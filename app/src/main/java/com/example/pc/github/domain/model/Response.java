package com.example.pc.github.domain.model;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.pc.github.domain.model.Status.ERROR;
import static com.example.pc.github.domain.model.Status.LOADING;
import static com.example.pc.github.domain.model.Status.SUCCESS;

public class Response {

    public final Status status;

    @Nullable
    public final List<Item> data;

    @Nullable
    public final Throwable error;

    private Response(Status status, @Nullable List<Item> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static Response loading() {
        return new Response(LOADING, null, null);
    }

    public static Response success(@NonNull List<Item> data) {
        return new Response(SUCCESS, data, null);
    }

    public static Response error(@NonNull Throwable error) {
        return new Response(ERROR, null, error);
    }
}