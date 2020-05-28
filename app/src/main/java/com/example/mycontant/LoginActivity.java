package com.example.mycontant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mycontant.model.PrefUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText account, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.account);
        pwd = findViewById(R.id.pwd);

    }

    public void login(View view) {
        //比对缓存账号是否正确
        if (account.getText().toString().equals(PrefUtils.getString(this, "account", ""))
                && pwd.getText().toString().equals(PrefUtils.getString(this, "pwd", ""))) {
            startActivity(new Intent(LoginActivity.this, Listactivity.class));
            finish();
        }
    }

    public void regist(View view) {
        startActivity(new Intent(LoginActivity.this, RegistActivity.class));
    }
}
