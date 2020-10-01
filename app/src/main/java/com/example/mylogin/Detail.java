package com.example.mylogin;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Detail implements Parcelable {
   @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String Year;
    @SerializedName("Rated")
    public String Rated;
    @SerializedName("Released")
    public String Released;
    @SerializedName("Runtime")
    public String Runtime;
    @SerializedName("Genre")
    public String Genre;
    @SerializedName("Director")
    public String Director;
    @SerializedName("Writer")
    public String Writer;
    @SerializedName("Actors")
    public String Actors;
   @SerializedName("Plot")
    public String Plot;
    @SerializedName("Language")
    public String Language;
    @SerializedName("Country")
    public String Country;
    @SerializedName("Awards")
    public String Awards;
    @SerializedName("Poster")
    public String Poster;
    @SerializedName("Metascore")
    public String Metascore;
    @SerializedName("imdbRating")
    public String imdbRating;
    @SerializedName("imdbVotes")
    public String imdbVotes;
    @SerializedName("imdbID")
    public String imdbID;
    @SerializedName("Type")
    public String Type;
    @SerializedName("Response")
    public String Response;

 /*   @Override
    public String toString() {
        return "Detail{" +
                "title='" + title + '\'' +
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
    }*/



    protected Detail(Parcel in) {
        title = in.readString();
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
        dest.writeString(title);
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
    public static final Parcelable.Creator<Detail> CREATOR = new Parcelable.Creator<Detail>() {
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
