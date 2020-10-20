package com.demoapp.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.R;
import com.demoapp.manager.ConnectionManager;
import com.demoapp.model.MovieDetails;
import com.demoapp.utils.SharedPrefManager;
import com.demoapp.utils.SpacingItemDecoration;
import com.demoapp.utils.Utils;
import com.demoapp.view.adapters.MoviesDataAdapter;
import com.demoapp.viewModel.MoviesDataViewModel;
import com.wc.widget.dialog.IosDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    MoviesDataAdapter movieDataAdapter;
    private MoviesDataViewModel mMoviesViewModel;

    @BindView(R.id.rvMovies)
    RecyclerView rvMovie;

    @BindView(R.id.ivLogout)
    ImageView ivLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setHeader();

        initializeMovieData();
        Utils.getProgressDialog(MainActivity.this, "Please Wait...");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                initializeMovieData();
            }
        }, 1000);
    }

    private void setHeader() {
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogOutDialog("Are you sure want to logout ?");
            }
        });
    }

    public void getLogOutDialog(String strTite) {

        Dialog dialog = new IosDialog.Builder(this)
                .setTitle("Alert!")
                .setTitleColor(Color.RED)
                .setTitleSize(20)
                .setMessage(strTite)
                .setMessageColor(Color.parseColor("#37474F"))
                .setMessageSize(18)
                .setNegativeButtonColor(Color.parseColor("#37474F"))
                .setNegativeButtonSize(18)
                .setNegativeButton("No", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButtonColor(Color.parseColor("#37474F"))
                .setPositiveButtonSize(18)
                .setPositiveButton("Yes", new IosDialog.OnClickListener() {
                    @Override
                    public void onClick(IosDialog dialog, View v) {
                        dialog.dismiss();
                        SharedPrefManager.getInstance(MainActivity.this).logout();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).build();
        dialog.show();
    }

    private void initializeMovieData() {

        if (!ConnectionManager.checkInternetConnection(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        movieDataAdapter = new MoviesDataAdapter(MainActivity.this);
        mMoviesViewModel = ViewModelProviders.of(MainActivity.this).get(MoviesDataViewModel.class);
        mMoviesViewModel.getMoviesDataList().observe(MainActivity.this, new Observer<PagedList<MovieDetails>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieDetails> movies) {
                Log.d(TAG, "onChanged: " + movies.size());
                Utils.hideProgressDialog();
                movieDataAdapter.submitList(movies);
            }
        });

        rvMovie.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        rvMovie.addItemDecoration(new SpacingItemDecoration(2, Utils.dpToPx(MainActivity.this, 3), true));
        rvMovie.setHasFixedSize(true);
        rvMovie.setAdapter(movieDataAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
