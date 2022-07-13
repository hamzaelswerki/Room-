package com.enghamza.roomappsample.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enghamza.roomappsample.R;
import com.enghamza.roomappsample.data.database.PostsDataBase;
import com.enghamza.roomappsample.model.Post;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hamza";
    EditText editTextTitle;
    EditText editTextBody;
     RecyclerView recyclerView;
    PostsAdapter adapter;
    MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.ed_title);
        editTextBody = findViewById(R.id.ed_body);

        initViewModel();
        initRecycler();
        observeData();
        observeResult();
        addProduct();
        getProducts();

    }

    private void initViewModel() {
        mainViewModel =new ViewModelProvider(this).get(MainViewModel.class);
    }

    private void observeData() {
        mainViewModel.observePosts(getApplicationContext());

    }
    private void observeResult() {
        mainViewModel.getResultInsertPosts().observe(MainActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        }); }
    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler);
        adapter = new PostsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void addProduct() {
        Button btnAdd = findViewById(R.id.button2);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mainViewModel.inserPost(getApplicationContext(),
                    new Post(editTextTitle.getText().toString()
                            ,editTextBody.getText().toString(),
                            1));

            }
        });
    }
    private void getProducts() {
       Button btnGet = findViewById(R.id.button_get);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.getPosts().observe(MainActivity.this, new Observer<List<Post>>() {
                    @Override
                    public void onChanged(List<Post> posts) {
                                adapter.setList(posts);
                    }
                });
            }
        });

        }

}