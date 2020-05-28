package com.example.mycontant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontant.model.PrefUtils;

public class RegistActivity extends AppCompatActivity {
    private EditText account, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        account = findViewById(R.id.account);//获取控件
        pwd = findViewById(R.id.pwd);

    }

    public void regist(View view) {
        String us = account.getText().toString();
        String pd = pwd.getText().toString();//获取内容
        if (!us.equals("") && !pd.equals("")) {
            PrefUtils.setString(this, "account", us);
            PrefUtils.setString(this, "pwd", pd);//加入进缓存
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "请输入账号密码", Toast.LENGTH_SHORT).show();
        }
    }
}
