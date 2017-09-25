package com.example.welcome.androidbgservicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by welcome on 8/12/2017.
 */
public class BgService extends Service {
    int mDownloadedFiles = 0;

    IBinder iBinder = new ServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Lokesh","onStart command called");
        new DownloadFiles().execute();
        Toast.makeText(BgService.this, "Service started!", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class ServiceBinder extends Binder {
        BgService getService() {
            return BgService.this;
        }
    }

    public int getmDownloadedFiles() {
        return mDownloadedFiles;
    }




    public class DownloadFiles extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (mDownloadedFiles<100) {
                try {

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mDownloadedFiles++;
                Log.d("MainActivity","BgService++"+mDownloadedFiles);
            }
            return null;
        }
    }
}
