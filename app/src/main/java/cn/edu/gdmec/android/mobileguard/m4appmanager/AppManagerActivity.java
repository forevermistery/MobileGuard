package cn.edu.gdmec.android.mobileguard.m4appmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


import java.util.logging.LogRecord;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m4appmanager.adapter.AppManagerAdapter;
import cn.edu.gdmec.android.mobileguard.m4appmanager.entity.AppInfo;
import cn.edu.gdmec.android.mobileguard.m4appmanager.utils.AppInfoParser;

/**
 * Created by Administrator on 2017/11/11 0011.
 */

public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener {
    //手机剩余内存TextView
    private TextView mPhoneMemoryTV;
    //展示SD卡剩余内存TextView
    private TextView mSDMemoryTV;
    private ListView mListView;
    private List<AppInfo> appInfos;
    private List<AppInfo> userAppInfos=new ArrayList<AppInfo>();
    private List<AppInfo> systemAppInfos=new ArrayList<AppInfo>();
    private AppManagerAdapter adapter;
    private TextView mAppNumTV;
    //接收app卸载成功的广播
    private  UninstallReceiver receiver;

    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    if (adapter == null) {
                        adapter = new AppManagerAdapter(userAppInfos, systemAppInfos, AppManagerActivity.this);

                    }
                    mListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 15:
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    };
    private  void initData() {
        appInfos = new ArrayList<AppInfo>();
        new Thread() {
            public void run() {
                appInfos.clear();
                userAppInfos.clear();
                systemAppInfos.clear();
                appInfos.addAll(AppInfoParser.getAppInfos(AppManagerActivity.this));
                for (AppInfo appInfo : appInfos) {
                    if (appInfo.isUserApp) {
                        userAppInfos.add(appInfo);
                    } else {
                        systemAppInfos.add(appInfo);
                    }
                }
                mHandler.sendEmptyMessage(10);
            }

            ;

        }.start();

    }
    class UninstallReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            //收到广播
            initData();
        }
    }
    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_app_manager);
        //注册广播
        receiver =new UninstallReceiver();
        IntentFilter intentFilter=new IntentFilter(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(receiver,intentFilter);
        initView();
    }
    //初始化控件
    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_yellow));
        ImageView mLeftImgv=(ImageView)findViewById(R.id.imgv_leftbtn);
        ((TextView)findViewById(R.id.tv_title)).setText("软件管家");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);

        mPhoneMemoryTV=(TextView)findViewById(R.id.tv_phonememory_appmanager);
        mSDMemoryTV=(TextView)findViewById(R.id.tv_sdmemory_appmanager);
        mAppNumTV=(TextView)findViewById(R.id.tv_appnumber);
        mListView=(ListView) findViewById(R.id.lv_appmanager);

      //拿到手机剩余内存和SD卡剩余内存
        getMemoryFromPhone();
        initData();
        initListener();
    }
    @Override
    public void onClick(View view ){
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }
    //拿到手机和SD卡剩余内存
    private void getMemoryFromPhone(){
        long avail_sd= Environment.getExternalStorageDirectory().getFreeSpace();
        long avail_rom=Environment.getDataDirectory().getFreeSpace();
//格式化内存
        String str_avail_sd= Formatter.formatFileSize(this,avail_sd);
        String str_avail_rom= Formatter.formatFileSize(this,avail_rom);
        mPhoneMemoryTV.setText("剩余手机内存:"+str_avail_rom);
        mSDMemoryTV.setText("剩余SD卡内存:"+str_avail_sd);
    }
    private  void  initListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView ,View view,final int i,long l){
                if (adapter!=null){
                    new  Thread(){
                        public void run() {
                            AppInfo mappInfo = (AppInfo) adapter.getItem(i);
                            //记住当前条目的状态
                            boolean flag = mappInfo.isSelected;
                            //先将集合中所有条目的AppInfo变为未选中状态
                            for (AppInfo appInfo : userAppInfos) {
                                appInfo.isSelected = false;
                            }
                            for (AppInfo appInfo : systemAppInfos) {
                                appInfo.isSelected = false;
                            }
                            if (mappInfo != null) {
                                if (flag) {
                                    mappInfo.isSelected = false;

                                } else {
                                    mappInfo.isSelected = true;
                                }
                                mHandler.sendEmptyMessage(15);
                            }
                        };
                    }.start();
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView absListView,int i){

            }
            @Override
            public void onScroll(AbsListView absListView, int i,int i1, int i2){
                if (i>=userAppInfos.size()+1){
                    mAppNumTV.setText("系统程序:"+systemAppInfos.size()+"个");

                }else {
                    mAppNumTV.setText("用户程序:"+userAppInfos.size()+"个");

                }
            }

        });
    }
    @Override
    protected  void onDestroy(){
        unregisterReceiver(receiver);
        receiver=null;
        super.onDestroy();
    }
}
