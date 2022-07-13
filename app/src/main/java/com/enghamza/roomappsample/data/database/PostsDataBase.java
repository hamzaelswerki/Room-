package com.enghamza.roomappsample.data.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.enghamza.roomappsample.dao.PostsDao;
import com.enghamza.roomappsample.model.Post;

@Database(entities = Post.class,version = 2)
abstract public class PostsDataBase extends RoomDatabase {

    private static PostsDataBase instance;

    public  abstract PostsDao dao();

    public  synchronized static  PostsDataBase getInstance(Context context){
            if (instance==null)
                instance= Room.databaseBuilder(context, PostsDataBase.class,"posts_databse")
                        .fallbackToDestructiveMigration()
                        .build();
            return  instance;
    }

}
