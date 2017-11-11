package cn.edu.gdmec.android.mobileguard.m4appmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class AppInfo {

    public String packageName;
//    应用程序包名
    public Drawable icon;
    //app图标
    public String appName;
    //app名称
    public String apkPath;
    //app路径
    public long appSize;
    //app大小
    public boolean isInRoom;
    //是否手机存储
    public boolean isUserApp;
    //是否用户应用
    public boolean isSelected=false;
    //是否选中，默认为false
/**拿到app位置字符串*/

public String getAppLoaction(boolean isInRoom){
    if (isInRoom){
        return "手机内存";

    }else {
        return "外部存储";
    }
}
}
