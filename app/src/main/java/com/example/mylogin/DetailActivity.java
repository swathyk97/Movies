package com.example.mylogin;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.bumptech.glide.Glide;


public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAIL = "movie_detail";
    public static final String IMAGE_URL = "image_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getActionBar();

        final Detail detail = getIntent().getParcelableExtra(MOVIE_DETAIL);
        final String imageUrl =  getIntent().getStringExtra(IMAGE_URL);
        Glide.with(this).load(imageUrl).into( (ImageView) findViewById(R.id.main_backdrop));


        ((TextView) findViewById(R.id.grid_title)).setText(detail.title);
        ((TextView) findViewById(R.id.grid_writers)).setText(detail.Writer);
        ((TextView) findViewById(R.id.grid_actors)).setText(detail.Actors);
        ((TextView) findViewById(R.id.grid_director)).setText(detail.Director);
        ((TextView) findViewById(R.id.grid_genre)).setText(detail.Genre);
        ((TextView) findViewById(R.id.grid_released)).setText(detail.Released);
        ((TextView) findViewById(R.id.grid_plot)).setText(detail.Plot);
        ((TextView) findViewById(R.id.grid_runtime)).setText(detail.Runtime);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
