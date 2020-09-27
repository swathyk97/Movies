package com.example.mylogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikhaellopez.circularimageview.CircularImageView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ProfileImage extends Activity {

   CircularImageView imageView;
   private static final int PICK_IMAGE=1;
   Uri imageUri;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView name, email;
    String sEncodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = findViewById(R.id.imageView);
        name=findViewById(R.id.profilename);
        email=findViewById(R.id.email);
        sharedPreferences=this.getSharedPreferences("login", Context.MODE_PRIVATE);
        name.setText(sharedPreferences.getString("name",null));
        email.setText(sharedPreferences.getString("email",null));
        editor=sharedPreferences.edit();

    }

    public void imageClick(View view){

        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE);
        Glide.with(this)
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE && resultCode == RESULT_OK){
            imageUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
                editor.putString("image", encodeTobase64(bitmap));
                editor.commit();
               
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static String encodeTobase64(Bitmap bitmap) {
        Bitmap immage = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
}
