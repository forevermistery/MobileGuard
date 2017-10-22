package cn.edu.gdmec.android.mobileguard.m2theftgurad.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.m2theftgurad.entity.Contactlnfo;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class ContactlnfoParser {
    public static List<Contactlnfo> getSystemContact(Context context) {
        ContentResolver resolver = context.getContentResolver();
        //1.查询raw_contacts表，把联系人的id取出来
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        List<Contactlnfo> infos = new ArrayList<Contactlnfo>();
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"},
                null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                System.out.println("联系人id:" + id);
                Contactlnfo info = new Contactlnfo();
                info.id = id;
                //2.根据联系人的id，查询data表，把这个id的数据取出来
                //系统api 查询data表的时候，不是真正的查询data表 而是查询的data表的视图
                Cursor dataCursor = resolver.query(datauri, new String[]{
                        "data1", "mimetype"}, "raw_contact_id=?",
                        new String[]{id}, null);
                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.println("姓名=" + data1);
                        info.name = data1;
                    }else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)){
                        System.out.print("电话="+data1);
                        info.phone=data1;
                    }
                }
                if (TextUtils.isEmpty(info.name) && TextUtils.isEmpty(info.phone))
                    continue;
                infos.add(info);
                dataCursor.close();
            }
        }
        cursor.close();
        return infos;
    }

    public static List<Contactlnfo> getSimContacts(Context context) {
        Uri uri = Uri.parse("content://icc/adn");
        List<Contactlnfo> infos = new ArrayList<Contactlnfo>();
        Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
        if (mCursor != null) {
            while(mCursor.moveToNext()){
            Contactlnfo info = new Contactlnfo();
            int nameFieldColumnIndex = mCursor.
                    getColumnIndex("name");
            info.name = mCursor.getString(nameFieldColumnIndex);
            int numberFieldColumnIndex = mCursor.
                    getColumnIndex("number");
            info.phone = mCursor.getString(numberFieldColumnIndex);
            infos.add(info);
        }
    }
    mCursor.close();
        return infos;
}
}
