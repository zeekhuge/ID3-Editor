
package com.wordpress.zubeentolani.arjayv010;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
public class customArrayAdapter extends ArrayAdapter {

    final int resource;
    final Context context;
//    String[] strings = new ArrayList<String>();
    String[] strings ;
    final int isSelected ;

    public customArrayAdapter(Context context, int resource, String[] arrstring, int isSelected1) {
        super(context, -1, arrstring);
        this.context = context;
        this.resource = resource;
        this.strings = arrstring;
        this.isSelected = isSelected1;
    }



    @Override
    public View getView(final int n, View view, ViewGroup viewGroup) {

        final ListView[] lstVw = new ListView[1];
        final View rowView = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.resource, viewGroup, false);
        final LinearLayout linearLayout = (LinearLayout)rowView.findViewById(R.id.mp3_list_view_topLinearLay);
        final TextView rwTextView = (TextView)rowView.findViewById(R.id.mp3_list_view_rwTextView);
        final ImageView imageView = (ImageView)rowView.findViewById(R.id.mp3_list_view_rwImageView);
        final Button button = (Button)rowView.findViewById(R.id.mp3_list_view_rwButton);

        rowView.setId(n);
        imageView.setImageResource(imageView.getContext().getResources().getIdentifier("_"+strings[n].substring(0, 1).toLowerCase(), "drawable", imageView.getContext().getPackageName()));
        rwTextView.setText(strings[n]);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Log.i("AlertZeek", String.format("inside onClick of customAdapter id=%d ", button.getId()));


//                rowView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                ((LinearLayout)lstVw[0].getParent()).addView(rowView);

//                FrameLayout fmLay = (FrameLayout) rowView.getParent().getParent();
//                Log.i("Function", String.format("id=%d", fmLay.getId()));
//                lstVw[0].removeView(rowView);
//                fmLay.addView(rowView);
                MainActivity.buttonPressed_loadDetailView(rowView);


//                    lstVw[0] = (ListView) rowView.getParent();
//                    lstVw[0].smoothScrollByOffset(n - lstVw[0].getChildAt(0).getId());
//                    String[] tempStr = {strings[n]};
////                    button.setHeight(((LinearLayout) lstVw[0].getParent()).getHeight());
//                    Animation anim = AnimationUtils.loadAnimation(context,R.anim.show_details_anim);
//                    rwDetailTextView.startAnimation(anim);
////
//
//                    final customArrayAdapter tempAdap = new customArrayAdapter(context, resource, tempStr, 1);
//
//                    rowView.setLayoutParams(new LinearLayout.LayoutParams(lstVw[0].getWidth(),lstVw[0].getHeight()));
//                    lstVw[0].setLayoutParams(new LinearLayout.LayoutParams(0, 0));

//                    final android.os.Handler handler = new android.os.Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            lstVw[0].setAdapter(tempAdap);
//                        }
//                    },1000);
//

//                    rwDetailTextView.setVisibility(View.VISIBLE);
//                    rwDetailTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));


//                    lstVw.scrollBy(0, (int) rowView.getY());
//                    lstVw.scrollListBy(n);

//                    lstVw.scrol
//                    rwDetailTextView.scrollTo(1, 1);
//                    rwDetailTextView.scrollBy(50, 50);
//                    linearLayout.scrollTo(1, 1);
//                    linearLayout.scrollBy(50, 50);


//                    lstVw[0].smoothScrollByOffset(n - lstVw[0].getChildAt(0).getId());

//                    String[] tempStr = {"str"};
//
//                    customArrayAdapter tempAdap = new customArrayAdapter(context, resource, tempStr,0);
//                    lstVw[0].setAdapter(tempAdap);
            }

        });

        return rowView;
    }

}

