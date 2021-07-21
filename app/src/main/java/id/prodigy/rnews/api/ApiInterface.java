package id.prodigy.rnews.api;

import java.util.Map;

import id.prodigy.rnews.models.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> getNewsSearch(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );

    @GET("top-headlines")
    Call<News> getNewsFiltered(
            @Query("category") String category,
            @Query("country") String country,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getNewsCategoryFiltered(
            @Query("category") String category,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> getNewsCountryFiltered(
            @Query("country") String country,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Call<News> filtered(
            @QueryMap Map<String,String> param,
            @Query("apiKey") String apiKey
    );

}
