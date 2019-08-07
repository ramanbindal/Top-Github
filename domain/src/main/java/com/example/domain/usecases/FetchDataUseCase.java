package com.example.domain.usecases;

import com.example.domain.base.SingleUseCase;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.model.Repository;
import com.example.domain.repository.SampleRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class FetchDataUseCase extends SingleUseCase<List<Repository>, FetchDataUseCase.Params> {

    //take repository here
    private SampleRepository sampleRepository;

    public FetchDataUseCase(PostExecutionThread postExecutionThread, SampleRepository sampleRepository) {
        super(postExecutionThread);
        this.sampleRepository = sampleRepository;
    }

    @Override
    public Single<List<Repository>> buildUseCaseObservable(final Params params) {
        return sampleRepository.fetchData().flatMap(new Function<List<Repository>, SingleSource<? extends List<Repository>>>() {
            @Override
            public SingleSource<? extends List<Repository>> apply(List<Repository> repositories) throws Exception {


                List<Single<Repository>> singles = new ArrayList<>();
                for (int i = 0; i < repositories.size(); i++) {

                    singles.add(sampleRepository.fetchImage(repositories.get(i)).onErrorReturnItem(repositories.get(i)));
                }

                return Single.zip(singles, new Function<Object[], List<Repository>>() {
                    @Override
                    public List<Repository> apply(Object[] objects) throws Exception {
                        List<Repository> imageDataList = new ArrayList<>();
                        for (Object i : objects) {
                            Repository imageData = (Repository) i;
                            imageDataList.add(imageData);
                        }
                        return imageDataList;
                    }
                });


            }
        });
    }

    public static final class Params {

        public Params() {
        }

        public static FetchDataUseCase.Params fetchData() {
            return new FetchDataUseCase.Params();
        }
    }
}

