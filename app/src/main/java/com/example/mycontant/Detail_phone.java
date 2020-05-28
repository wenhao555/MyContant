package com.example.mycontant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontant.model.DBAdapter;
import com.example.mycontant.model.Phone_table;

/**
 * 传递界面
 */
public class Detail_phone extends AppCompatActivity {
    DBAdapter db;
    //private Send_dail dail_send=new Send_dail();
    private Button motifactate, mf_dail, mf_sendmassage, mf_delete;
    private TextView ph_name, ph_number, ph_email, ph_company, ph_address;
    private Phone_table ph_detail = null;
    private TextView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_phone);
        db = new DBAdapter(this);

        ph_name = (TextView) findViewById(R.id.detail_name);
        ph_number = (TextView) findViewById(R.id.detail_phonenumber);
        ph_email = (TextView) findViewById(R.id.detail_email);
        ph_company = (TextView) findViewById(R.id.detail_company);
        ph_address = (TextView) findViewById(R.id.detail_address);
        add = (TextView) findViewById(R.id.title_modifaction);
        motifactate = (Button) findViewById(R.id.detail_modificate);
        mf_dail = (Button) findViewById(R.id.detail_dail);
        mf_delete = (Button) findViewById(R.id.detail_delete);
        mf_sendmassage = (Button) findViewById(R.id.detail_sendmassage);


        try {
            Intent masa = getIntent();
            Bundle infod = masa.getExtras();
            ph_detail = (Phone_table) infod.getSerializable("pho");
            ph_name.setText(ph_detail.name);
            ph_number.setText(ph_detail.phonenumber);
            ph_email.setText(ph_detail.Email);
            ph_company.setText(ph_detail.company);
            ph_address.setText(ph_detail.address);
        } catch (Exception e) {
            // TODO: handle exception
        }

        Button[] btn = new Button[]{motifactate, mf_dail, mf_sendmassage, mf_delete};
        for (Button bt : btn) {
            bt.setOnClickListener(detaillistener);
        }

    }

    View.OnClickListener detaillistener = new View.OnClickListener() {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.detail_modificate:
//                        add.setText("修改个人名片");
                    Intent intent = new Intent(Detail_phone.this, New_phone.class);
                    Bundle mbun = new Bundle();
                    mbun.putSerializable("pho", ph_detail);
                    intent.putExtras(mbun);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.detail_dail:
                    try {

                        dail(ph_detail.phonenumber);
                        finish();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    break;
                case R.id.detail_sendmassage:
                    try {
                        Log.e("aa", "ddds");

                        sendmassage(ph_detail.phonenumber);
                        finish();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                    break;
                case R.id.detail_delete:
                    try {
                        db.delete(ph_detail.name);
                        Intent intent2 = new Intent(Detail_phone.this, Listactivity.class);
                        startActivity(intent2);
                        finish();
                    } catch (Exception e) {
                        // TODO: handle exception
                        Toast.makeText(Detail_phone.this, "删除不了该号码，请重试...", Toast.LENGTH_LONG).show();

                    }
                    break;

                default:
                    break;
            }
        }
    };

    public void sendmassage(String phonetext) {
        Uri phonefaxinxi = Uri.parse("smsto:" + phonetext);
        Intent in = new Intent(Intent.ACTION_SENDTO, phonefaxinxi);
        startActivity(in);
    }

    public void dail(String phonetext) {
        Uri phonebohao = Uri.parse("tel:" + phonetext);
        Intent intent = new Intent(Intent.ACTION_CALL, phonebohao);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            startActivity(intent);
            return;
        }
        startActivity(intent);
    }
}
