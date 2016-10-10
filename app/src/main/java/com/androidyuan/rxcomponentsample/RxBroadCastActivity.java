package com.androidyuan.rxcomponentsample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.androidyuan.rxbroadcast.RxLocalBroadcastManager;
import com.androidyuan.rxbroadcast.component.RxBroadcastReceiver;
import com.androidyuan.rxbroadcast.component.RxBroadcastReceiverBackground;
import com.androidyuan.rxbroadcast.component.RxOnReceive;

public class RxBroadCastActivity extends AppCompatActivity {
    /**
     * 测试发现 多次 commit 都没有收到多条 解决 使用　LocalBroadCastManager　时带来的问题
     */
    RxBroadcastReceiver broadCastReceiverAsync = new RxBroadcastReceiver() {
        @SuppressLint("LongLogTag")
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("RxLocalBroadCastManager send:",
                    "" + intent.getAction() + " ,onReceive is MainThraed=" + isMainTread());
        }
    };
    RxBroadcastReceiver broadCastReceiverback = new RxBroadcastReceiverBackground() {
        @SuppressLint("LongLogTag")
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("RxLocalBroadCastManager send:",
                    "" + intent.getAction() + " ,onReceive is MainThraed=" + isMainTread());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        IntentFilter intentFilters=new IntentFilter();
        intentFilters.addAction("testthread");
        RxLocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiverback,intentFilters);


        //the test repeated registration.
        RxLocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiverAsync,intentFilters);
        RxLocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiverAsync,intentFilters);
        RxLocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiverAsync,intentFilters);

        RxLocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("testthread"));


    }

    @SuppressLint("LongLogTag")



    private boolean isMainTread() {

        return Looper.myLooper() == Looper.getMainLooper();
    }

    // we need unRegister.
    @Override
    protected void onDestroy() {

        super.onDestroy();


        RxLocalBroadcastManager.getInstance(this).unregisterReceiver(broadCastReceiverAsync);
        RxLocalBroadcastManager.getInstance(this).unregisterReceiver(broadCastReceiverback);
    }
}
