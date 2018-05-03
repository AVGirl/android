package com.fff.tools;

import android.content.Context;
import android.content.res.Resources;

public class DensityTool {

    //�����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
    public static float dp2px(Resources resources, float dpValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
    //�����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
    //�����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
    public static float px2dp(Resources resources, float pxValue) {
        final float scale = resources.getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }
    //��ȡ��Ļdpi
    public static int getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }
}