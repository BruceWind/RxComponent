package com.androidyuan.rxcomponentsample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.androidyuan.rxbus.RxBus;
import com.androidyuan.rxbus.component.Subscribe;
import com.androidyuan.rxbus.component.ThreadMode;
import com.androidyuan.rxcomponentsample.model.DriverEvent;

/**
 * Created by wei on 2016/10/9.
 */
public class RxBusActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        RxBus.getInstance().register(this);

        setContentView(R.layout.activity_bus);


        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        RxBus.getInstance().post("scream1");
                        RxBus.getInstance().post(new DriverEvent("scream2"));

                    }
                }).start();

                RxBus.getInstance().post("scream3");

            }
        });

    }



    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void handleEvent(DriverEvent event) {
        Log.d("RXJAVA", "event info = "+event.info+", is MainThread : "+(Looper.getMainLooper()==Looper.myLooper()));
    }



    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleEvent(String event) {
        Log.d("RXJAVA",
                "handleEvent info = " + event + ", is MainThread : " + (Looper.getMainLooper()
                        == Looper.myLooper()));
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handle(String event) {
        Log.d("RXJAVA", "handle info = " + event + ", is MainThread : " + (Looper.getMainLooper()
                == Looper.myLooper()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }

}
