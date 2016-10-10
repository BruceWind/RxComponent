# RxComponent

借用RxJava实现，RxBroadCast，RxBus，RxPermission。

三个组件的git地址：

[https://github.com/weizongwei5/RxComponent](https://github.com/weizongwei5/RxComponent)

[https://github.com/weizongwei5/RxBusLib](https://github.com/weizongwei5/RxBusLib)

[https://github.com/weizongwei5/rxpermissions](https://github.com/weizongwei5/rxpermissions)


# 栗子


## 使用RxBroadcast
```
MainActivity extends AppCompatActivity  {

    RxBroadcastReceiver broadcastReceiverAsync = new RxBroadcastReceiver(){

        @SuppressLint("LongLogTag")
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("RxLocalBroadcastManager send:", intent.getAction()+ " ,onReceive is MainThraed=" + isMainTread());
        }
    };
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
    
            super.onCreate(savedInstanceState);

            IntentFilter filters=new IntentFilter();
            filters.addAction("testthread");
            RxLocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiverAsync,filters);
            
        }


...
...

}
    
//log如下 ：

RxBroadCastManager send:: testthread ,onReceive is MainThraed=true
RxBroadCastManager send:: testthread ,onReceive is MainThraed=false


```


## 使用RxPermissions


```

        <!--mainfast 设置透明 notitlebar -->
        <activity android:name="com.androidyuan.rxpermissions.PermissionReqActivity"
                  android:theme="@android:style/Theme.Translucent.NoTitleBar" />

                //开始
                RxPermissions.getInstance(MainActivity.this)
                        .request(Manifest.permission.CAMERA)
                        .callback(new OnPermissionsCallback() {
                            @Override
                            public void call(Boolean aBoolean) {
                                toast("" + aBoolean);
                            }
                        });
                //结束

                
                //lambda结构
                RxPermissions.getInstance(MainActivity.this)
                .request(Manifest.permission.CAMERA)
                .callback((Boolean aBoolean)-> toast(""+aBoolean) });//显示成功与否


```


## 使用RxBus

```

public class MainActivity extends AppCompatActivity {

    private final String filer="testfilter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RxBus.getInstance().register(this);

        startActivity(new Intent(this,SecondActivity.class));



    }


    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void handleEvent(DriverEvent event) {
        Log.d("RXJAVA", "event info = "+event.info+", is MainThread : "+(Looper.getMainLooper()==Looper.myLooper()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().unRegister(this);
    }
}

```