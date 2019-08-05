package com.example.domain.repository;

import com.example.domain.model.Response;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface SampleRepository {


    Single<List<Response>> fetchData();




}
