package com.t6.babyvaccin.babyvaccin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t6.babyvaccin.babyvaccin.model.IAClass;

import java.util.ArrayList;

public class InjectLocationAdapter extends ArrayAdapter<IAClass> {
    ArrayList<IAClass> ias;
    Context currentCtx;

    public InjectLocationAdapter(Context context, ArrayList<IAClass> ias){
        super(context, R.layout.activity_baby_listview_item);
        this.currentCtx = context;
        this.ias = ias;
    }

    @Override
    public int getCount() {
        return ias.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InjectLocationAdapter.ViewHolder mViewHolder = new InjectLocationAdapter.ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) currentCtx.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_location_listview_item, parent, false);
            mViewHolder.ianame = (TextView) convertView.findViewById(R.id.IATextview);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (InjectLocationAdapter.ViewHolder) convertView.getTag();
        }
        mViewHolder.ianame.setText(ias.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView ianame;
    }

}
