package com.example.welcome.androidbgservicesdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    BgService bgService;
    TextView tvShowTime;
    BgService.ServiceBinder serviceBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvShowTime = (TextView) findViewById(R.id.textView);
    }


    public void download(View view) {
        Intent intent = new Intent(MainActivity.this, BgService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        Log.d("MainActivity", "download clicked");
    }

    public void showFiles(View view) {
        Log.d("MainActivity", "binding....");
        tvShowTime.setText("Downloaded files" + bgService.getmDownloadedFiles());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("MainActivity", "binding file started"+bgService.getmDownloadedFiles());
                    tvShowTime.setText("Downloaded files" + bgService.getmDownloadedFiles());
                    Toast.makeText(MainActivity.this, "Downloaded files  " + bgService.getmDownloadedFiles(), Toast.LENGTH_SHORT).show();

                }
            }
        };
tvShowTime.post(runnable);

    }


    ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = (BgService.ServiceBinder) service;
            bgService = serviceBinder.getService();
            Log.d("MainActivity", "service binded");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private void showToast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
