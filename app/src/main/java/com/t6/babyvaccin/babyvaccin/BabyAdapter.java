package com.t6.babyvaccin.babyvaccin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BabyAdapter extends ArrayAdapter<String> {

    String[] babynames;
    Context currentCtx;

    public BabyAdapter(Context context, String[] babynames){
        super(context, R.layout.activity_baby_listview_item);
        this.currentCtx = context;
        this.babynames = babynames;
    }

    @Override
    public int getCount() {
        return babynames.length;
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
        mViewHolder.babyname.setText(babynames[position]);

        return convertView;
    }

    static class ViewHolder {
        TextView babyname;
    }

}
