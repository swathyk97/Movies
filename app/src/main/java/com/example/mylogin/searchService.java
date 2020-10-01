package com.example.mylogin;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class searchService implements Serializable {
    private static final String API_URL = " http://www.omdbapi.com";
    private static Omdbapi sOmdbApi;

    public static class ResultWithDetail {
        private List<Detail> movieDetailList;
        private String totalResults;
        private String Response;

        public ResultWithDetail(Result result) {
            this.totalResults = result.totalResults;
            this.Response = result.Response;
            movieDetailList = new ArrayList<>();
            Log.d("Log", "response" + Response);

        }

        public void addToList(Detail detail) {
            movieDetailList.add(detail);
        }

        public List<Detail> getMovieDetailList() {
            Log.d("Log", "Movie list" + movieDetailList);
            return movieDetailList;
        }

        public String getTotalResults() {
            return totalResults;
        }

        public String getResponse() {
            return Response;
        }


    }



    public interface Omdbapi {
        @GET("?type=movie")
        retrofit2.Call<Result> Result(
                @Query("s") String Title);

        @GET("?plot=full")
        retrofit2.Call<Detail> Detail(
                @Query("i") String ImdbId);
    }

    private static void setsOmdbApi() {
        if (sOmdbApi == null) {

            OkHttpClient.Builder httpClient =
                    new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    okhttp3.Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();


                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", "96d4a5fa")
                            .build();
                    Log.d("Log", "detail" + originalHttpUrl);

                    okhttp3.Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            sOmdbApi = retrofit.create(Omdbapi.class);
        }
    }

    public static Result performSearch(String title) throws IOException {
        setsOmdbApi();


        retrofit2.Call<Result> call = sOmdbApi.Result(title);
        Log.d("Log", "call" + call);


        return call.execute().body();
    }

    public static Detail getDetail(String imdbId) throws IOException {
        setsOmdbApi();

        Call<Detail> call = sOmdbApi.Detail(imdbId);
        Log.d("Log", "call" + call);

        return call.execute().body();
    }
}
