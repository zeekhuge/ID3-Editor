package com.wordpress.zubeentolani.arjayv010;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by zeekhuge on 22/11/15.
 */
public class ColumnData extends ArrayAdapter {

    final int resource;
    final Context context;
    ArrayList<String[]> strings = new ArrayList<String[]>();
//    String[] strings ;


    public customArrayAdapter(Context context, int resource, ArrayList<String[]> arrstring ) {
        super(context, -1, arrstring);
        this.context = context;
        this.resource = resource;
        this.strings = arrstring;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
