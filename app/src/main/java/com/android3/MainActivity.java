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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ArrayList<View> viewList;
    private List<String> titleList;
    private MyPagerAdapter adapter;
    private ImageView cursor;
    private LinearLayout titleBar;
    private float bmpw = 0;
    private float offset = 0;
    private float currentIndex = 0;
    private float screenW = 0;
    private float currentX = 0;
    private float fScreenW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initViewPager();
    }

    /**
     * ViewPager 保证 缓存中存在三个视图，即 左边 右边 和中间 隔一个的灰被destroy，
     */
    @SuppressLint("InflateParams")
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        titleBar = (LinearLayout) findViewById(R.id.titleBar);
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
        adapter = new MyPagerAdapter(viewList);
        titleList = new ArrayList<>();
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
     *  单位px
     */
    public void initCursorPos() {
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.mipmap.cursor).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenW = dm.widthPixels;// 获取分辨率宽度
        fScreenW=screenW / viewList.size();
        offset = (fScreenW - bmpw) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置   ###原始位置
        currentX = offset;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btnRight = (Button) mToolbar.findViewById(R.id.btnRight);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(this);
        btnRight.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case -1:
                finish();
                break;
            case R.id.btnRight:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onPageSelected(int position) {
        float X=fScreenW*position; //在此页面中，滑块位置和原始位置的距离
        //currentX 当前位置和原始位置的距离
        TranslateAnimation animation = new TranslateAnimation(currentX, X, 0, 0);
        /**
         *  public TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta) {
         *  throw new RuntimeException("Stub!");
         * }
         * 在X轴方向：
         * fromXDelta  动画从原始位置+fromXDelta开始
         *
         * toXDelta   动画从原始位置+toXDelta结束
         *
         *  在Y轴方向：
         * fromYDelta  动画从原始位置+fromYDelta开始
         *
         * toYDelta   动画从原始位置+toYDelta结束
         *
         *
         */
        currentX=fScreenW*position;//更新当前位置
        currentIndex = position;//保存上一个View编号
        animation.setFillAfter(true);//保存动画
        animation.setDuration(300);//动画持续时间
        cursor.startAnimation(animation);//开始
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
