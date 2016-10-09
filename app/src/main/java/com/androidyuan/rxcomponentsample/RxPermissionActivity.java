package com.androidyuan.rxcomponentsample;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.androidyuan.rxpermissions.RxPermissions;
import com.androidyuan.rxpermissions.component.OnPermissionsCallback;

/**
 * Created by wei on 16/10/1.
 */
public class RxPermissionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);


        findViewById(R.id.btn_permission).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //开始
                RxPermissions.getInstance(RxPermissionActivity.this)
                        .request(Manifest.permission.CAMERA)
                        .callback(new OnPermissionsCallback() {
                            @Override
                            public void call(Boolean aBoolean) {

                                Toast.makeText(RxPermissionActivity.this,"" + aBoolean,Toast.LENGTH_SHORT).show();
                            }
                        });
                //结束

            }
        });


    }
}
