package com.si.lei.android_sample_features;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * @author lei.si
 * @date 2019/02/15
 * 添加底部导航栏
 * https://github.com/yaochangliang159/Android-TabView
 *
 * @author lei.si
 * @data 2019/04/07
 * 添加悬浮窗口显示可用CPU
 * 【Bug】无法移动悬浮窗
 * https://github.com/brycegao/GlobalWindow
 */
public class MainActivity extends AppCompatActivity  {

    TextView floatWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = LayoutInflater.from(this).inflate(R.layout.layout_window_alert,
                null, false);
        floatWindow = (TextView) view.findViewById(R.id.floatWindow);

        findViewById(R.id.btn_window).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showWindow();
                //这里无法执行？？？
                new TimeThread().start();
            }
        });
    }

    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getBooleanExtra("fromBaidu", false)) {
            finish();
        }
    }

    private void showWindow() {
        WindowManager.LayoutParams wmParamsDu = new WindowManager.LayoutParams();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT > 24) {
            wmParamsDu.type = WindowManager.LayoutParams.TYPE_PHONE;
        } else {
            wmParamsDu.type = WindowManager.LayoutParams.TYPE_TOAST;
        }

        wmParamsDu.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParamsDu.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //初始化坐标
        wmParamsDu.x = 0;
        wmParamsDu.y = 800;
        //弹窗类型为系统Window
        //以左上角为基准
        wmParamsDu.gravity = Gravity.START | Gravity.TOP;
        wmParamsDu.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;;
        //如果不加,背景会是一片黑色。
        wmParamsDu.format = PixelFormat.RGBA_8888;
        View view = LayoutInflater.from(this).inflate(R.layout.layout_window_alert,
                null, false);
        windowManager.addView(view, wmParamsDu);
    }

    public class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }

    /**
     * 转换格式
     *
     * @return resultBuffer.toString() 转换格式
     */
    public String formatSize(long size) {
        String suffix = null;
        float fSize = 0;

        if (size >= 1024) {
            suffix = "KB";
            fSize = size / 1024;
            if (fSize >= 1024) {
                suffix = "MB";
                fSize /= 1024;
            }
            if (fSize >= 1024) {
                suffix = "GB";
                fSize /= 1024;
            }
        } else {
            fSize = size;
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
        if (suffix != null) {
            resultBuffer.append(suffix);
        }
        return resultBuffer.toString();
    }

    /**
     * 获得系统可用内存大小
     *
     * @return availMemStr 系统可用内存大小
     */
    private String getSystemAvaialbeMemorySize() {
        //获得ActivityManager服务的对象
        //getSystemService无法在fragment中使用，所以要先getActivity()获得context才可以
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo);
        long memSize = memoryInfo.availMem;
        //字符类型转换
        String availMemStr = formatSize(memSize);
        return availMemStr;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    floatWindow.setText(getSystemAvaialbeMemorySize());
                    Log.d("sielixinhua", getSystemAvaialbeMemorySize());
                    break;

                default:
            }
            return false;
        }
    });

    public void quick_start(View view) {
        startActivity(new Intent(this, QuickStartActivity.class));
    }
}
