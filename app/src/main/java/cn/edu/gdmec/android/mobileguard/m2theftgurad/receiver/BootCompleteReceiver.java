package cn.edu.gdmec.android.mobileguard.m2theftgurad.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.m9advancedtools.service.AppLockService;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        ((App)(context.getApplicationContext())).correctSIM();
        //启动程序锁服务
        context.startActivity(new Intent(context, AppLockService.class));
    }
}
