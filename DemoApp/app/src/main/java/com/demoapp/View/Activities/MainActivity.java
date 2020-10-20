package com.demoapp.View.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.APIUtils.NetworkState;
import com.demoapp.R;
import com.demoapp.Manager.ConnectionManager;
import com.demoapp.Model.MovieDetails;
import com.demoapp.Utils.SharedPrefManager;
import com.demoapp.Utils.SpacingItemDecoration;
import com.demoapp.Utils.Utils;
import com.demoapp.View.Adapters.MoviesDataAdapter;
import com.demoapp.ViewModel.MoviesDataViewModel;
import com.wc.widget.dialog.IosDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    MoviesDataAdapter movieDataAdapter;
    private MoviesDataViewModel mMoviesViewModel;

    @BindView(R.id.rvMovies)
    RecyclerView rvMovie;

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
                Utils.hideProgressDialog();
                initializeMovieData();
            }
        }, 2000);
    }

    private void setHeader() {
        View headerView = findViewById(R.id.header);
        ImageView ivBack = headerView.findViewById(R.id.ivBack);
        ImageView ivLogout = headerView.findViewById(R.id.ivLogout);
        ivBack.setVisibility(View.INVISIBLE);
        ivLogout.setVisibility(View.VISIBLE);
        TextView tvPageName = headerView.findViewById(R.id.tvPageName);
        tvPageName.setText("Movie List");
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
                movieDataAdapter.submitList(movies);
            }
        });

        mMoviesViewModel.getNetworkStateLiveData().observe(MainActivity.this, new Observer<NetworkState>() {
            @Override
            public void onChanged(@Nullable NetworkState networkState) {
                Log.d(TAG, "onChanged: network state changed");
                movieDataAdapter.setNetworkState(networkState);
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
