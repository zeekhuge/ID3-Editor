/*
 * Decompiled with CFR 0_92.
 *
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.graphics.Color
 *  android.os.Bundle
 *  android.util.Log
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
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
import android.widget.ListAdapter;
import android.widget.ListView;
import com.wordpress.zubeentolani.arjayv010.CloudScanCustmArrayAdapter;
import java.util.ArrayList;

/*
 * Failed to analyse overrides
 */
public class CloudScanActivity
        extends Activity {
    public static ArrayList<String> fileNameList;
    public boolean Selectionflag = false;
    ListView mainListView;
    Menu mainMenu;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_main);
        this.mainListView = (ListView)this.findViewById(R.id.Main_List_View);
        CloudScanCustmArrayAdapter cloudScanCustmArrayAdapter = new CloudScanCustmArrayAdapter((Context)this, 2130968581, fileNameList);
        this.mainListView.setAdapter((ListAdapter)cloudScanCustmArrayAdapter);
        this.mainListView.setOnItemClickListener((AdapterView.OnItemClickListener)new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Log.i((String)"AlertZeekCloudScan", (String)("Clicked at pos = " + n + " bool= " + view.isSelected()));
                if (view.getTag() != Boolean.valueOf((boolean)true)) {
                    Log.i((String)"AlertZeekCloudScan", (String)("Clicked at pos = " + n));
                    if (CloudScanActivity.this.Selectionflag) {
                        view.setBackgroundColor(Color.parseColor((String)"grey"));
                        view.setTag((Object)true);
                    }
                }
            }
        });
        this.mainListView.setOnItemLongClickListener((AdapterView.OnItemLongClickListener)new AdapterView.OnItemLongClickListener(){

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                Log.i((String)"AlertZeekCloudScan", (String)("LongClicked at " + adapterView.getItemAtPosition(n).toString()));
                CloudScanActivity.this.Selectionflag = true;
                view.setBackgroundColor(Color.parseColor((String)"grey"));
                view.setTag((Object)true);
                view.setSelected(true);
                CloudScanActivity.this.mainMenu.findItem(2131361816).setVisible(false);
                CloudScanActivity.this.mainMenu.findItem(2131361819).setVisible(true);
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(2131296256, menu);
        this.mainMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean onMenuItemSelected(int var1_1, MenuItem var2_2) {
        switch (var2_2.getItemId()) {
            case 2131361816: {
                if (this.Selectionflag == false) return super.onMenuItemSelected(var1_1, var2_2);
                this.mainMenu.findItem(2131361816).setVisible(true);
                this.mainMenu.findItem(2131361819).setVisible(false);
                this.Selectionflag = false;
                ** break;
            }
            case 2131361818: {
                if (this.Selectionflag == false) return super.onMenuItemSelected(var1_1, var2_2);
                this.mainMenu.findItem(2131361816).setVisible(true);
                this.mainMenu.findItem(2131361819).setVisible(false);
                this.Selectionflag = false;
                ** break;
            }
            case 2131361819: {
                if (this.Selectionflag == false) return super.onMenuItemSelected(var1_1, var2_2);
                this.mainMenu.findItem(2131361816).setVisible(true);
                this.mainMenu.findItem(2131361819).setVisible(false);
                this.Selectionflag = false;
            }
            lbl19: // 4 sources:
            default: {
                return super.onMenuItemSelected(var1_1, var2_2);
            }
            case 2131361817:
        }
        if (this.Selectionflag == false) return super.onMenuItemSelected(var1_1, var2_2);
        this.mainMenu.findItem(2131361816).setVisible(true);
        this.mainMenu.findItem(2131361819).setVisible(false);
        this.Selectionflag = false;
        return super.onMenuItemSelected(var1_1, var2_2);
    }

}

