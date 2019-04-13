package com.example.pnam.doctruyen.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pnam.doctruyen.Adapter.CustomListTruyen;
import com.example.pnam.doctruyen.DB.DBManager;
import com.example.pnam.doctruyen.GetRSS.GetRSS;
import com.example.pnam.doctruyen.Link.LinkRss;
import com.example.pnam.doctruyen.Object.Truyen;
import com.example.pnam.doctruyen.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DocTruyenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView lvTruyen;
    GetRSS getRSS;
    ArrayList<Truyen> truyens;
    DBManager dbManager;

    ArrayList<Truyen> truyensDB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_truyen);

        anhXa();
        action();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("App Đọc Truyện");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Email: Triệu Quang Tài", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void action() {
        getRSS = new GetRSS();
        truyens = new ArrayList<>();

        try {
            truyens = getRSS.execute(LinkRss.VNEXPRESS_RSS_CUOI).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        CustomListTruyen customListTruyen = new CustomListTruyen(getBaseContext(), R.layout.item_truyen, truyens);
        lvTruyen.setAdapter(customListTruyen);

        lvTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DocTruyenActivity.this, truyens.get(position) + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), TruyenActivity.class);
                intent.putExtra("contentTruyen", truyens.get(position).getLinkTruyen());
                startActivity(intent);
            }
        });


        lvTruyen.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

//                Toast.makeText(DocTruyenActivity.this, truyens.get(position).toString(), Toast.LENGTH_SHORT).show();
                dbManager = new DBManager(DocTruyenActivity.this);

                final int i = position;
                final AlertDialog.Builder alert = new AlertDialog.Builder(DocTruyenActivity.this);
                alert.setTitle("Lưu!!!");
                alert.setMessage("Thêm vào thư viện!!!");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Truyen truyen = new Truyen(truyens.get(position).getTitleTruyen(), truyens.get(position).getDescriptionTruyen()
                                , truyens.get(position).getLinkTruyen(), truyens.get(position).getUrlImgTruyen());
                        long checkInsert = dbManager.addTruyen(truyen);
                        if (checkInsert > 0) {
                            Toast.makeText(DocTruyenActivity.this, "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
                            truyensDB.addAll(dbManager.getAllData());
                            for (int i = 0; i < truyensDB.size(); i++) {
                                Log.d("trangham", "Danh sach data: " + truyensDB.get(i).toString() + "\n");
                            }
                        }

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

    }

    private void anhXa() {
        lvTruyen = findViewById(R.id.lv_truyen);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doc_truyen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_trangchu) {
            Intent intent1 = new Intent(DocTruyenActivity.this, DocTruyenActivity.class);
            startActivity(intent1);

        } else if (id == R.id.nav_tindaluu) {
            Intent intent = new Intent(DocTruyenActivity.this, TruyenDaLuuActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_truyen_thieu_nhi) {

            Intent intent = new Intent(DocTruyenActivity.this,TruyenThieuNhiActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_truyen_co_tich_viet_nam) {
            Intent intent = new Intent(DocTruyenActivity.this, CoTichVietNamActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
