package cn.edu.gdmec.android.mobileguard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.m1home.utils.MyUtils;
import cn.edu.gdmec.android.mobileguard.m1home.utils.VersionUpdateUtils;

/**
 * Created by Administrator on 2017/11/17 0017.
 */

public class UpdateVirusActivity extends AppCompatActivity {
    private TextView mTvVersion;
    private String mVirusVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mVirusVersion= MyUtils.getVersion(getApplicationContext());
        mTvVersion=(TextView)findViewById(R.id.tv_splash_version);
        mTvVersion.setText("版本号"+mVirusVersion);

        final VersionUpdateUtils Virus_versionUpdateUtils=new VersionUpdateUtils(mVirusVersion,UpdateVirusActivity.this);
        new Thread(){
            @Override
            public void run(){
                super.run();
                Virus_versionUpdateUtils.getCloudVersion();
            }
        }.start();
    }
}
