package cn.edu.gdmec.android.mobileguard.m4appmanager.utils;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
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
            //

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
            String mVersion=packInfo.versionName;
            appInfo.mVersion=mVersion;
            //版本号


            //
            appInfo.InstallTime = new Date(packInfo.firstInstallTime).toLocaleString();
            int flags=packInfo.applicationInfo.flags;
                //签证
            try {
                                PackageInfo packinfo = pm.getPackageInfo(packname,
                        PackageManager.GET_SIGNATURES);
                                byte[] ss = packinfo.signatures[0].toByteArray();
                                CertificateFactory cf = CertificateFactory.getInstance("X509");
                                X509Certificate cert = (X509Certificate) cf.generateCertificate(
                                                new ByteArrayInputStream(ss));
                                if (cert!=null){
                                        appInfo.certificate=cert.getIssuerDN().toString();
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                           }
//             活动activity
            try {
                PackageInfo packinfo = pm.getPackageInfo(packname,
                        PackageManager.GET_ACTIVITIES);
                ActivityInfo[] act = packinfo.activities;
              List<ActivityInfo> ac=new ArrayList<>();
                if (act!=null) {
                    for (ActivityInfo str : act) {
                        ac.add(str);
                        appInfo.activitys = ac.toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
                       PackageInfo packinfo1 = null;
                       try {
                               packinfo1 = pm.getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
                               if (packinfo1.requestedPermissions!=null){
                                     for (String pio : packinfo1.requestedPermissions){
                                              appInfo.permission= appInfo.permission+pio+"\n";
                                         }
                                 }
                        } catch (Exception e) {
                              e.printStackTrace();
                           }

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
