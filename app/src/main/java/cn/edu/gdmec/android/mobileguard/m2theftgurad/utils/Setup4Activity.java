package cn.edu.gdmec.android.mobileguard.m2theftgurad.utils;

import android.os.Bundle;
import android.widget.RadioButton;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class Setup4Activity extends BaseSetUpActivity {
    @Override
    public    void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_4);
        ((RadioButton)findViewById(R.id.rb_four)).setChecked(true);
    }
    @Override
    public void showNext(){
        startActivityAndFinishSelf(LostFindActivity.class);
    }
    @Override
    public void showPre(){
        startActivityAndFinishSelf(Setup3Activity.class);
    }
}