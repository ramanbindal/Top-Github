package com.example.domain.usecases;

import com.example.domain.base.SingleUseCase;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.model.Repository;
import com.example.domain.repository.SampleRepository;

import java.util.List;

import io.reactivex.Single;

public class FetchDataUseCase extends SingleUseCase<List<Repository>, FetchDataUseCase.Params> {

    //take repository here
    private SampleRepository sampleRepository;

    public FetchDataUseCase(PostExecutionThread postExecutionThread, SampleRepository sampleRepository) {
        super(postExecutionThread);
        this.sampleRepository = sampleRepository;
    }

    @Override
    public Single<List<Repository>> buildUseCaseObservable(final Params params) {
        return sampleRepository.fetchData();
    }

    public static final class Params {

        public Params() {
        }

        public static FetchDataUseCase.Params fetchData() {
            return new FetchDataUseCase.Params();
        }
    }
}

