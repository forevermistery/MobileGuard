package cn.edu.gdmec.android.mobileguard.m5virusscan.entity;

import android.graphics.drawable.Drawable;

import cn.edu.gdmec.android.mobileguard.m2theftgurad.utils.MD5Utils;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class ScanAppInfo {
    public String appName;
    public boolean isVirus;
    public String packagename;
    public String description;
    public Drawable appicon;
    public String virusScanUrl="";
    public String md5info;
}
