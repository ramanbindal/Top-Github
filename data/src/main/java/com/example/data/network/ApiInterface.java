package com.example.data.network;

import com.example.data.models.ResponseNw;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/developers")
    Single<List<ResponseNw>> fetchData(@Query("language") String lang, @Query("since") String since);

}
