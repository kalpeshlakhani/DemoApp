package com.demoapp.view.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demoapp.R;

import com.demoapp.utils.SharedPrefManager;
import com.demoapp.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @BindView(R.id.btnSignIn)
    Button btnSignIn;

    private String strPassword;
    private String strEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialization();
    }

    private void initialization() {
        ButterKnife.bind(this);

        if (SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        btnSignIn.setPaintFlags(btnSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (text != null && text.length() > 0) {
                    strEmail = text.toString();

                    String strEmail = etEmail.getText().toString();
                    String strPassword = etPassword.getText().toString();
                    validateData(strEmail,strPassword,true);
                } else {
                    strEmail = "";
                    btnSignIn.setEnabled(false);
                    btnSignIn.setBackground(getResources().getDrawable(R.drawable.border_normal));
                    btnSignIn.setTextColor(getResources().getColor(R.color.Grey_5));
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (text != null && text.length() > 0) {
                    strPassword = text.toString();

                    String strEmail = etEmail.getText().toString();
                    String strPassword = etPassword.getText().toString();

                    validateData(strEmail,strPassword,false);

                } else {
                    strPassword = "";
                    btnSignIn.setEnabled(false);
                    btnSignIn.setBackground(getResources().getDrawable(R.drawable.border_normal));
                    btnSignIn.setTextColor(getResources().getColor(R.color.Grey_5));
                }
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();

                if (strEmail == null || !Utils.isValidEmail(strEmail)) {
                    etEmail.setError("Please enter valid email");
                    etEmail.requestFocus();
                } else if (strPassword == null || strPassword.length() < 8 || strPassword.length() > 15) {
                    etPassword.setError("Please enter minimum 8 characters and maximum 15 characters password");
                    etPassword.requestFocus();
                } else {
                    SharedPrefManager.getInstance(LoginActivity.this).setBooleanPreference(SharedPrefManager.KEY_IS_LOGIN, true);
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    private void validateData(String strEmail, String strPassword,boolean isFromEmail) {
        if (strEmail == null || !Utils.isValidEmail(strEmail)) {
            if (isFromEmail){
                etEmail.requestFocus();
            }
            btnSignIn.setEnabled(false);
            btnSignIn.setBackground(getResources().getDrawable(R.drawable.border_normal));
            btnSignIn.setTextColor(getResources().getColor(R.color.Grey_5));
        } else if (strPassword == null || strPassword.length() < 8 || strPassword.length() > 15) {
            if (!isFromEmail){
                etPassword.requestFocus();
            }
            btnSignIn.setEnabled(false);
            btnSignIn.setBackground(getResources().getDrawable(R.drawable.border_normal));
            btnSignIn.setTextColor(getResources().getColor(R.color.Grey_5));
        }else{
            btnSignIn.setEnabled(true);
            btnSignIn.setBackground(getResources().getDrawable(R.drawable.border_pressed));
            btnSignIn.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
