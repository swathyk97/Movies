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

    public static class Result {
        public List<Movie> Search;
        public String totalResults;
        public String Response;

        @Override
        public String toString() {
            return "Result{" +
                    "Search=" + Search +
                    ", totalResults='" + totalResults + '\'' +
                    ", Response='" + Response + '\'' +
                    '}';
        }

    }

    public static class Movie {
        public String Title;
        public String Year;
        public String imdbID;
        public String Type;
        public String Poster;

        @Override
        public String toString() {
            return "\nMovie{" +
                    "Title='" + Title + '\'' +
                    ", Year='" + Year + '\'' +
                    ", imdbID='" + imdbID + '\'' +
                    ", Type='" + Type + '\'' +
                    ", Poster='" + Poster + '\'' +
                    '}';
        }

    }

    public static class Detail implements Parcelable {
        public String Title;
        public String Year;
        public String Rated;
        public String Released;
        public String Runtime;
        public String Genre;
        public String Director;
        public String Writer;
        public String Actors;
        public String Plot;
        public String Language;
        public String Country;
        public String Awards;
        public String Poster;
        public String Metascore;
        public String imdbRating;
        public String imdbVotes;
        public String imdbID;
        public String Type;
        public String Response;

        @Override
        public String toString() {
            return "Detail{" +
                    "Title='" + Title + '\'' +
                    ", Year='" + Year + '\'' +
                    ", Rated='" + Rated + '\'' +
                    ", Released='" + Released + '\'' +
                    ", Runtime='" + Runtime + '\'' +
                    ", Genre='" + Genre + '\'' +
                    ", Director='" + Director + '\'' +
                    ", Writer='" + Writer + '\'' +
                    ", Actors='" + Actors + '\'' +
                    ", Plot='" + Plot + '\'' +
                    ", Language='" + Language + '\'' +
                    ", Country='" + Country + '\'' +
                    ", Awards='" + Awards + '\'' +
                    ", Poster='" + Poster + '\'' +
                    ", Metascore='" + Metascore + '\'' +
                    ", imdbRating='" + imdbRating + '\'' +
                    ", imdbVotes='" + imdbVotes + '\'' +
                    ", imdbID='" + imdbID + '\'' +
                    ", Type='" + Type + '\'' +
                    ", Response='" + Response + '\'' +
                    '}';
        }

        /* Boilerplate code to make the object parcelable */

        protected Detail(Parcel in) {
            Title = in.readString();
            Year = in.readString();
            Rated = in.readString();
            Released = in.readString();
            Runtime = in.readString();
            Genre = in.readString();
            Director = in.readString();
            Writer = in.readString();
            Actors = in.readString();
            Plot = in.readString();
            Language = in.readString();
            Country = in.readString();
            Awards = in.readString();
            Poster = in.readString();
            Metascore = in.readString();
            imdbRating = in.readString();
            imdbVotes = in.readString();
            imdbID = in.readString();
            Type = in.readString();
            Response = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Title);
            dest.writeString(Year);
            dest.writeString(Rated);
            dest.writeString(Released);
            dest.writeString(Runtime);
            dest.writeString(Genre);
            dest.writeString(Director);
            dest.writeString(Writer);
            dest.writeString(Actors);
            dest.writeString(Plot);
            dest.writeString(Language);
            dest.writeString(Country);
            dest.writeString(Awards);
            dest.writeString(Poster);
            dest.writeString(Metascore);
            dest.writeString(imdbRating);
            dest.writeString(imdbVotes);
            dest.writeString(imdbID);
            dest.writeString(Type);
            dest.writeString(Response);
        }

        @SuppressWarnings("unused")
        public static final Creator<Detail> CREATOR = new Creator<Detail>() {
            @Override
            public Detail createFromParcel(Parcel in) {
                return new Detail(in);
            }

            @Override
            public Detail[] newArray(int size) {
                return new Detail[size];
            }
        };
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

        return call.execute().body();
    }

    public static Detail getDetail(String imdbId) throws IOException {
        setsOmdbApi();

        Call<Detail> call = sOmdbApi.Detail(imdbId);
        Log.d("Log", "call" + call);

        return call.execute().body();
    }
}
