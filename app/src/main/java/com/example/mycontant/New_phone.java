package com.example.mycontant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycontant.model.DBAdapter;
import com.example.mycontant.model.Phone_table;

public class New_phone extends AppCompatActivity {
    private EditText name, phone, email, company, address;
    private Button save, fanhui;
    private DBAdapter db;
    long colunm;
    private Phone_table ph_detail = null;
    private boolean updatess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modification);
        name = (EditText) findViewById(R.id.mf_ph_name);
        phone = (EditText) findViewById(R.id.mf_ph_number);
        email = (EditText) findViewById(R.id.mf_ph_email);
        company = (EditText) findViewById(R.id.mf_ph_company);
        address = (EditText) findViewById(R.id.mf_ph_address);

        save = (Button) findViewById(R.id.save_phone);
        fanhui = (Button) findViewById(R.id.fanhui_phone);
        save.setOnClickListener(mf_listener);
        fanhui.setOnClickListener(mf_listener);
        db = new DBAdapter(this);


        try {
            Intent masa = getIntent();


            Bundle infod = masa.getExtras();

            Log.e("detail", "不为空");
            ph_detail = (Phone_table) infod.getSerializable("pho");
            name.setText(ph_detail.name);
            phone.setText(ph_detail.phonenumber);
            email.setText(ph_detail.Email);
            company.setText(ph_detail.company);
            address.setText(ph_detail.address);
            if (ph_detail.phonenumber != null) {
                updatess = true;
                phone.setEnabled(false);
//                name.setEnabled(false);
            }


        } catch (Exception e) {
            // TODO: handle exception
        }


    }

    View.OnClickListener mf_listener = new View.OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.save_phone:
                    try {

                        Phone_table phones = new Phone_table();
                        phones.name = name.getText().toString().trim();
                        phones.phonenumber = phone.getText().toString().trim();
                        phones.address = address.getText().toString().trim();
                        phones.Email = email.getText().toString().trim();
                        phones.company = company.getText().toString().trim();
                        if (updatess) {
                            db.update(ph_detail.name,
                                    phones.name,
                                    phones.phonenumber,
                                    phones.Email, phones.company, phones.address);
                        } else {
                            db.add(phones.name,
                                    phones.phonenumber,
                                    phones.Email,
                                    phones.company, phones.address);
                        }
                        if (colunm == -1) {
                            Toast.makeText(New_phone.this, "添加数据错误...", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(New_phone.this, "保存成功", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(New_phone.this, Listactivity.class);
                            startActivity(intent);
                            finish();
                        }

                        //   Intent intent=new Intent(New_phone.this,Listactivity.class);
                        // startActivity(intent);
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(New_phone.this, "保存失败,请从新保存...", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.fanhui_phone:
                    finish();
                    break;

                default:
                    break;
            }
        }
    };
}
