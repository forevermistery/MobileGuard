package cn.edu.gdmec.android.mobileguard.m2theftgurad.utils;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class Setup1Activity extends BaseSetUpActivity {
    @Override
    public    void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_1);
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);
    }
    @Override
    public void showNext(){
        startActivityAndFinishSelf(Setup2Activity.class);
       
    }
    @Override
    public void showPre(){
        Toast.makeText(this,"当前已经是第一页",Toast.LENGTH_LONG).show();
    }
}
