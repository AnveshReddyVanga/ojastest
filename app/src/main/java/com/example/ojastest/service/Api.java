package com.example.ojastest.service;

import com.example.ojastest.dtos.Ojas;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "https://hn.algolia.com";
    @GET("/api/v1/search_by_date?")
    Single<Ojas> getHeroes(@Query("tags") String tags, @Query("page") int page);
}