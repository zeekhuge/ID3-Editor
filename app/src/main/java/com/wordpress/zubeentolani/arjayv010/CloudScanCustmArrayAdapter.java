package com.wordpress.zubeentolani.arjayv010;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CloudScanCustmArrayAdapter
        extends ArrayAdapter {
    final Context context;
    final int resource;
    ArrayList<String> strings = new ArrayList();

    public CloudScanCustmArrayAdapter(Context context, int n, ArrayList<String> arrayList) {
        super(context, -1, arrayList);
        this.context = context;
        this.resource = n;
        this.strings = arrayList;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {

        View view2 = ((LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.resource, viewGroup, false);
        LinearLayout linearLayout = (LinearLayout)view2.findViewById(R.id.mp3_list_view_topLinearLay);
        TextView textView = (TextView)view2.findViewById(R.id.mp3_list_view_rwTextView);
        ImageView imageView = (ImageView)view2.findViewById(R.id.mp3_list_view_rwImageView);

        view2.setId(n);
        imageView.setImageResource(imageView.getContext().getResources().getIdentifier("_" + ((String)this.strings.get(n)).substring(0, 1).toLowerCase(), "drawable", imageView.getContext().getPackageName()));

        textView.setText((CharSequence)this.strings.get(n));

        return view2;
    }
}

