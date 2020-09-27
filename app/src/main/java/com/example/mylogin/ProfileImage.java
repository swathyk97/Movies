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

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

public class ProfileImage extends Activity {

   CircularImageView imageView;
   private static final int PICK_IMAGE=1;



    Uri imageUri;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView name, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = findViewById(R.id.imageView);
        name=findViewById(R.id.profilename);
        email=findViewById(R.id.email);
        sharedPreferences=this.getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        name.setText(sharedPreferences.getString("name",null));
        email.setText(sharedPreferences.getString("email",null));





    }

    public void imageClick(View view){

        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE);

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
                saveCacheFile(imageUri,bitmap);
                getCacheFile(imageUri);
             ;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    private static ProfileImage INSTANCE = null;
    private HashMap<Uri, String> cacheMap;
    private HashMap<Uri, Bitmap> bitmapMap;
    private static final String cacheDir = "/Android/data/com.example.mylogin/cache/";
    private static final String CACHE_FILENAME = ".cache";

    @SuppressWarnings("unchecked")
    public ProfileImage() {
        cacheMap = new HashMap<Uri, String>();
        bitmapMap = new HashMap<Uri, Bitmap>();
        File fullCacheDir = new File(Environment.getExternalStorageDirectory().toString(),cacheDir);

    }



    private synchronized static void createInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ProfileImage();
        }
    }

    public static ProfileImage getInstance() {
        if(INSTANCE == null) createInstance();
        return INSTANCE;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveCacheFile(Uri cacheUri, Bitmap image) {
        File fullCacheDir = new File(Environment.getExternalStorageDirectory().toString(),cacheDir);
        String fileLocalName = new SimpleDateFormat("ddMMyyhhmmssSSS").format(new java.util.Date())+".PNG";
        File fileUri = new File(fullCacheDir.toString(), fileLocalName);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(fileUri);
            image.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            cacheMap.put(cacheUri, fileLocalName);
            Log.i("CACHE", "Saved file "+cacheUri+" (which is now "+fileUri.toString()+") correctly");
            bitmapMap.put(cacheUri, image);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(new File(fullCacheDir.toString(), CACHE_FILENAME))));
            os.writeObject(cacheMap);
            os.close();
        } catch (FileNotFoundException e) {
            Log.i("CACHE", "Error: File "+cacheUri+" was not found!");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("CACHE", "Error: File could not be stuffed!");
            e.printStackTrace();
        }
    }

    public Bitmap getCacheFile(Uri cacheUri) {
        if(bitmapMap.containsKey(cacheUri)) return (Bitmap)bitmapMap.get(cacheUri);

        if(!cacheMap.containsKey(cacheUri)) return null;
        String fileLocalName = cacheMap.get(cacheUri).toString();
        File fullCacheDir = new File(Environment.getExternalStorageDirectory().toString(),cacheDir);
        File fileUri = new File(fullCacheDir.toString(), fileLocalName);
        if(!fileUri.exists()) return null;

        Log.i("CACHE", "File "+cacheUri+" has been found in the Cache");
        Bitmap bm = BitmapFactory.decodeFile(fileUri.toString());
        bitmapMap.put(cacheUri, bm);
        return bm;
    }
}
