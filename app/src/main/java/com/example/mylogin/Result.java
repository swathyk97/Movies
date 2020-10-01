package com.example.mylogin;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("Search")
    public List<Movie> Search;
    @SerializedName("totalResults")
    public String totalResults;
    @SerializedName("Response")
    public String Response;

   /* @Override
    public String toString() {
        return "Result{" +
                "Search=" + Search +
                ", totalResults='" + totalResults + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }*/
}
