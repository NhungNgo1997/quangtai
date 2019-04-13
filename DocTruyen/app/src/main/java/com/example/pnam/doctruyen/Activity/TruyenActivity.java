package com.example.pnam.doctruyen.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;

import com.example.pnam.doctruyen.Adapter.CustomListTruyen;
import com.example.pnam.doctruyen.GetRSS.GetRSS;
import com.example.pnam.doctruyen.Link.LinkRss;
import com.example.pnam.doctruyen.Object.Truyen;
import com.example.pnam.doctruyen.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TruyenActivity extends AppCompatActivity {

    WebView wvNoidungtruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truyen);

        anhXa();
        action();

    }

    private void action() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("contentTruyen");
        wvNoidungtruyen.loadUrl(url);

    }

    private void anhXa() {
       wvNoidungtruyen = findViewById(R.id.wv_noidungtruyen);
    }
}
