package com.androidyuan.rxcomponentsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.view_rxpermision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to(RxPermissionActivity.class);
            }
        });

        findViewById(R.id.view_rxbroadcst).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to(RxBroadCastActivity.class);
            }
        });

        findViewById(R.id.view_rxbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to(RxBusActivity.class);
            }
        });




    }

    private void to(Class<?> cls) {

        startActivity(new Intent(this,cls));
    }
}
