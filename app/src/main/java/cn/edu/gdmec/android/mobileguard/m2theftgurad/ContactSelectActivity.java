package cn.edu.gdmec.android.mobileguard.m2theftgurad;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftgurad.adapter.ContactAdapter;
import cn.edu.gdmec.android.mobileguard.m2theftgurad.entity.Contactlnfo;
import cn.edu.gdmec.android.mobileguard.m2theftgurad.utils.ContactlnfoParser;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private ContactAdapter adapter;
    private List<Contactlnfo> systemContacts;
    Handler mHandler=new Handler() {
        public void handleMessage(android.os.Message msg){
switch (msg.what){
    case 10:
        if (systemContacts!=null){
            adapter=new ContactAdapter(systemContacts,ContactSelectActivity.this);
        mListView.setAdapter(adapter);
        }
        break;
}
        };
    };
@Override
    protected  void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_select);
    initView();
}
private void initView(){
    ((TextView)findViewById(R.id.tv_title)).setText("选择联系人");
    ImageView mLeftImgv=(ImageView) findViewById(R.id.imgv_leftbtn);
    mLeftImgv.setOnClickListener(this);
    mLeftImgv.setImageResource(R.drawable.back);

    findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.purple));
    mListView=(ListView)findViewById(R.id.lv_contact);
    new Thread(){
        public void run(){
            systemContacts=ContactlnfoParser.getSystemContact(ContactSelectActivity.this);
            systemContacts.addAll(ContactlnfoParser.getSimContacts(ContactSelectActivity.this));
       mHandler.sendEmptyMessage(10);
        };
    }.start();
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            Contactlnfo item=(Contactlnfo) adapter.getItem(position);
            Intent intent=new Intent();
            intent.putExtra("phone",item.phone);
            intent.putExtra("name",item.name);//补充1

            setResult(0,intent);
            finish();
        }
    });
}
@Override
    public void onClick(View view ){
    switch (view.getId()){
        case R.id.imgv_leftbtn:
            finish();
            break;
    }
}
}
