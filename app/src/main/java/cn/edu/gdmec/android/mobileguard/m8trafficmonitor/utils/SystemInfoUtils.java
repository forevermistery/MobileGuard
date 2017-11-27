package cn.edu.gdmec.android.mobileguard.m8trafficmonitor.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/11/26 0026.
 */

public class SystemInfoUtils {
    /**
     *
     * 判断一个服务是否处于运行状态
     */
    public static boolean isServiceRunning(Context context,String className){
        ActivityManager am=(ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos=am.getRunningServices(200);
        for (ActivityManager.RunningServiceInfo info:infos){
            String servoceClassName=info.service.getClassName();
            if (className.equals(servoceClassName)){
                return true;
            }
        }
        return false;
    }
}
