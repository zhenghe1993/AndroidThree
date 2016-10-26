package com.android3;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/25.
 */


    public class DensityUtil {

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public static float dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public static float px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (pxValue / scale + 0.5f);
        }
    }

