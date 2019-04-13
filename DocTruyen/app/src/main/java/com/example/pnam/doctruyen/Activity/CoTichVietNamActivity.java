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
import com.example.pnam.doctruyen.Link.LinkRss;
import com.example.pnam.doctruyen.Object.Truyen;
import com.example.pnam.doctruyen.R;
import com.example.pnam.doctruyen.ReadJson.ReadJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CoTichVietNamActivity extends AppCompatActivity {

    DrawerLayout drawerCoTichVietNam;
    Toolbar toolbarCoTichVietNam;
    ListView lvCoTichVietNam;
    NavigationView navCoTichVietNam;

    ArrayList<Truyen> truyens;

    ReadJson readJson;

    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_tich_viet_nam);

        anhXa();
        action();

    }

    private void action() {
        toolbarCoTichVietNam.setTitle("Giải trí");
        setSupportActionBar(toolbarCoTichVietNam);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerCoTichVietNam, toolbarCoTichVietNam, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerCoTichVietNam.addDrawerListener(toggle);
        toggle.syncState();

        truyens = new ArrayList<>();

        String contensJsonTruyenThieuNhi = null;
        readJson = new ReadJson();
        try {
            contensJsonTruyenThieuNhi = readJson.execute(LinkRss.GET_TRUYEN_CO_TICH_VIET_NAM).get();

            JSONArray jsonArray = new JSONArray(contensJsonTruyenThieuNhi);
            Log.d("jsonArraysss", "Chieu dai: " + jsonArray.length() + "");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String titleTruyen = jsonObject.getString("post_title");
                String descriptionTruyen = jsonObject.getString("post_desc");
                String linkTruyen = jsonObject.getString("post_content");
                String urlImgTruyen = jsonObject.getString("post_thumb");

                Truyen truyen = new Truyen(titleTruyen, descriptionTruyen, linkTruyen, urlImgTruyen);
                truyens.add(truyen);

            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("contensssss", "noi dung la: " + contensJsonTruyenThieuNhi);
        Log.d("truyensssss", truyens.toString());

        CustomListTruyen customListTruyen = new CustomListTruyen(CoTichVietNamActivity.this, R.layout.item_truyen, truyens);
        lvCoTichVietNam.setAdapter(customListTruyen);

        lvCoTichVietNam.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), TruyenActivity.class);
                intent.putExtra("contentTruyen", truyens.get(position).getLinkTruyen());
                startActivity(intent);
            }
        });

        lvCoTichVietNam.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int i = position;
                final AlertDialog.Builder alert = new AlertDialog.Builder(CoTichVietNamActivity.this);
                alert.setTitle("Lưu!!!");
                alert.setMessage("Thêm vào thư viện!!!");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dbManager = new DBManager(CoTichVietNamActivity.this);
                        dbManager.addTruyen(truyens.get(i));
                        Toast.makeText(CoTichVietNamActivity.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
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

        navCoTichVietNam.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.nav_trangchu) {
                    Intent intent1 = new Intent(CoTichVietNamActivity.this, DocTruyenActivity.class);
                    startActivity(intent1);

                } else if (id == R.id.nav_tindaluu) {
                    Intent intent = new Intent(CoTichVietNamActivity.this, TruyenDaLuuActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_truyen_thieu_nhi) {

                    Intent intent = new Intent(CoTichVietNamActivity.this, TruyenThieuNhiActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_truyen_co_tich_viet_nam) {
                    Intent intent = new Intent(CoTichVietNamActivity.this, CoTichVietNamActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_share) {

                } else if (id == R.id.nav_send) {

                }

                return false;
            }
        });

    }

    private void anhXa() {
        drawerCoTichVietNam = findViewById(R.id.drawer_cotichvietnam);
        toolbarCoTichVietNam = findViewById(R.id.toolbar_cotichvietnam);
        lvCoTichVietNam = findViewById(R.id.lv_cotichvietnam);
        navCoTichVietNam = findViewById(R.id.nav_cotichvietnam);
    }
}
