package id.prodigy.rnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.prodigy.rnews.api.ApiClient;
import id.prodigy.rnews.api.ApiInterface;
import id.prodigy.rnews.filters.Category;
import id.prodigy.rnews.filters.Country;
import id.prodigy.rnews.models.Article;
import id.prodigy.rnews.models.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterDialog.FilterDialogListener {
    final int SEARCHED = 1;
    final int FILTERED = 2;
    final int COUNTRY_FILTERED = 3;
    final int CATEGORY_FILTERED = 4;


    public static final String API_KEY = "83778633a5754f7ea0030aaa7cd12f7e";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    private ImageView status;
    String keyword;
    Context mContext;
    private EditText searchText;
    private ImageButton searchButton, filterButton;
    private LinearLayout bookmarks, themeColor, setting;
    private ImageButton closeMenuButton;
    private Dialog dialog;

    private Map<String, String> filter = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setComponentsID();

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson(0);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = searchText.getText().toString();
                LoadJson(SEARCHED);
            }
        });
        
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterDialog();
            }
        });
    }

    private void setComponentsID() {
        searchText = findViewById(R.id.et_search_text);
        searchButton = findViewById(R.id.ib_search_button);
        filterButton = findViewById(R.id.ib_filter_button);
        recyclerView = findViewById(R.id.recyclerview_news);
        status = findViewById(R.id.status);
        closeMenuButton = findViewById(R.id.ib_close_menu);
    }


    public void LoadJson(final int mode) {
        status.setImageResource(R.drawable.ic_loading);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String country = "id";
        String language = Utils.getLanguage();
        Call<News> call;

        switch (mode) {
            case SEARCHED:
                call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY);
                break;
            case COUNTRY_FILTERED:
                call = apiInterface.getNewsCountryFiltered(country, "publishedAt", API_KEY);
                break;
            case CATEGORY_FILTERED:
                call = apiInterface.getNewsCategoryFiltered("business", "publishedAt", API_KEY);
                break;
            case FILTERED:
                call = apiInterface.filtered(filter, API_KEY);
                break;
            default:
                call = apiInterface.getNews(country, API_KEY);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    status.setImageResource(0);
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticle();
                    newsAdapter = new NewsAdapter(articles, MainActivity.this);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    initListener();

                } else {
                    status.setImageResource(R.drawable.ic_error);
                    Toast.makeText(MainActivity.this, "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                status.setImageResource(R.drawable.ic_error);

            }
        });
    }


    // News Detail Clicked
    private void initListener() {
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, TestActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img", article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("publisher", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());
                intent.putExtra("content", article.getContent());

                startActivity(intent);
            }
        });
    }


    // Filter
    private void openFilterDialog() {
        FilterDialog filterDialog = new FilterDialog();
        filterDialog.show(getSupportFragmentManager(), "filter dialog");
    }

    @Override
    public void applyFilter(Map<String, String> filter) {
        this.filter = filter;
        LoadJson(FILTERED);
    }


    // Menu
    public void onClickMenuButton(View view) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.menu_dialog);
        dialog.show();
    }

    public void onClickCloseMenuButton(View view) {
        dialog.dismiss();
    }
    // Akhir Menu
}
