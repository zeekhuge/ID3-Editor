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
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Failed to analyse overrides
 */




public class CloudScanActivity extends Activity implements DialogBox.DialogBoxListner {
    public static ArrayList<String> fileNameList;
    public static ArrayList<Integer> selectedList= new ArrayList<Integer>();
    public int objectId;
    public String fileNametoWrite;

    ArrayList<String> columnDataList = new ArrayList<String>();
    ListView mainListView,secondListView;
    Menu mainMenu;

    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);

        Log.i("AlertZeekCloudScan", "inside onCreate");

        mainListView = (ListView)this.findViewById(R.id.Main_List_View);
        CloudScanCustmArrayAdapter cloudScanCustmArrayAdapter = new CloudScanCustmArrayAdapter(this, R.layout.mp3_list_view_dash, fileNameList);
        mainListView.setAdapter(cloudScanCustmArrayAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                if (selectedList.size() > 0) {
                    Log.i("AlertZeekCloudScan", "Clicked at pos = " + n);
                    if (view.getTag() != Boolean.valueOf(true)) {
                        selectedList.add(Integer.valueOf(n));
                        view.setBackgroundColor(Color.parseColor("grey"));
                        view.setTag(true);
                    } else {
                        selectedList.remove((Integer) n);
                        view.setBackgroundColor(Color.parseColor("white"));
                        view.setTag(false);
                        if (selectedList.size() == 1) {
                            selectedList.clear();
                        }
                    }
                } else {
                    try {
                        readFileColumnData(adapterView.getItemAtPosition(n).toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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

        Log.i("AlertZeekCloudScan", "inside onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.cloud_activity_menu, menu);
        mainMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        Log.i("AlertZeekCloudScan", "inside onMenuItemSelected");

        switch (item.getItemId()) {

            case R.id.item_refresh:
                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
                if (mainListView.getVisibility() == View.VISIBLE){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<ParseUser> gotData = null;
                            ParseQuery<ParseUser> query = ParseQuery.getQuery("ID3tags");
                            query.selectKeys(Arrays.asList("FileName"));
                            query.orderByAscending("FileName");
                            try {
                                gotData = query.find();
                            } catch (ParseException e) {
                                e.printStackTrace();
                                handleParseExceptions(e.getCode());
                            }
                            Log.i("AlertZeek", "Returned from query");
                            final ArrayList<String> arrayList = new  ArrayList<String>();
                            arrayList.clear();
                            for (ParseObject obj:gotData){
                                arrayList.add(obj.getString("FileName"));
                            }

                            CloudScanActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fileNameList.clear();
                                    fileNameList = (ArrayList<String>) arrayList.clone();
                                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                                    arrayList.clear();
                                }
                            });
                        }
                    }).start();

                }else{

                    try {
                        readFileColumnData(fileNametoWrite);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        handleParseExceptions(e.getCode());
                    }
                    ((ArrayAdapter)secondListView.getAdapter()).notifyDataSetChanged();

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
                objectId = -1;

                Log.i("AlertZeekCloudScan", "Calling DialogBox");

                DialogBox frag = new DialogBox();
                frag.setValues("Enter file name", "", -1);
                frag.show(CloudScanActivity.this.getFragmentManager(), "ManagerZeek");

            break;

            case R.id.item_delete:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }
            break;

            case R.id.item_commitChanges:

                writeFileColumnData((ArrayList<String>) columnDataList.clone(),fileNametoWrite);
                item.setEnabled(false);
            break;

        }

        return super.onMenuItemSelected(featureId, item);
    }


    public void readFileColumnData(String fileName) throws ParseException {
        Log.i("AlertZeekCloudScan","Inside updateFileColumnData to search for "+ fileName);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ID3tags");
        query.whereEqualTo("FileName", fileName);
        ParseObject obj = query.getFirst();
        Log.i("AlertZeek", "Returned from query");
        final ArrayList<String> arrayList = new  ArrayList<String>();
        arrayList.clear();
        objectId = obj.getInt("objectId");
        for (String str: MainActivity.supportedID3Frames) {
            arrayList.add(obj.getString(str));
        }
        columnDataList.clear();
        columnDataList = (ArrayList<String>) arrayList.clone();
    }


    public void openUpDetails (final View rowView){

        Log.i("AlertZeekCloudScan", "inside openUpDetails");

        mainMenu.findItem(R.id.item_commitChanges).setVisible(true);
        mainMenu.findItem(R.id.item_cancel).setVisible(false);
        mainMenu.findItem(R.id.item_add).setVisible(false);
        mainMenu.findItem(R.id.item_delete).setVisible(false);

        mainListView.setEnabled(false);

        String st;
        final View v = ((LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detail_view_dash, null);
        TextView tx = (TextView) v.findViewById(R.id.detail_view_rwTextView);
        ImageView im = (ImageView) v.findViewById(R.id.detail_view_rwImageView);
        final ListView listView = (ListView) v.findViewById(R.id.detail_view_rwDetailListView);
        final FrameLayout mainframeLayout = (FrameLayout) findViewById(R.id.Main_Frame_Layout);

        secondListView = listView;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AlertZeek", "Detail list click postition =" + position);
//                callDialogBox(position,1);
            }
        });


        v.setOnClickListener(new View.OnClickListener() {
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
                        mainMenu.findItem(R.id.item_commitChanges).setVisible(false);
                        mainMenu.findItem(R.id.item_cancel).setVisible(true);
                        mainMenu.findItem(R.id.item_add).setVisible(true);
                        mainMenu.findItem(R.id.item_delete).setVisible(true);
                        columnDataList.clear();
                    }
                }, 500);
            }
        });


        v.setX(rowView.getX());
        v.setY(0);
        st = ((TextView) rowView.findViewById(R.id.mp3_list_view_rwTextView)).getText().toString();
        tx.setText(st);
        fileNametoWrite = st;
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
        if (columnDataList.size() == 0){
            for (int i=0; i < MainActivity.supportedID3Frames.length;i++ ){
                columnDataList.add("");
            }
        }
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

    public void writeFileColumnData(ArrayList<String> toWrite, String fileName){

        Log.i("AlertZeekCloudScan", "inside writeFileColumnData");

            ParseObject id3Tags = new ParseObject("ID3tags");
            for (int i = 0; i < MainActivity.supportedID3Frames.length; i++) {
                id3Tags.put(MainActivity.supportedID3Frames[i], toWrite.get(i));
            }
            if (objectId == -1) {
                id3Tags.put("FileName", fileName);
            }else{
                id3Tags.put("objectId",objectId);
            }
            id3Tags.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e ==null) {
                        Toast.makeText(CloudScanActivity.this, "Data saved on cloud", Toast.LENGTH_SHORT).show();
                    }else{
                        handleParseExceptions(e.getCode());
                    }
                }
            });



    }

    public void handleParseExceptions(int code){

        Log.i("AlertZeekCloudScan", "inside handleParseExceptions");

        switch (code){
            case ParseException.CONNECTION_FAILED:
                Toast.makeText(CloudScanActivity.this,"Connection failed, check your internet connection",Toast.LENGTH_LONG).show();
            break;

            case ParseException.EXCEEDED_QUOTA:
            Toast.makeText(CloudScanActivity.this,"Couldn't connect, application quota exceeded",Toast.LENGTH_LONG).show();
            break;

            case ParseException.INTERNAL_SERVER_ERROR:
            Toast.makeText(CloudScanActivity.this,"Connection failed, internal server error",Toast.LENGTH_LONG).show();
            break;

            case ParseException.TIMEOUT:
            Toast.makeText(CloudScanActivity.this,"Connection failed, timed out",Toast.LENGTH_LONG).show();
            break;
        }
    }

    @Override
    public void onDialogSaveClick(int position, String textEntered) {

        Log.i("AlertZeekCloudScan", "inside onDialogSaveClick");

        if (textEntered == null || textEntered.compareTo("") == 0){
            Toast.makeText(CloudScanActivity.this,"No text was entered",Toast.LENGTH_SHORT).show();
        }else {
            if (position == -1){
                fileNametoWrite = textEntered;
                fileNameList.add(textEntered);
                Collections.sort(fileNameList);
                ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();

                openUpDetails(mainListView.getChildAt(fileNameList.indexOf(fileNameList)));
            }
        }
    }

    @Override
    public void onDialogCancelClick() {

    }
}

