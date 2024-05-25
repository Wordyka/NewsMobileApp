package com.example.mandirinews;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

    // 3202f7abcee84c0a9ff08eeb07ab7977

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.rvNews);
        categoryRV = findViewById(R.id.rvCategories);
        loadingPB = findViewById(R.id.pbLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        getCategories();
        getNews("Science");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("Top Headlines","https://images.unsplash.com/photo-1552764217-6d34d9795ab9?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHdvcmxkfGVufDB8fDB8fHww"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8VGVjaG5vbG9neXxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1628595351029-c2bf17511435?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTV8fHNjaWVuY2V8ZW58MHx8MHx8fDA%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8c3BvcnRzfGVufDB8fDB8fHww"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://media.istockphoto.com/id/524426975/photo/new-york-state-capitol-building-albany.webp?b=1&s=170667a&w=0&k=20&c=0tFCLRnNE5FuNgIsX37f5OkRhe57iVkXh0mmogYG5wA="));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://media.istockphoto.com/id/1474838897/photo/business-partner-handshake-after-the-meeting-financial-and-investment-cooperation.webp?b=1&s=170667a&w=0&k=20&c=UfrKkB2oU0j0VwYPcmRJIwwg32LYMZcezMQdYJf4j0E="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://images.unsplash.com/photo-1499364615650-ec38552f4f34?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://plus.unsplash.com/premium_photo-1672760403439-bf51a26c1ae6?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OXx8aGVhbHRoY2FyZXxlbnwwfHwwfHx8MA%3D%3D"));
        categoryRVAdapter.notifyDataSetChanged();

    }

    private void getNews(String category) {
        Log.d("dika1", "getNews method called with category: " + category);

        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines/?country=us&category="+category+"&apiKey=3202f7abcee84c0a9ff08eeb07ab7977";
        String url = "https://newsapi.org/v2/top-headlines/?country=us&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=3202f7abcee84c0a9ff08eeb07ab7977";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("Top Headlines")) {
            call = retrofitAPI.getAllNews(url);
        } else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                Log.d("dika1", "API call successful, response code: " + response.code());

                if (response.body() != null) {
                    NewsModal newsModal = response.body();
                    ArrayList<Articles> articles = newsModal.getArticles();
                    Log.d("dika1", "Number of articles received: " + articles.size());

                    for (Articles article : articles) {
                        articlesArrayList.add(new Articles(
                                article.getTitle(),
                                article.getDescription(),
                                article.getUrlToImage(),
                                article.getUrl(),
                                article.getContent()));
                    }

                    newsRVAdapter.notifyDataSetChanged();
                } else {
                    Log.d("dika1", "Response body is null");
                }

                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }
}