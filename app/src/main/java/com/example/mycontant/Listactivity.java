package com.example.mycontant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycontant.model.BaseRecyclerAdapter;
import com.example.mycontant.model.DBAdapter;
import com.example.mycontant.model.OnItemClickListener;
import com.example.mycontant.model.OnItemLongClickListener;
import com.example.mycontant.model.Phone_table;

import java.util.ArrayList;
import java.util.List;

public class Listactivity extends AppCompatActivity {
    private DBAdapter db;
    private RecyclerView recyvle;
    private List<Phone_table> infos = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);

        db = new DBAdapter(this);
        infos = db.findAll();
        recyvle = findViewById(R.id.recyvle);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                infos = db.findAll();
                initData();
                recyvle.setAdapter(adapter);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        initData();
        recyvle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyvle.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {//点击跳转界面
            @Override
            public void onItemClick(@NonNull int position) {
                Phone_table info = infos.get(position);
                Intent intent = new Intent(Listactivity.this, Detail_phone.class);
                Bundle mbun = new Bundle();
                mbun.putSerializable("pho", info);
                intent.putExtras(mbun);
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {//长按删除
            @Override
            public void onLongItemClick(@NonNull int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Listactivity.this);
                builder.setTitle("提示")
                        .setMessage("是否删除信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Phone_table info = infos.get(position);

                                db.delete(info.getName());
                                Toast.makeText(Listactivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                infos = db.findAll();
                                initData();
                                recyvle.setAdapter(adapter);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private BaseRecyclerAdapter adapter;

    private void initData() {
        adapter = new BaseRecyclerAdapter() {
            @Override
            protected void onBindView(@NonNull BaseRecyclerAdapter.BaseViewHolder holder, @NonNull final int position) {
                final Phone_table info = infos.get(position);
                TextView item_title = holder.getView(R.id.item_title);
                TextView item_context = holder.getView(R.id.item_context);
                item_title.setText(info.getName());
                item_context.setText(info.getPhonenumber());
            }

            @Override
            protected int getLayoutResId(int position) {
                return R.layout.iten_adapter;
            }

            @Override
            public int getItemCount() {
                return infos.size();
            }
        };
    }

    /*
     * 菜单栏
     */
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    ;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_quit:
                finish();
                break;
            case R.id.menu_all_delete:
                new AlertDialog.Builder(Listactivity.this).setTitle("全删").setMessage(
                        "你确定全部删除吗...").setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                db.deleteAllData();
                                infos = db.findAll();
                                initData();
                                recyvle.setAdapter(adapter);
                            }
                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();

                    }
                })
                        .show();


                break;
            case R.id.new_phone:
                Intent intent = new Intent(Listactivity.this, New_phone.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
