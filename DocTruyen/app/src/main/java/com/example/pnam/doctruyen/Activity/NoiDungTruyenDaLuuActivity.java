package com.example.pnam.doctruyen.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;

import com.example.pnam.doctruyen.R;

public class NoiDungTruyenDaLuuActivity extends AppCompatActivity {

    WebView wvNoidungtruyendaluu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noi_dung_truyen_da_luu);

        wvNoidungtruyendaluu = findViewById(R.id.wv_noidungtruyendaluu);

        Intent intent = getIntent();
        String linktruyen = intent.getStringExtra("linktruyen");
        wvNoidungtruyendaluu.loadUrl(linktruyen);

    }
}
