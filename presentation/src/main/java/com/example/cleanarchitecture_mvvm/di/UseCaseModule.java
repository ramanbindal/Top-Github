package com.example.cleanarchitecture_mvvm.di;

import com.example.domain.executor.PostExecutionThread;
import com.example.domain.repository.SampleRepository;
import com.example.domain.usecases.FetchDataUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {

    @Provides
    FetchDataUseCase provideFetchDataUseCase(PostExecutionThread postExecutionThread, SampleRepository sampleRepository) {
        return new FetchDataUseCase(postExecutionThread, sampleRepository);
    }
}
