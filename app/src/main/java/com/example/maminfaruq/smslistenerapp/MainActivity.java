package com.example.maminfaruq.smslistenerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCheckPermision;
    Button btnDownload;

    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    private BroadcastReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckPermision = findViewById(R.id.btn_permision);
        btnCheckPermision.setOnClickListener(this);

        btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);

        registerReceiver(downloadReceiver, downloadIntentFilter);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_permision) {
            PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE);
        } else if (v.getId() == R.id.btn_download) {
            startService(new Intent(MainActivity.this, DownloadService.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReceiver != null) {
            unregisterReceiver(downloadReceiver);
        }
    }

    final int SMS_REQUEST_CODE = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permisions,
                                           @NonNull int[] grantResults) {
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
