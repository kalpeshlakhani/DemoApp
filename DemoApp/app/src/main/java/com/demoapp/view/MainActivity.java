package com.demoapp.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.R;
import com.demoapp.adapter.MovieDataAdapter;
import com.demoapp.model.MovieDetails;
import com.demoapp.utils.SharedPrefManager;
import com.demoapp.utils.SpacingItemDecoration;
import com.demoapp.utils.Utils;
import com.demoapp.viewmodel.MovieDataViewModel;
import com.wc.widget.dialog.IosDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    MovieDataViewModel movieDataViewModel;
    private ArrayList<MovieDetails> movieDetailsArrayList = new ArrayList<>();
    MovieDataAdapter movieDataAdapter;

    @BindView(R.id.rvMovies)
    RecyclerView rvMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setHeader();
        initialization();
        getMovieData();
    }


    private void setHeader() {
        View headerView = findViewById(R.id.header);
        ImageView ivBack = headerView.findViewById(R.id.ivBack);
        ImageView ivLogout = headerView.findViewById(R.id.ivLogout);
        ivBack.setVisibility(View.GONE);
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
                    }
                }).build();
        dialog.show();
    }

    private void initialization() {
        ButterKnife.bind(this);

        movieDataViewModel = ViewModelProviders.of(this).get(MovieDataViewModel.class);

        rvMovie.setLayoutManager(new GridLayoutManager(this, 2));
        rvMovie.addItemDecoration(new SpacingItemDecoration(2, Utils.dpToPx(this, 3), true));
        rvMovie.setHasFixedSize(true);

        movieDataAdapter = new MovieDataAdapter(MainActivity.this, movieDetailsArrayList);
        rvMovie.setAdapter(movieDataAdapter);


    }

    private void getMovieData() {
        Utils.getProgressDialog(MainActivity.this, "Please Wait...");
        movieDataViewModel.getMovieResponseLiveData().observe(this, movieResponse -> {
            if (movieResponse != null) {
                Utils.hideProgressDialog();
                List<MovieDetails> articles = movieResponse.getMovieDetails();
                movieDetailsArrayList.addAll(articles);
                movieDataAdapter.notifyDataSetChanged();
            }
        });
    }

}
