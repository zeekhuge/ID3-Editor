package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zeekhuge on 22/11/15.
 */

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        getActionBar().hide();

// Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "vPyZYz1qtehGF5faKHNwdrdGLnJcstEIsx5IdqHY", "Ocf2fhJkgZWpOCbVaIIIl6maCRSkjgoS4IHQkBKB");

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();


//        ParseObject id3Tags = new ParseObject("ID3tags");
//        for (String str : MainActivity.supportedID3Frames){
//            id3Tags.put(str,"");
//        }
//        id3Tags.put("FileName","file1");
//        id3Tags.put("FileAddress","");
//
//        id3Tags.saveInBackground();
//
//        ParseObject id3Tags1 = new ParseObject("ID3tags");
//        for (String str : MainActivity.supportedID3Frames){
//            id3Tags1.put(str,"");
//        }
//        id3Tags1.put("FileName","file2");
//        id3Tags1.put("FileAddress", "");
//        id3Tags1.saveInBackground();

//        ProgressBar


        AlphaAnimation animAlpha = new AlphaAnimation(0,1);
        animAlpha.setDuration(2000);

        ImageView im = (ImageView) findViewById(R.id.ImageView_Arjay);
        final TextView tx1 = (TextView) findViewById(R.id.TextView_local);
        final TextView tx2 = (TextView) findViewById(R.id.TextView_net);

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.first_activity_anims);

        im.startAnimation(animAlpha);
        tx1.startAnimation(anim);
        tx2.startAnimation(anim);

        final Intent intent_local = new Intent(this, MainActivity.class);
        final Intent intent_cloud = new Intent(this, CloudScanActivity.class);

        tx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_local);
            }
        });

        tx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
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
                        }
                        Log.i("AlertZeek", "Returned from query");
                        final ArrayList<String> arrayList = new  ArrayList<String>();
                        arrayList.clear();
                        for (ParseObject obj:gotData){
                            arrayList.add(obj.getString("FileName"));
                        }

                        FirstActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CloudScanActivity.fileNameList = (ArrayList<String>) arrayList.clone();
                                arrayList.clear();
                                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                                startActivity(intent_cloud);
                            }
                        });
                    }
                }).start();
            }
        });

        Handler hand = new Handler();
        hand.postDelayed(new Runnable() {
            @Override
            public void run() {
                tx1.setEnabled(true);
                tx2.setEnabled(true);
            }
        }, 2000);

    }

}
