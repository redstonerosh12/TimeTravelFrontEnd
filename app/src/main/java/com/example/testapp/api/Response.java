package com.example.testapp.api;

public interface Response<T> {
    void onResponse(T response);
}
