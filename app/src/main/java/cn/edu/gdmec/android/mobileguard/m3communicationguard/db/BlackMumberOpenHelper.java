package cn.edu.gdmec.android.mobileguard.m3communicationguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class BlackMumberOpenHelper extends SQLiteOpenHelper {
    private  static  String DB_NAME="my_info";
    private  static  int VERSION=1;
    private  static  BlackMumberOpenHelper instance=null;
    public static BlackMumberOpenHelper getInstance(Context context){
        if (instance==null){
            instance=new BlackMumberOpenHelper(context,DB_NAME,null,VERSION);
        }
        return instance;
    }
    public BlackMumberOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        this.DB_NAME=name;
        this.VERSION=version;

    }
    @Override
    public void  onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("create table blacknumber"+
                "(id integer primary key autoincrement,"+
                "number varchar(20),"+
                "name varchar(255),"+
                "mode integer,"+"mtype varchar(200))");
    }

@Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int i,int i1){

}
}
