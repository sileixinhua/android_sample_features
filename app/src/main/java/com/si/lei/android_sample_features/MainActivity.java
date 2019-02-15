package com.si.lei.android_sample_features;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

/**
 * @author lei.si
 * @date 2019/02/15
 * 添加底部导航栏
 * https://github.com/yaochangliang159/Android-TabView
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void quick_start(View view){
        startActivity(new Intent(this,QuickStartActivity.class));
    }
}
