package com.t6.babyvaccin.babyvaccin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.t6.babyvaccin.babyvaccin.model.VaccinClass;

import java.util.ArrayList;

public class VaccineAdapter extends ArrayAdapter<VaccinClass> {

    ArrayList<VaccinClass> vaccines;
    Context currentCtx;

    public VaccineAdapter(Context context, ArrayList<VaccinClass> vaccines){
        super(context, R.layout.activity_baby_listview_item);
        this.currentCtx = context;
        this.vaccines = vaccines;
    }

    @Override
    public int getCount() {
        return vaccines.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) currentCtx.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_vaccine_listview_item, parent, false);
            mViewHolder.vaccinename = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.vaccinename.setText(vaccines.get(position).getName());

        return convertView;
    }

    static class ViewHolder {
        TextView vaccinename;
    }

}
