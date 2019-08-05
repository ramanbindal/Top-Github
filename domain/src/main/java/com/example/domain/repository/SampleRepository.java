package com.example.domain.repository;

import com.example.domain.model.Repository;

import java.util.List;

import io.reactivex.Single;

public interface SampleRepository {


    Single<List<Repository>> fetchData();




}
