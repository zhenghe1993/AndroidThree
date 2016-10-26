package com.android3;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ArrayList<View> viewList;
    private ImageView cursor;
    private float offset = 0;
    private float screenW = 0;
    private float eCurrentX = 0;
    private Matrix matrix;
    private float fScreenW = 0;
    private int currentIndex = 0;
    private int temp = 1;
    private float sCurrentX;
    private int len;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initToolbar();
        initViewPager();
    }

    /**
     * ViewPager 保证 缓存中存在三个视图，即 左边 右边 和中间 隔一个的灰被destroy，
     */
    @SuppressLint("InflateParams")
    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        LinearLayout titleBar = (LinearLayout) findViewById(R.id.titleBar);
        LayoutInflater inflater = getLayoutInflater();
        //创建四个View
        View view1 = inflater.inflate(R.layout.viewpage_01, null);
        View view2 = inflater.inflate(R.layout.viewpage_02, null);
        View view3 = inflater.inflate(R.layout.viewpage_03, null);
        View view4 = inflater.inflate(R.layout.viewpage_04, null);

        viewList = new ArrayList<>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        len = viewList.size();
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        List<String> titleList = new ArrayList<>();
        titleList.add("第一页面");
        titleList.add("第二页面");
        titleList.add("第三页面");
        titleList.add("第四页面");
        for (int i = 0; i < titleList.size(); i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.setMargins(5, 3, 5, 3);
            textView.setLayoutParams(params);
            textView.setText(titleList.get(i));
            textView.setTextSize(15);
            textView.setGravity(Gravity.CENTER);
            titleBar.addView(textView);
        }

        initCursorPos();   //初始化指示器位置

        viewPager.setAdapter(adapter);//绑定适配器
        viewPager.addOnPageChangeListener(this); //注 ： setOnPageChangeListener 过时
    }

    /**
     * 单位px
     */
    public void initCursorPos() {
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.cursor);
        float cursorW = BitmapFactory.decodeResource(getResources(), R.mipmap.cursor).getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;// 获取分辨率宽度
        fScreenW = screenW / viewList.size();
        offset = (fScreenW - cursorW) / 2;// 计算偏移量
        matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置   ###原始位置
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case -1:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        System.out.println("position---"+position);
        System.out.println("positionOffset---"+positionOffset);
        System.out.println("positionOffsetPixels---"+positionOffsetPixels);
        sCurrentX = positionOffset * fScreenW;
         if(position!=currentIndex){
             temp=1;
             currentIndex=position;
             return;
         }
            if (temp == 0) {
                matrix.postTranslate((sCurrentX - eCurrentX), 0);
                cursor.setImageMatrix(matrix);
                eCurrentX = sCurrentX;

            } else {
                if (positionOffset > 0.5) {
                    eCurrentX = fScreenW;
                } else {
                    eCurrentX = 0;
                }
                temp--;
            }
        currentIndex=position;

    }

    @Override
    public void onPageSelected(int position) {
     }

    @Override
    public void onPageScrollStateChanged(int state) {
        System.out.println(state);
    }
}
