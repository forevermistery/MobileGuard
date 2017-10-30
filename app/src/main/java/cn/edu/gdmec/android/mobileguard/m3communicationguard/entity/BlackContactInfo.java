package cn.edu.gdmec.android.mobileguard.m3communicationguard.entity;

/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class BlackContactInfo {
    public String phoneNumber;
//黑名单号码
    public String contactName;
    //黑名单联系人名称
    public int mode;
//    黑名单拦截方式
    public String getModeString(int mode){

        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话、短信拦截";
        }
        return "";
    }
}
