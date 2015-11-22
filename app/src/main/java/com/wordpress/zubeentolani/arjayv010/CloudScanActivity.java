package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by zeekhuge on 22/11/15.
 */
public class CloudScanActivity extends Activity {
    public static ArrayList<String> fileNameList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mainListView = (ListView) findViewById(R.id.Main_List_View);
        CloudScanCustmArrayAdapter arrayAdapter = new CloudScanCustmArrayAdapter(this, R.layout.mp3_list_view, fileNameList);
        mainListView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);


//        menu.findItem(R.id.item_more).setVisible(false);
//        menu.findItem(R.id.item_commitChanges).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }
}
