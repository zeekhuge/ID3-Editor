package com.wordpress.zubeentolani.arjayv010;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.wordpress.zubeentolani.arjayv010.CloudScanCustmArrayAdapter;
import java.util.ArrayList;

/*
 * Failed to analyse overrides
 */
public class CloudScanActivity extends Activity {
    public static ArrayList<String> fileNameList;
    public static ArrayList<Integer> selectedList;
    public boolean Selectionflag = false;
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
                    Log.i("AlertZeekCloudScan", "Clicked at pos = " + n + " bool= " + view.isSelected());
                    if (view.getTag() != Boolean.valueOf(true)) {
                        if (CloudScanActivity.this.Selectionflag) {
                            selectedList.add(n);
                            view.setBackgroundColor(Color.parseColor("grey"));
                            view.setTag(true);
                        }
                    } else {
                        selectedList.remove((Integer) n);
                        view.setBackgroundColor(Color.parseColor("white"));
                        view.setTag(false);
                    }
                }else{

                }
            }
        });

        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                Log.i("AlertZeekCloudScan", "LongClicked at " + adapterView.getItemAtPosition(n).toString());

                selectedList.add(n);
                view.setBackgroundColor(Color.parseColor("grey"));
                view.setTag(true);
                CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(false);
                CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(true);

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
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }

            break;

            case R.id.item_cancel:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter) mainListView.getAdapter()).notifyDataSetChanged();
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(false);
                }

            break;

            case R.id.item_add:

                if (selectedList.size() > 0) {
                    selectedList.clear();
                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_refresh).setVisible(true);
                    CloudScanActivity.this.mainMenu.findItem(R.id.item_cancel).setVisible(false);
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

}

