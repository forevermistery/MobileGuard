package cn.edu.gdmec.android.mobileguard.m3communicationguard.reveiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.App;
import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftgurad.ContactSelectActivity;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class AddBlackNumberActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox mSmsCB;
    private CheckBox mTelCB;
    private EditText mNumET;
    private EditText mNameET;
    private EditText mTypeET;
    private BlackNumberDao dao;

    private void initView(){

        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_purple));
        ((TextView)findViewById(R.id.tv_title)).setText("添加黑名单");
        ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);

        mSmsCB=(CheckBox) findViewById(R.id.cb_blacknumber_sms);
        mTelCB=(CheckBox)findViewById(R.id.cb_blacknumber_tel);

        mNumET=(EditText) findViewById(R.id.et_blacknumber);
        mNameET=(EditText)findViewById(R.id.et_blackname);
        mTypeET=(EditText) findViewById(R.id.et_blacktype);

        findViewById(R.id.add_blacknum_btn).setOnClickListener(this);
        findViewById(R.id.add_fromcontact_btn).setOnClickListener(this);
    }
    @Override
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data!=null){
            //获取选中的联系人信息
            String phone=data.getStringExtra("phone");
            String name=data.getStringExtra("name");
            String type=data.getStringExtra("mtype");
            mNameET.setText(name);
            mNumET.setText(phone);
            mTypeET.setText(type);

        }
    }
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_black_number);
        dao=new BlackNumberDao(AddBlackNumberActivity.this);
        initView();

    }
    @Override
    public  void onClick(View view ){
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.add_blacknum_btn:
                String number=mNumET.getText().toString().trim();
                String name=mNameET.getText().toString().trim();
                String type=mTypeET.getText().toString().trim();
                if (TextUtils.isEmpty(number)||TextUtils.isEmpty(name)){
                    Toast.makeText(this,"电话号码和手机号不能为空!",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    //电话号码和名称不能为空
                    BlackContactInfo blackContactInfo=new BlackContactInfo();
                    blackContactInfo.phoneNumber=number;
                    blackContactInfo.contactName=name;
                    blackContactInfo.preventType=type;
                    if (mSmsCB.isChecked()&mTelCB.isChecked()){
                        //两种拦截模式都选
                        blackContactInfo.mode=3;
                    }else  if (mSmsCB.isChecked()&!mTelCB.isChecked()){
                        //短信拦截
                        blackContactInfo.mode=2;
                    }else  if (!mSmsCB.isChecked()&mTelCB.isChecked()){
                        //电话拦截
                        blackContactInfo.mode=1;
                    }else{
                        Toast.makeText(this,"请选择拦截模式",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!dao.IsNumberExist(blackContactInfo.phoneNumber)){
                        dao.add(blackContactInfo);

                    }else {
                        Toast.makeText(this,"该号码已经被添加至黑名单",Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
                break;
            case R.id.add_fromcontact_btn:
                startActivityForResult(new Intent(this, ContactSelectActivity.class),0);
                break;

        }
    }
}
