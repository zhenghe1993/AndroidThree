package com.android3;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * Created by Administrator on 2016/10/25.
 */

public class MyPagerAdapter extends PagerAdapter {
   private List<View> list=null;
    //通过构造函数将装有View的集合传入适配器
    MyPagerAdapter(List<View> list){
        this.list=list;
    }

    /**
     *
     * @return 将要显示的View的总个数
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     *
     * 该方法  判断Key与View是否是正确的映射关系
     *
     * 而且在同一个容器中不能有相同的key传入，否则行为会错乱
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 因为ViewPager 容器中只保存当前位置的View和相邻的View，当在容器中的View的编号位置距离当前位置超过1，
     * 就对该View进行移除
     * @param container 容器
     * @param position  即将失去的View位置
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    /**
     *
     * 首先判断当前位置的左右view是否在容器中，如果不在，则调用instantiateItem方法将相邻的view（之前不在容器中）加入容器中；
     * 返回此View，作为映射的Key
     *
     * @param container 容器
     * @param position  位置 从0开始
     * @return  view对应的Key
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }
}
