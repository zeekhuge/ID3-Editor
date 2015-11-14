package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static android.graphics.Color.parseColor;


public class MainActivity extends Activity {

//    TableLayout mainTable ;
//    ImageView imVw;
//    TextView txView;
//    Button btn;

    public static FrameLayout mainframeLayout;
    public static Context context;
    public static ListView mainListView ;
    public static ArrayList<String> str = new ArrayList<String>() ;
    public static String[] noString = {"str1","srt2","str3","str4","str5","str6","srt7","str8","str9","str10","str12","srt13","str14","str16","str17","str18","srt19","str20","str21","str22","str23","srt24","str25","str26","str27","str28","srt29","str30","str31","str32"};
    public static File[] files;
    public static customArrayAdapter arrAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Log.i("Function", String.format("inside onCreate of MainActivity- id=%d",findViewById(R.id.Main_Linear_Layout).getId()));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        mainListView = (ListView) findViewById(R.id.Main_List_View);
        arrAdapter = new customArrayAdapter(context, R.layout.mp3_list_view, str,0);
        mainListView.setAdapter(arrAdapter);


        
















        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("AlertZeek", "start");



                File dir = new File(String.format("%s", Environment.getExternalStorageDirectory().getPath().toString()));
                if (dir.exists() && dir.isDirectory()){
                    Log.i("AlertZeek","inside if");



                    files = dir.listFiles(new FilenameFilter(){
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.contains(".mp3")){
                                Log.i("AlertZeek","Found file");
                                return true;
                            }
                            return false;
                        }
                    });
                }else{

                    Log.i("AlertZeek","inside else");
                }
//
//                int i = 0;
                for (File file : files){

                    str.add(file.getName());
                }
//                str.add(noString[0]) ;
//                str.add(noString[1]) ;
                Log.i("AlertZeek", String.format("getting name for %d files", str.size()));
                if (arrAdapter == null){
                    Log.i("AlertZeek", "F**");
                }

                if (arrAdapter == null){
                    Log.i("AlertZeek", "WTF");
                }
                mainListView.post(new Runnable() {
                    @Override
                    public void run() {
                        mainListView.deferNotifyDataSetChanged();
                    }
                });
                Log.i("AlertZeek", "made");
            }
        }).start();





        Log.i("Function", "Inside onCreate of MainActivity - Array created");
        Log.i("Function", String.format("inside onCreate of MainActivity- id=%d",findViewById(R.id.Main_Frame_Layout).getId()));

        mainframeLayout = (FrameLayout) findViewById(R.id.Main_Frame_Layout);

//        str.add(noString[0]) ;
//        str.add(noString[1]) ;




        Log.i("Function", "Inside onCreate of MainActivity - Adapter");
//        mainListView.setAdapter(adap);

//        mainTable = (TableLayout)findViewById(R.id.tbLay);
//        TableRow tblRw = (TableRow) tb.getChildAt(0);
//        TextView txtView = (TextView) tblRw.getChildAt(1);
//        txtView.setText("Changed");


    }

    public static void buttonPressed_loadDetailView(View rowView){
        Log.i("Function", "inside buttonPressed of MainActivity");
        mainListView.setEnabled(false);
        mainListView.setVisibility(View.INVISIBLE);
        View v = ((LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detail_view,null);

        mainframeLayout.addView(v);
        v.setX(rowView.getX());
        v.setY(rowView.getY());

        Animation DetailViewAnim = null;
        Animation DetailListAnim = new AnimationUtils().loadAnimation(context,R.anim.show_details_anim);
        ListView listView = (ListView) v.findViewById(R.id.detail_view_rwDetailListView);

        DetailViewAnim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.RELATIVE_TO_PARENT,0.0f,
                Animation.ABSOLUTE,rowView.getY(),
                Animation.RELATIVE_TO_PARENT,0.0f);
        DetailViewAnim.setDuration(500);
        DetailViewAnim.setFillAfter(true);

        v.setY(0)   ;
        listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,str);
        listView.setAdapter(arrayAdapter);
        listView.setVisibility(View.VISIBLE);

        v.startAnimation(DetailViewAnim);
        listView.startAnimation(DetailListAnim);

    }


//    void openUpDetails (View v){
//
//        Log.i("Function", "Inside openDetails of MainActivity");
//
//        TextView txtView = (TextView) mainTable.getChildAt((v.getId() * 3)+1);
//
//        if (txtView.getVisibility() == View.VISIBLE){
//            txtView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 0));
//            txtView.setVisibility(View.INVISIBLE);
//        }
//        else {
//            txtView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//            txtView.setVisibility(View.VISIBLE);
//        }
//
//    }




    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}




////            imVw = (ImageView) findViewById(R.id.rwImageView);
////            txView = (TextView) findViewById(R.id.rwTextView);
////            btn = (Button) findViewById(R.id.rwButton);
//
//        mainTable.setBackgroundColor(parseColor("white"));
//
//        for(int i=0; i<15; i++){
//
//            TableRow mainTableRow = new TableRow(this);
//            ImageView imVw = new ImageView(this);
//            TextView txView = new TextView(this);
//            Button btn = new Button(this);
//
//            mainTableRow.addView(findViewById(R.id.viewForRw));
////            ImageView imVw = (ImageView) findViewById(R.id.rwImageView);
////            TextView tx3 = (TextView) findViewById(R.id.rwTextView);
////            View vw = new View(this);
////            Button bt = (Button) findViewById(R.id.rwButton);
//
//
////            TableRow tbrew = new TableRow(this);
////            tbrw = tbrw_;
//
////            mainTableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
////            mainTableRow.setBackgroundColor(parseColor("white"));
//
//
//            txView.setText(str[i]);
////            txView.setLayoutParams(((TextView)findViewById(R.id.rwTextView)).getLayoutParams());
////            txView.setTextSize(((TextView)findViewById(R.id.rwTextView)).getTextSize());
////
////            imVw.setBackgroundColor(parseColor("black"));
//            imVw.setImageResource(R.drawable.character_a);
////            imVw.setScaleType(((ImageView) findViewById(R.id.rwImageView)).getScaleType());
////            imVw.setLayoutParams(findViewById(R.id.rwImageView).getLayoutParams());
////            imVw.setLayoutParams(new TableRow.MarginLayoutParams(5, 5));
////
////
////            vw.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
////            vw.setBackgroundColor(parseColor("black"));
////            vw.setPadding(20, 20, 20, 20);
////
////            btn.setText("butt");
////            btn.setLayoutParams(findViewById(R.id.rwButton).getLayoutParams());
////            btn.setBackgroundColor(findViewById(R.id.rwButton).getSolidColor());
//            btn.setId(i);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    openUpDetails(v);
//                }
//            });
//
//
////            mainTableRow.addView(imVw);
////            mainTableRow.addView(txView);
////            mainTableRow.addView(btn);
//            mainTable.addView(mainTableRow);
//
