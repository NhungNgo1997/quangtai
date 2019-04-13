package com.example.pnam.doctruyen.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pnam.doctruyen.Adapter.CustomListTruyen;
import com.example.pnam.doctruyen.DB.DBManager;
import com.example.pnam.doctruyen.Object.Truyen;
import com.example.pnam.doctruyen.R;

import java.util.ArrayList;

public class TruyenDaLuuActivity extends AppCompatActivity {

    DrawerLayout drawerRruyendaluu;
    Toolbar toolbarTruyendaluu;
    ListView lvTtruyendaluu;
    NavigationView navTruyendaluu;

    ArrayList<Truyen> truyens;

    CustomListTruyen customListTruyen;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen_da_luu);

        anhXa();
        action();

    }

    private void action() {
        toolbarTruyendaluu.setTitle("Truyện đã lưu");
        setSupportActionBar(toolbarTruyendaluu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerRruyendaluu, toolbarTruyendaluu, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerRruyendaluu.addDrawerListener(toggle);
        toggle.syncState();

        dbManager = new DBManager(TruyenDaLuuActivity.this);
        truyens = new ArrayList<>();
        truyens.addAll(dbManager.getAllData());
        for (int i = 0; i < truyens.size(); i++) {
            Log.d("nammmmmmm", "Truyen: " + truyens.get(i).toString() + "\n");
        }

        customListTruyen = new CustomListTruyen(TruyenDaLuuActivity.this, R.layout.item_truyen, truyens);
        lvTtruyendaluu.setAdapter(customListTruyen);

        lvTtruyendaluu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TruyenDaLuuActivity.this, NoiDungTruyenDaLuuActivity.class);
                intent.putExtra("linktruyen", truyens.get(position).getLinkTruyen());
                startActivity(intent);
            }
        });

        lvTtruyendaluu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

//                Toast.makeText(DocTruyenActivity.this, truyens.get(position).toString(), Toast.LENGTH_SHORT).show();
                dbManager = new DBManager(TruyenDaLuuActivity.this);

                final int i = position;
                final AlertDialog.Builder alert = new AlertDialog.Builder(TruyenDaLuuActivity.this);
                alert.setTitle("Xóa!!!");
                alert.setMessage("Xóa truyện đã lưu!!!");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Truyen truyen = new Truyen(truyens.get(position).getTitleTruyen(), truyens.get(position).getDescriptionTruyen()
                                , truyens.get(position).getLinkTruyen(), truyens.get(position).getUrlImgTruyen());
                        dbManager.deleteTruyen(truyens.get(i));
                        truyens.clear();
                        truyens.addAll(dbManager.getAllData());
//                        customListTruyen = new CustomListTruyen(TruyenDaLuuActivity.this,R.layout.item_truyen,truyens);
//                        lvTtruyendaluu.setAdapter(customListTruyen);
                        customListTruyen.notifyDataSetChanged();

                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();

                return false;
            }
        });

        navTruyendaluu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_trangchu) {
                    Intent intent1 = new Intent(TruyenDaLuuActivity.this, DocTruyenActivity.class);
                    startActivity(intent1);

                } else if (id == R.id.nav_tindaluu) {

                    Intent intent = new Intent(TruyenDaLuuActivity.this, TruyenDaLuuActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_truyen_thieu_nhi) {
                    Intent intent = new Intent(TruyenDaLuuActivity.this, TruyenThieuNhiActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_truyen_co_tich_viet_nam) {
                    Intent intent = new Intent(TruyenDaLuuActivity.this, CoTichVietNamActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_share)

                {

                } else if (id == R.id.nav_send)

                {

                }
                drawerRruyendaluu.closeDrawers();
                return false;
            }
        });

    }

    private void anhXa() {
        drawerRruyendaluu = findViewById(R.id.drawer_truyendaluu);
        toolbarTruyendaluu = findViewById(R.id.toolbar_truyendaluu);
        lvTtruyendaluu = findViewById(R.id.lv_truyendaluu);
        navTruyendaluu = findViewById(R.id.nav_truyendaluu);
    }
}
