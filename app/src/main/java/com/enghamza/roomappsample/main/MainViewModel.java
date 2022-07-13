package com.enghamza.roomappsample.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.enghamza.roomappsample.data.database.PostsDataBase;
import com.enghamza.roomappsample.model.Post;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
   private final MutableLiveData<List<Post>>mutableLiveData=new MutableLiveData<>();
   private final MutableLiveData<String>insertPostLiveData=new MutableLiveData<>();

    public  void  observePosts(Context context){

        Observable<List<Post>> observable= PostsDataBase.getInstance(context).dao().getPosts();

        Observer<List<Post>> observer=new Observer<List<Post>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Post> value) {
                mutableLiveData.setValue(value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.toString());
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

        .subscribe(observer);
        //Or
       // .subscribe(value->mutableLiveData.setValue(value),error->mutableLiveData.setValue(null));

    }

    public  MutableLiveData<List<Post>> getPosts(){
        return  mutableLiveData;
    }

    public   MutableLiveData<String> getResultInsertPosts(){
        return  insertPostLiveData;
    }

    public  void  inserPost(Context context,Post post){
        PostsDataBase.getInstance(context).dao().insertPost(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        insertPostLiveData.setValue("Success");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        insertPostLiveData.setValue("Error"+e);

                    }
                });
    }
}
