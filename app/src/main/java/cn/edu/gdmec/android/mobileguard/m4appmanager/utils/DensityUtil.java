package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class DensityUtil {
    //dip转换像素px
    public static int dip2px(Context context,float dpValue){
        try {
            final float scale=context.getResources().getDisplayMetrics().density;
            return (int)(dpValue*scale+0.5f);

        }catch (Exception e){
            e.printStackTrace();
        }
        return (int)dpValue;

    }
    //像素转化dip
    public static int px2dip(Context context,float pxValue){
        try {
            final float scale=context.getResources().getDisplayMetrics().density;
            return (int)(pxValue/scale+0.5f);

        }catch (Exception e){
            e.printStackTrace();
        }
        return (int)pxValue;
    }
}
