package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class AppInfoParser {

    public static List<AppInfo> getAppInfos(Context context){
        PackageManager pm=context.getPackageManager();
        List<PackageInfo> packInfos=pm.getInstalledPackages(0);
        List<AppInfo> appInfos=new ArrayList<AppInfo>();
        for (PackageInfo packInfo:packInfos){
            AppInfo appInfo=new AppInfo();
            String packname=packInfo.packageName;
            appInfo.packageName=packname;
            Drawable icon=packInfo.applicationInfo.loadIcon(pm);
            appInfo.icon=icon;
            String appname=packInfo.applicationInfo.loadLabel(pm).toString();
            appInfo.appName=appname;
            //应用程序apk包的路径
            String apkpath=packInfo.applicationInfo.sourceDir;
            appInfo.apkPath=apkpath;
            File file=new File(apkpath);
            long appSize=file.length();
            appInfo.appSize=appSize;
            //应用程序安装的位置
            int flags=packInfo.applicationInfo.flags;

            if ((ApplicationInfo.FLAG_EXTERNAL_STORAGE & flags)!=0){
                //外部存储
                appInfo.isInRoom=false;

            }else{
                appInfo.isInRoom=true;

            }
            if ((ApplicationInfo.FLAG_SYSTEM&flags)!=0){
                appInfo.isUserApp=false;
            }else{
                appInfo.isUserApp=true;
            }
            appInfos.add(appInfo);
            appInfo=null;

        }
return appInfos;
    }
}
