package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class OmdbActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<searchService.ResultWithDetail> {


    public SearchActivity search;
    private RecyclerView mMovieListRecyclerView;
    private MovieRecyclerViewAdapter mMovieAdapter;
    private String mMovieTitle;
    private ProgressBar mProgressBar;
    public String moviename;
    Bundle bb;


    private static final int LOADER_ID = 1;

    private static final String LOG_TAG = "OmdbActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_omdb);

        bb = getIntent().getExtras();
        assert bb != null;
        moviename = bb.getString("movie");
        mMovieListRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_spinner);

        startSearch();

        mMovieAdapter = new MovieRecyclerViewAdapter(null);
        mMovieListRecyclerView.setAdapter(mMovieAdapter);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mMovieListRecyclerView.setItemAnimator(null);
        mMovieListRecyclerView.setLayoutManager(gridLayoutManager);
        LoaderManager.enableDebugLogging(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mMovieTitle", mMovieTitle);
        outState.putInt("progress_visibility", mProgressBar.getVisibility());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int progress_visibility = savedInstanceState.getInt("progress_visibility");
        if (progress_visibility == View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        mMovieTitle = savedInstanceState.getString("mMovieTitle");
        if (mMovieTitle != null) {
            Bundle args = new Bundle();
            args.putString("movieTitle", mMovieTitle);
            getSupportLoaderManager().initLoader(LOADER_ID, args, this);
        }
    }

    @Override
    public Loader<searchService.ResultWithDetail> onCreateLoader(int id, Bundle args) {
        return new RetrofitLoader(OmdbActivity.this, args.getString("movieTitle"));
    }

    @Override
    public void onLoadFinished(Loader<searchService.ResultWithDetail> loader, searchService.ResultWithDetail resultWithDetail) {
        mProgressBar.setVisibility(View.GONE);
        mMovieListRecyclerView.setVisibility(View.VISIBLE);
        if (resultWithDetail.getResponse().equals("True")) {
            mMovieAdapter.swapData(resultWithDetail.getMovieDetailList());
        } else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.snackbar_title_not_found), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<searchService.ResultWithDetail> loader) {
        mMovieAdapter.swapData(null);
    }

    public class MovieRecyclerViewAdapter
            extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

        private List<Detail> mValues;

        public MovieRecyclerViewAdapter(List<Detail> items) {
            mValues = items;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_movie, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final Detail detail = mValues.get(position);
            final String title = detail.title;
            final String imdbId = detail.imdbID;
            final String director = detail.Director;
            final String year = detail.Year;
            holder.mDirectorView.setText(director);
            holder.mTitleView.setText(title);
            holder.mYearView.setText(year);

            final String imageUrl;
            if (!detail.Poster.equals("N/A")) {
                imageUrl = detail.Poster;
            } else {
                // default image if there is no poster available
                imageUrl = getResources().getString(R.string.default_poster);
            }
            holder.mThumbImageView.layout(0, 0, 0, 0); // invalidate the width so that glide wont use that dimension
            Glide.with(OmdbActivity.this).load(imageUrl).into(holder.mThumbImageView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OmdbActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.MOVIE_DETAIL, detail);
                    intent.putExtra(DetailActivity.IMAGE_URL, imageUrl);

                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(OmdbActivity.this,
                                    holder.mThumbImageView, "poster");
                    startActivity(intent, options.toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mValues == null) {
                return 0;
            }
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mTitleView;
            public final TextView mYearView;
            public final TextView mDirectorView;
            public final ImageView mThumbImageView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTitleView = (TextView) view.findViewById(R.id.movie_title);
                mYearView = (TextView) view.findViewById(R.id.movie_year);
                mThumbImageView = (ImageView) view.findViewById(R.id.thumbnail);
                mDirectorView = (TextView) view.findViewById(R.id.movie_director);
            }

        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);

        }

        public void swapData(List<Detail> items) {
            if (items != null) {
                mValues = items;
                notifyDataSetChanged();

            } else {
                mValues = null;
            }
        }
    }

    public void startSearch() {
        if (CommonUtils.isNetworkAvailable(getApplicationContext())) {

            String movieTitle = moviename.trim();
            if (!movieTitle.isEmpty()) {
                Bundle args = new Bundle();
                args.putString("movieTitle", movieTitle);
                getSupportLoaderManager().restartLoader(LOADER_ID, args, this);
                mMovieTitle = movieTitle;
                mProgressBar = (ProgressBar) findViewById(R.id.progress_spinner);
                mProgressBar.setVisibility(View.VISIBLE);
                mMovieListRecyclerView.setVisibility(View.GONE);
            } else
                Snackbar.make(mMovieListRecyclerView,
                        getResources().getString(R.string.snackbar_title_empty),
                        Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(mMovieListRecyclerView,
                    getResources().getString(R.string.network_not_available),
                    Snackbar.LENGTH_LONG).show();
        }
    }


}
