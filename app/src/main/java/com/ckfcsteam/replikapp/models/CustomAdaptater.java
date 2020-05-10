package com.ckfcsteam.replikapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ckfcsteam.replikapp.R;

import java.util.ArrayList;

public class CustomAdaptater extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataModel;
    Context context;

    private static class ViewHolder {
        ImageView lvl_icon;
        TextView lvl_number;
        TextView lvl_desc;
    }

    public CustomAdaptater(ArrayList<DataModel> dataModel, Context context) {
        super(context, R.layout.level_cell, dataModel);
        this.dataModel = dataModel;
        this.context = context;

    }

    @Override
    public void onClick(View v) {}

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        DataModel dataModel = getItem(position);
        View cellView = convertView;
        if(cellView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cellView = inflater.inflate(R.layout.level_cell, parent, false);
        }

        ImageView lvl_icon = (ImageView) cellView.findViewById(R.id.lvl_icon);
        TextView lvl_number = (TextView) cellView.findViewById(R.id.lvl_number);
        TextView lvl_desc = (TextView) cellView.findViewById(R.id.lvl_desc);
        LinearLayout cell = (LinearLayout) cellView.findViewById(R.id.cell);

        lvl_icon.setImageResource(dataModel.getImg_ressource());
        lvl_number.setText(dataModel.getLvl_number());
        lvl_desc.setText(dataModel.getLvl_desc());

        return cellView;
    }
}
