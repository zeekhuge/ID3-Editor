package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * Failed to analyse overrides
 */




public class CloudScanActivity extends Activity {
    public static ArrayList<String> fileNameList;
    public static ArrayList<Integer> selectedList= new ArrayList<Integer>();
    ArrayList<String> columnDataList = new ArrayList<String>();
    ListView mainListView;
    Menu mainMenu;

    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);

        mainListView = (ListView)this.findViewById(R.id.Main_List_View);
        CloudScanCustmArrayAdapter cloudScanCustmArrayAdapter = new CloudScanCustmArrayAdapter(this, R.layout.mp3_list_view, fileNameList);
        mainListView.setAdapter(cloudScanCustmArrayAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                if (selectedList.size() > 0) {
                    Log.i("AlertZeekCloudScan", "Clicked at pos = " + n );
                    if (view.getTag() != Boolean.valueOf(true)) {
                         selectedList.add(Integer.valueOf(n));
                         view.setBackgroundColor(Color.parseColor("grey"));
                         view.setTag(true);
                    } else {
                        selectedList.remove((Integer) n);
                        view.setBackgroundColor(Color.parseColor("white"));
                        view.setTag(false);
                        if(selectedList.size() == 1){
                            selectedList.clear();
                        }
                    }
                }else{
                    openUpDetails(view);
                }
            }
        });

        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                Log.i("AlertZeekCloudScan", "LongClicked at " + adapterView.getItemAtPosition(n).toString());
                if (selectedList.size() <= 0) {
                    selectedList.add(Integer.valueOf(-1));
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cloud_activity_menu, menu);
        mainMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_refresh:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
            break;

            case R.id.item_cancel:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter) mainListView.getAdapter()).notifyDataSetChanged();
                    mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
            break;

            case R.id.item_add:



                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
            break;

            case R.id.item_delete:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
            break;

        }

        return super.onMenuItemSelected(featureId, item);
    }


    public void openUpDetails (final View rowView){

        Log.i("AlertZeekCloudScna", "inside openUpDetails");

        mainListView.setEnabled(false);

        String st;
        final View v = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detail_view, null);
        TextView tx = (TextView) v.findViewById(R.id.detail_view_rwTextView);
        ImageView im = (ImageView) v.findViewById(R.id.detail_view_rwImageView);
        Button btn = (Button) v.findViewById(R.id.detail_view_rwButton);
        final ListView listView = (ListView) v.findViewById(R.id.detail_view_rwDetailListView);
        final FrameLayout mainframeLayout = (FrameLayout) findViewById(R.id.Main_Frame_Layout);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AlertZeek", "Detail list click postition =" + position);
//                callDialogBox(position,1);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btV) {

                Log.i("AlertZeek", "inside onClick to reach back to mp3List");

                mainListView.setVisibility(View.VISIBLE);

                Animation DetailListAnim = new AnimationUtils().loadAnimation(CloudScanActivity.this, R.anim.remove_details_anim);
                Animation DetailViewAnim = new TranslateAnimation(
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.RELATIVE_TO_PARENT, 0.0f,
                        Animation.ABSOLUTE, -rowView.getY(),
                        Animation.RELATIVE_TO_PARENT, 0.0f);
                DetailViewAnim.setDuration(500);
                DetailViewAnim.setFillAfter(true);

                v.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                v.setY(rowView.getY());
                v.startAnimation(DetailViewAnim);
                listView.startAnimation(DetailListAnim);
                android.os.Handler hand = new android.os.Handler();
                hand.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mainframeLayout.removeView(v);
                        mainListView.setEnabled(true);
                        columnDataList.clear();
                    }
                }, 500);
            }
        });


        v.setX(rowView.getX());
        v.setY(0);
        st = ((TextView) rowView.findViewById(R.id.mp3_list_view_rwTextView)).getText().toString();
        tx.setText(st);
        im.setImageResource(im.getContext().getResources().getIdentifier("_" + st.substring(0, 1).toLowerCase(), "drawable", im.getContext().getPackageName()));



        mainframeLayout.addView(v);

        Animation DetailViewAnim;
        Animation DetailListAnim = new AnimationUtils().loadAnimation(this, R.anim.show_details_anim);


        DetailViewAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.ABSOLUTE, rowView.getY(),
                Animation.RELATIVE_TO_PARENT, 0.0f);
        DetailViewAnim.setDuration(500);
        DetailViewAnim.setFillAfter(true);

        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,noString);
        columnDataList.add("hey");
        ColumnData arrayAdapter = new ColumnData(this, R.layout.detail_list_view, columnDataList);
        listView.setAdapter(arrayAdapter);
        listView.setVisibility(View.VISIBLE);

        v.startAnimation(DetailViewAnim);
        listView.startAnimation(DetailListAnim);
        android.os.Handler hand = new android.os.Handler();

        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainListView.setVisibility(View.INVISIBLE);

            }
        }, 500);




    }

}

