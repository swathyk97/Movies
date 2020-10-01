package com.example.mylogin;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    public String Title;
    @SerializedName("Year")
    public String Year;
    @SerializedName("imdbID")
    public String imdbID;
    @SerializedName("Type")
    public String Type;
    @SerializedName("Poster")
    public String Poster;

   /* @Override
    public String toString() {
        return "\nMovie{" +
                "title='" + title + '\'' +
                ", Year='" + Year + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", Type='" + Type + '\'' +
                ", Poster='" + Poster + '\'' +
                '}';
    }  */

}
