package com.enghamza.roomappsample.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.enghamza.roomappsample.model.Post;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;

@Dao
public interface PostsDao {

    @Insert
    Completable insertPost(Post post);

    @Query("select * from post_table")
    Observable<List<Post>> getPosts();
}
