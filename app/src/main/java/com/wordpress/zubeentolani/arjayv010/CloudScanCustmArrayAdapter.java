package com.wordpress.zubeentolani.arjayv010;

import android.content.Context;
import android.util.Log;
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
public class CloudScanCustmArrayAdapter extends ArrayAdapter{

    final int resource;
    final Context context;
    ArrayList<String> strings = new ArrayList<String>();
//    String[] strings ;


    public CloudScanCustmArrayAdapter(Context context, int resource, ArrayList<String> arrstring){
        super(context, -1, arrstring);
        this.context = context;
        this.resource = resource;
        this.strings = arrstring;
    }



    @Override
    public View getView(final int n, View view, ViewGroup viewGroup) {

//        Log.i("AlertZeek","Inside getView of customArrayAdapter");
        final ListView[] lstVw = new ListView[1];
        final View rowView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.resource, viewGroup, false);
        final LinearLayout linearLayout = (LinearLayout) rowView.findViewById(R.id.mp3_list_view_topLinearLay);
        final TextView rwTextView = (TextView) rowView.findViewById(R.id.mp3_list_view_rwTextView);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.mp3_list_view_rwImageView);
        final Button button = (Button) rowView.findViewById(R.id.mp3_list_view_rwButton);
        button.setLayoutParams(new LinearLayout.LayoutParams(0,0));

        rowView.setId(n);
        imageView.setImageResource(imageView.getContext().getResources().getIdentifier("_" + strings.get(n).substring(0, 1).toLowerCase(), "drawable", imageView.getContext().getPackageName()));
        rwTextView.setText(strings.get(n));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.i("AlertZeek", String.format("inside onClick of customAdapter id=%d ", button.getId()));
            }
        });

        return rowView;
    }
}
