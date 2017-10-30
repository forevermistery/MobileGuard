package cn.edu.gdmec.android.mobileguard.m3communicationguard.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m2theftgurad.entity.Contactlnfo;

/**
 * Created by Administrator on 2017/10/20 0020.
 */

public class ContactAdapter extends BaseAdapter {
    private List<Contactlnfo> contactlnfos;
    private Context context;
    public ContactAdapter(List<Contactlnfo> contactlnfos, Context context){
        super();
        this.contactlnfos=contactlnfos;
        this.context=context;
    }
    @Override
    public int getCount(){
        return contactlnfos.size();
    }
    @Override
    public Object getItem(int i){
        return contactlnfos.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder=null;
          if (convertView==null){
              convertView=View.inflate(context, R.layout.item_list_contact_select,null);
              holder=new ViewHolder();
              holder.mNameTV=(TextView)convertView.findViewById(R.id.tv_name);
              holder.mPhoneTV=(TextView)convertView.findViewById(R.id.tv_phone);
              convertView.setTag(holder);
          }else
          {
              holder=(ViewHolder) convertView.getTag();
          }
          holder.mNameTV.setText(contactlnfos.get(position).name);
        holder.mPhoneTV.setText(contactlnfos.get(position).phone);
        holder.mNameTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
        holder.mPhoneTV.setTextColor(context.getResources().getColor(R.color.bright_purple));
        holder.mContactImgv.setBackgroundResource(R.drawable.brightpurple_contact_icon);
        return convertView;
    }
    static class ViewHolder{
        TextView mNameTV;
        TextView mPhoneTV;
        View mContactImgv;
    }

}
