package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.graphics.Color.parseColor;


public class MainActivity extends Activity {

//    TableLayout mainTable ;
//    ImageView imVw;
//    TextView txView;
//    Button btn;

    public ListView mainListView ;
    String[] str = {"str1","srt2","str3","str4","str5","str1","srt2","str3","str4","str5","str1","srt2","str3","str4","str5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("Function", "Inside onCreate of MainActivity");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customArrayAdapter arrAdapter = new customArrayAdapter(this, R.layout.explicit_views, str);
        ArrayAdapter <String> adap = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str);

        mainListView = (ListView) findViewById(R.id.Main_List_View);
        mainListView.setAdapter(arrAdapter);

//        mainListView.setAdapter(adap);

//        mainTable = (TableLayout)findViewById(R.id.tbLay);
//        TableRow tblRw = (TableRow) tb.getChildAt(0);
//        TextView txtView = (TextView) tblRw.getChildAt(1);
//        txtView.setText("Changed");

    }

    public void scrollListFunc ( ){
        mainListView.scrollBy(10,10);
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
