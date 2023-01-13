package com.example.boundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bt1, bt2;
    private BService bindService;
    private Boolean  isServiceConnected;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BService.MyBinder myBinder = (BService.MyBinder) iBinder;
            bindService = myBinder.getBoundService();
            bindService.startMusic();
            isServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isServiceConnected = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.start);
        bt2 = findViewById(R.id.stop);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStartService();
            }
        });
        
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStopService();
            }
        });
    }

    private void onClickStopService() {
        if(isServiceConnected){
            unbindService(serviceConnection);
            isServiceConnected = false;
        }
    }

    private void onClickStartService() {
        Intent intent = new Intent(MainActivity.this, BService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}