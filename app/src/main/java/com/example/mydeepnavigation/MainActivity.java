package com.example.mydeepnavigation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOpenDetail = (Button)findViewById(R.id.btn_open_detail);
        btnOpenDetail.setOnClickListener(this);
        DelayAsync delayAsync = new DelayAsync();
        delayAsync.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_open_detail){
            Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
            detailIntent.putExtra(DetailActivity.EXTRA_TITLE, "Haloo Selamat Datang");
            detailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, "Sekarang Anda bisa belajar android dalam dicoding ");
            startActivity(detailIntent);
        }

    }
    private class DelayAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void  onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            showNotifications(MainActivity.this, "Hay Apa Kabar?", "Apakah anda sudah liburan minggu ini",110);
        }
    }

    private void showNotifications(Context context, String title, String message, int notifId) {
        Intent notifDetailInten = new Intent(MainActivity.this, DetailActivity.class);
        notifDetailInten.putExtra(DetailActivity.EXTRA_TITLE, title);
        notifDetailInten.putExtra(DetailActivity.EXTRA_MESSAGE,message);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(DetailActivity.class)
                .addNextIntent(notifDetailInten)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompact = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_email_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.white))
                .setVibrate(new long[]{1000,1000,1000,1000,1000,1000,1000,1000,1000,1000,})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManagerCompact.notify(notifId, builder.build());
    }
}
