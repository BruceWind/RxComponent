# RxComponent

借用RxJava实现，RxBroadCast，RxBus，RxPerMission。


# 栗子


## 使用RxBroadCast
```
MainActivity extends AppCompatActivity implements RxOnReceive {

    RxBroadCastReceiver broadCastReceiverAsync = new RxBroadCastReceiver(this);
    RxBroadCastReceiver broadCastReceiverback = new RxBroadCastReceiverBackground(this);
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
    
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            broadCastReceiverAsync.putFilter("testthread");
            broadCastReceiverAsync.commit();
    
            broadCastReceiverback.putFilter("testthread");//注册到相同的filter
            broadCastReceiverback.commit();
            //the test repeated registration.
            broadCastReceiverback.commit();//多次注册 只会生效一次
            broadCastReceiverback.commit();
            broadCastReceiverback.commit();
    
            RxBroadCastManager.getInstance().sendBroadcast("testthread", "scream");//sendBroadcast "testthread" 
    
        }
        
        @Override
        public void call(Object o) {
        
            Log.d("RxBroadCastManager send:", "" + o + " ,onReceive is MainThraed=" + isMainTread());
        }

...
...

}
    
//log如下 ：

RxBroadCastManager send:: scream ,onReceive is MainThraed=true
RxBroadCastManager send:: scream ,onReceive is MainThraed=false

```


# 使用RxPerMissions


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


# 使用RxBus

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