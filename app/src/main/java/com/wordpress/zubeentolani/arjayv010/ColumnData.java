package com.wordpress.zubeentolani.arjayv010;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zeekhuge on 22/11/15.
 */
public class ColumnData extends ArrayAdapter {

    final int resource;
    final Context context;
    ArrayList<String> strings = new ArrayList<String>();
//    String[] strings ;


    public ColumnData(Context context, int resource, ArrayList<String> arrstring) {
        super(context, -1, arrstring);
        this.context = context;
        this.resource = resource;
        this.strings = arrstring;
    }

    @Override
    public View getView(final int n, View view, ViewGroup viewGroup) {

        final View rowView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.resource, viewGroup, false);
        final TextView rwTextView = (TextView)rowView.findViewById(R.id.detail_list_view_rwTextView);
        final TextView deTextView = (TextView) rowView.findViewById(R.id.detail_list_view_rwDetailTextView);
        final ImageView imageView = (ImageView)rowView.findViewById(R.id.detail_list_view_rwImageView);


        rowView.setId(n);
        imageView.setImageResource(imageView.getContext().getResources().getIdentifier("_" + MainActivity.frameName[n].substring(0, 1).toLowerCase(), "drawable", imageView.getContext().getPackageName()));
        rwTextView.setText(MainActivity.frameName[n]);
        deTextView.setText(strings.get(n));

        return rowView;
    }
}
