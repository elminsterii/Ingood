package com.fff.ingood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fff.ingood.R;

import java.util.ArrayList;

/**
 * Created by yoie7 on 2018/5/22.
 */

public class RadioListAdapter extends BaseAdapter {
    public static final int KEY_1 = 1;
    public static final int KEY_2 = 2;
    public static final int KEY_3 = 3;
    public static final int KEY_4 = 4;
    public static final int KEY_5 = 5;
    public static final int KEY_6 = 6;
    public static final int KEY_7 = 7;
    public static final int KEY_8 = 8;
    public static final int KEY_9 = 9;
    public static final int KEY_10 = 10;

    private LayoutInflater inflater;
    private String[] interests;
    private viewHolder holder;
    private ArrayList<Boolean> radioStateList;
    // 标记用户当前选择的那一个作家
    private int index = -1;
    private Context mContext;

    public RadioListAdapter(Context c, String[] interests, ArrayList<Boolean> radioStateList) {
        super();
        this.mContext = c;
        this.interests = interests;
        this.radioStateList = radioStateList;
        inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return interests.length;
    }

    @Override
    public Boolean getItem(int position) {

        return radioStateList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<Boolean> getRadioStateList(){
        return radioStateList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new viewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_radio_list, null);
            holder.nameTxt = (TextView) convertView.findViewById(R.id.interest_name);
            holder.selectBtn = (RadioButton) convertView
                    .findViewById(R.id.radio);
            holder.index = position;


            switch (position){
                case 0:{
                    convertView.setTag(R.id.tag_0, holder);
                    break;
                }
                case 1:{
                    convertView.setTag(R.id.tag_1, holder);
                    break;
                }
                case 2:{
                    convertView.setTag(R.id.tag_2, holder);
                    break;
                }
                case 3:{
                    convertView.setTag(R.id.tag_3, holder);
                    break;
                }
                case 4:{
                    convertView.setTag(R.id.tag_4, holder);
                    break;
                }
                case 5:{
                    convertView.setTag(R.id.tag_5, holder);
                    break;
                }
                case 6:{
                    convertView.setTag(R.id.tag_6, holder);
                    break;
                }
                case 7:{
                    convertView.setTag(R.id.tag_7, holder);
                    break;
                }
                case 8:{
                    convertView.setTag(R.id.tag_8, holder);
                    break;
                }
                case 9:{
                    convertView.setTag(R.id.tag_9, holder);
                    break;
                }

            }


//            convertView.setTag(holder.index, holder);


        } else {
            switch (position){
                case 0:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_0);
                    break;
                }
                case 1:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_1);
                    break;
                }
                case 2:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_2);
                    break;
                }
                case 3:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_3);
                    break;
                }
                case 4:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_4);
                    break;
                }
                case 5:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_5);
                    break;
                }
                case 6:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_6);
                    break;
                }
                case 7:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_7);
                    break;
                }
                case 8:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_8);
                    break;
                }
                case 9:{
                    holder = (viewHolder) convertView.getTag(R.id.tag_9);
                    break;
                }

            }
            //holder = (viewHolder) convertView.getTag(position);
        }

        if(holder == null){
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.item_radio_list, null);
            holder.nameTxt = (TextView) convertView.findViewById(R.id.interest_name);
            holder.selectBtn = (RadioButton) convertView
                    .findViewById(R.id.radio);
            holder.index = position;
            switch (position){
                case 0:{
                    convertView.setTag(R.id.tag_0, holder);
                    break;
                }
                case 1:{
                    convertView.setTag(R.id.tag_1, holder);
                    break;
                }
                case 2:{
                    convertView.setTag(R.id.tag_2, holder);
                    break;
                }
                case 3:{
                    convertView.setTag(R.id.tag_3, holder);
                    break;
                }
                case 4:{
                    convertView.setTag(R.id.tag_4, holder);
                    break;
                }
                case 5:{
                    convertView.setTag(R.id.tag_5, holder);
                    break;
                }
                case 6:{
                    convertView.setTag(R.id.tag_6, holder);
                    break;
                }
                case 7:{
                    convertView.setTag(R.id.tag_7, holder);
                    break;
                }
                case 8:{
                    convertView.setTag(R.id.tag_8, holder);
                    break;
                }
                case 9:{
                    convertView.setTag(R.id.tag_9, holder);
                    break;
                }

            }
        }
        holder.nameTxt.setText(interests[position]);
        final View cView = convertView;
        holder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position){
                    case 0:{
                        holder = (viewHolder) cView.getTag(R.id.tag_0);
                        break;
                    }
                    case 1:{
                        holder = (viewHolder) cView.getTag(R.id.tag_1);
                        break;
                    }
                    case 2:{
                        holder = (viewHolder) cView.getTag(R.id.tag_2);
                        break;
                    }
                    case 3:{
                        holder = (viewHolder) cView.getTag(R.id.tag_3);
                        break;
                    }
                    case 4:{
                        holder = (viewHolder) cView.getTag(R.id.tag_4);
                        break;
                    }
                    case 5:{
                        holder = (viewHolder) cView.getTag(R.id.tag_5);
                        break;
                    }
                    case 6:{
                        holder = (viewHolder) cView.getTag(R.id.tag_6);
                        break;
                    }
                    case 7:{
                        holder = (viewHolder) cView.getTag(R.id.tag_7);
                        break;
                    }
                    case 8:{
                        holder = (viewHolder) cView.getTag(R.id.tag_8);
                        break;
                    }
                    case 9:{
                        holder = (viewHolder) cView.getTag(R.id.tag_9);
                        break;
                    }

                }

                    if(radioStateList.get(position) != holder.selectBtn.isChecked()){
                        radioStateList.set(position, holder.selectBtn.isChecked());
                    }
                    else {
                        radioStateList.set(position, !holder.selectBtn.isChecked());
                        holder.selectBtn.setChecked(!holder.selectBtn.isChecked());
                    }
                    notifyDataSetChanged();



            }
        });

        if(holder.selectBtn.isChecked() != radioStateList.get(position))
            holder.selectBtn.setChecked(!holder.selectBtn.isChecked());

        return convertView;
    }

    public class viewHolder {
        public int index;
        public TextView nameTxt;
        public RadioButton selectBtn;
    }
}
