package com.t6.babyvaccin.babyvaccin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.t6.babyvaccin.babyvaccin.model.BabyClass;

import java.util.ArrayList;

public class BabyAdapter extends ArrayAdapter<BabyClass> {

    ArrayList<BabyClass> babies;
    Context currentCtx;

    public BabyAdapter(Context context, ArrayList<BabyClass> babies){
        super(context, R.layout.activity_baby_listview_item);
        this.currentCtx = context;
        this.babies = babies;
    }

    @Override
    public int getCount() {
        return babies.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) currentCtx.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_baby_listview_item, parent, false);
            mViewHolder.babyname = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.babyname.setText(babies.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView babyname;
    }

}
