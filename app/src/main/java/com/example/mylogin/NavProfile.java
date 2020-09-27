package com.example.mylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class NavProfile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView name;
    ImageView imageView;
    Uri imageUri2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProfileImage image =new ProfileImage();
        imageUri2=image.imageUri;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav);
        name=findViewById(R.id.name);
        imageView= findViewById(R.id.imageView2);
        Glide.with(this)
                .load(sharedPreferences.getString("imageprefernce",null))
                .centerCrop()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);


    }


}
