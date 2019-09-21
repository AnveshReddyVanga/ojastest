package com.example.ojastest.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ojastest.service.APICall;
import com.example.ojastest.dtos.Hit;
import com.example.ojastest.dtos.Ojas;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private CompositeDisposable disposable;
    public int totalSize = 0;
    public MutableLiveData<List<Hit>> listMutableLiveData = new MutableLiveData<>();

    public MainViewModel() {
        disposable = new CompositeDisposable();
        getOjas();
    }

    protected void getOjas() {
        disposable.add(APICall.getRetrofit().getHeroes("story", 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Ojas>() {
                    @Override
                    public void onSuccess(Ojas value) {
                        totalSize = value.getNbPages()*value.getHitsPerPage();
                        listMutableLiveData.postValue(value.getHits());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    public void getMoreOjas(int pageNumber) {
        disposable.add(APICall.getRetrofit().getHeroes("story", pageNumber).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Ojas>() {
                    @Override
                    public void onSuccess(Ojas value) {
                        listMutableLiveData.postValue(value.getHits());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
