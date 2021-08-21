package com.alfred.moviefiledownloadertask.ui.mainActivity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alfred.moviefiledownloadertask.Repository.MovieRepository;
import com.alfred.moviefiledownloadertask.data.response.Movie;
import com.alfred.moviefiledownloadertask.data.response.APIStatus;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    MovieRepository repository;
    private MutableLiveData<List<Movie>> responseLiveData ;
    private MutableLiveData<APIStatus> status;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public MainActivityViewModel(MovieRepository authenticationRepository) {
        responseLiveData = new MutableLiveData<>();
        status = new MutableLiveData<>();
        this.repository=authenticationRepository;
    }

    public void moviesList(){
        try {
            disposable.add(
                    repository.getMovie()
                            .doOnSubscribe(__ -> status.postValue(APIStatus.LOADING))
                            .subscribe(listAPIResponseCategories -> {

                                try {
                                    responseLiveData.setValue(listAPIResponseCategories);
                                    status.postValue(APIStatus.SUCCESS);
                                }catch (Exception ex){

                                }

                            }, throwable -> {

                                status.postValue(APIStatus.ERROR);
                            }));
        }catch (Exception e){
            status.postValue(APIStatus.ERROR);
        }
    }

    public MutableLiveData<List<Movie>> getResponseLiveData() {
        return responseLiveData;
    }

    public void setResponseLiveData(MutableLiveData<List<Movie>> responseLiveData) {
        this.responseLiveData = responseLiveData;
    }

    public MutableLiveData<APIStatus> getStatus() {
        return status;
    }
}