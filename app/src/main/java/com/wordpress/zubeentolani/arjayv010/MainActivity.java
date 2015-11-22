package com.wordpress.zubeentolani.arjayv010;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

class frameObject implements Comparable{
    public String frameID;
    public String frameName;
    public long framePosition;
    public String frameDetail;
    public boolean frameDataChanged = false;


    public frameObject(String frameID, String frameName, long framePos, String frameDet){
        this.frameID = frameID;
        this.frameName = frameName;
        this.framePosition = framePos;
        this.frameDetail = frameDet ;

    }

    @Override
    public int compareTo(Object another) {
        int ret = (int) (this.framePosition - ((frameObject)another).framePosition);
        return ret;
    }

    public byte[] parsedDataToWrite(int encoding){
        if (this.frameID.compareTo("TXXX") == 0){

        }
        else if (this.frameID.charAt(0) == 'T'){
            if (encoding == 0){
                return frameDetail.getBytes();
            }else{
                byte [] read = frameDetail.getBytes();
                byte [] ret = new byte[read.length * 2];
                for (int i =0; i < read.length; i++){
                    ret[i*2] = read[i];
                    ret[i*2 + 1] = 0;
                }
                return ret;
            }
        }
        return null;
    }
}

public class MainActivity extends Activity implements DialogBox.DialogBoxListner {

    public boolean frameDataChangedFlag = false;

    public static String fileAddress;
    public static Menu mainMenu;
    public static ActionBar actionBar;
    public static FrameLayout mainframeLayout;
    public static Context context;
    public static ListView mainListView ;
    public static ListView listViewSecond;
    public static ArrayList<String[]> mp3SongsList = new ArrayList<String[]>() ;
    public static ArrayList<frameObject> tagList = new ArrayList<frameObject>() ;

    public static String[] supportedID3Frames = new String[]{
            "TALB",
            "TPE2",
            "COMR",
            "TCOM",
            "TIT1",
            "TPE3",
            "WCOP",
            "TCOP",
            "TDAT",
            "TOWN",
            "TEXT",
            "TPE1",
            "MCDI",
            "TOAL",
            "TOPE",
            "TOFN",
            "TOLY",
            "TORY",
            "WPAY",
            "PCNT",
            "POPM",
            "TPUB",
            "TRDA",
            "TSIZ",
            "TIT3",
            "TIME",
            "TIT2",
            "TYER",
            "TXXX"
    };

    public static String[] frameName = new String[]{
            "Album/Movie/Show title",
            "Band/orchestra/accompaniment",
            "Commercial frame",
            "Composer",
            "Content group description",
            "Conductor/performer refinement",
            "Copyright/Legal information",
            "Copyright message",
            "Date (Format DDMM)",
            "File owner/licensee",
            "Lyricist/Text writer",
            "Lead performer(s)/Soloist(s)",
            "Music CD identifier",
            "Original album/movie/show title",
            "Original artist(s)/performer(s)",
            "Original filename",
            "Original lyricist(s)/text writer(s)",
            "Original release year",
            "Payment",
            "Play counter",
            "Popularimeter",
            "Publisher",
            "Recording dates",
            "Size",
            "Subtitle/Description refinement",
            "Time (Format HHMM)",
            "Title/songname/content description",
            "Year",
            "Other data"
    };




    public static File[] files;
    public static customArrayAdapter arrAdapter = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("AlertZeek", "Inside onCreate of MainActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mainListView = (ListView) findViewById(R.id.Main_List_View);
        arrAdapter = new customArrayAdapter(context, R.layout.mp3_list_view, mp3SongsList);
//        arrAdapter = new customArrayAdapter(context, R.layout.mp3_list_view, noString,0);
        mainListView.setAdapter(arrAdapter);

        if (savedInstanceState == null)
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("AlertZeek", "Inside Thread to load mp3 files");

                final ArrayList<String[]> arrayList = scanForMp3();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mp3SongsList.addAll(arrayList);
                        ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                    }
                });
                Log.i("AlertZeek", "made");
            }
        }).start();

        Log.i("Function", "Inside onCreate of MainActivity - Array created");
        Log.i("Function", String.format("inside onCreate of MainActivity- id=%d", findViewById(R.id.Main_Frame_Layout).getId()));

        mainframeLayout = (FrameLayout) findViewById(R.id.Main_Frame_Layout);

//        str.add(new String[]{noString[0]," "}) ;
//        str.add(new String[]{noString[1]," "}) ;
//        mainListView.deferNotifyDataSetChanged();

        Log.i("Function", "Inside onCreate of MainActivity - Adapter");

//        mainListView.setAdapter(adap);

//        mainTable = (TableLayout)findViewById(R.id.tbLay);
//        TableRow tblRw = (TableRow) tb.getChildAt(0);
//        TextView txtView = (TextView) tblRw.getChildAt(1);
//        txtView.setText("Changed");

//        scanFor();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        mainMenu = menu;

        menu.findItem(R.id.item_more).setVisible(false);
        menu.findItem(R.id.item_commitChanges).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i("AlertZeek", "optionButton clicked id = " + item.getTitle());

        switch (item.getItemId()){
            case (R.id.item_refresh):
                Log.i("AlertZeek","refresh button clicked");
                if (mainListView.getVisibility() == View.VISIBLE){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("AlertZeek", "Inside Thread to load mp3 files");

                            final ArrayList<String[]> tempList= scanForMp3();


                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mp3SongsList.clear();
                                    mp3SongsList.addAll(tempList);
                                    ((ArrayAdapter)mainListView.getAdapter()).notifyDataSetChanged();
                                }
                            });
                            Log.i("AlertZeek", "made");
                        }
                    }).start();
                }else{

                }
            break;
            case (R.id.item_onlyExistingFrames):
                Log.i("AlertZeek","existing/all frames button clicked");
                if (item.getTitle().toString().compareTo("Show all frames") != 0 ) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < tagList.size(); i++) {
                                if (tagList.get(i).framePosition == -1) {
                                    tagList.remove(i);
                                    i--;
                                }
                            }
                            listViewSecond.post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ArrayAdapter) listViewSecond.getAdapter()).notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();
                    item.setTitle("Show all frames");
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int i;
                            for ( i = 0; i < tagList.size() ; i++) {
                                if (tagList.get(i).frameID.compareTo(supportedID3Frames[i]) != 0 ) {
                                    tagList.add(i, new frameObject(supportedID3Frames[i], frameName[i], -1, ""));
                                }
                            }
                            for (; i < frameName.length;i++){
                                tagList.add(i, new frameObject(supportedID3Frames[i], frameName[i], -1, ""));
                            }
                            listViewSecond.post(new Runnable() {
                                @Override
                                public void run() {
                                    ((ArrayAdapter) listViewSecond.getAdapter()).notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();
                    item.setTitle("Only existing frames");
                }
            break;
            case (R.id.item_commitChanges):
                Log.i("AlertZeek","commitChanges button clicked");

                item.setEnabled(false);

//                mainMenu.getItem(R.id.item_refresh).setEnabled(false);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("AlertZeek","thread started");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Writing changes...", Toast.LENGTH_SHORT).show();
                            }
                        });
                        try {
                            commitChangesToFile((ArrayList<frameObject>) tagList.clone());

                        } catch (IOException e) {
                            Log.i("AlertZeek","Exception in commitChangesToFile");
                            e.printStackTrace();
                        }
                        Log.i("AlertZeek","wrote changes to file");
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Changes committed", Toast.LENGTH_SHORT).show();
                                item.setEnabled(true);
//                                mainMenu.getItem(R.id.item_refresh).setEnabled(true);
                            }
                        });
                    }
                }).start();
            break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void commitChangesToFile(ArrayList<frameObject> toWrite) throws IOException {

        long wroteTill =0;
        long readOpFrameSize = 0;
        long writeOpFrameSize = 0;
        byte [] sizeAndFlagsBuffer = new byte[6];
        byte [] startPart = new byte[10];

        Log.i("AlertZeek","inside commitChangesToFiles toWrite length = " + toWrite.size());
        RandomAccessFile file = new RandomAccessFile(new File(fileAddress),"rw");
        RandomAccessFile writeFile = new RandomAccessFile(new File(getFilesDir(),"arjayTempFile.mp3"),"rw");
//        Log.i("AlertZeek","mileStone 0");
        for (int i = 0 ; i < toWrite.size(); i++) {
//            Log.i("AlertZeek","val of i  ="+i);
            if (toWrite.get(i).frameDataChanged != true) {
                toWrite.remove(i);
                i--;
            }
        }
//        Log.i("AlertZeek","mileStone1");
        Collections.sort(toWrite);

//        file.read(startPart);
//        writeFile.write(startPart);

        for (frameObject obj:toWrite){

            Log.i("AlertZeek", "Frame postion=" + obj.framePosition + " frameID=" + obj.frameID);


//            if (obj.framePosition == -1){
//                writeFile.write(obj.frameID.getBytes());
//            }
            for (;wroteTill <= obj.framePosition - 6  ; wroteTill++ ){
                writeFile.write(file.readByte());
            }
            writeFile.write(obj.frameID.getBytes());

            file.skipBytes(4);

            readOpFrameSize|= ((long)file.read());
            readOpFrameSize= readOpFrameSize<< 8;

            readOpFrameSize|= ((long)file.read());
            readOpFrameSize= readOpFrameSize<< 8;

            readOpFrameSize|= ((long)file.read());
            readOpFrameSize= readOpFrameSize<< 8;

            readOpFrameSize|= ((long)file.read());

            sizeAndFlagsBuffer[4] = (byte) file.read();
            sizeAndFlagsBuffer[5] = (byte) file.read();

            if (obj.frameID.compareTo("TXXX") == 0){

            }
            else if (obj.frameID.charAt(0) == 'T') {
                if (file.read() == 1) {
                    writeOpFrameSize = obj.parsedDataToWrite(1).length + 3;
                    sizeAndFlagsBuffer[0] = (byte) ((writeOpFrameSize & 0xff000000) >> 24);
                    sizeAndFlagsBuffer[1] = (byte) ((writeOpFrameSize & 0x00ff0000) >> 16);
                    sizeAndFlagsBuffer[2] = (byte) ((writeOpFrameSize & 0x0000ff00) >> 8);
                    sizeAndFlagsBuffer[3] = (byte) (writeOpFrameSize & 0x000000ff);
                    writeFile.write(sizeAndFlagsBuffer);
                    writeFile.write(1);
                    writeFile.write(file.read());
                    writeFile.write(file.read());
                    writeFile.write(obj.parsedDataToWrite(1));
                }else{
                    writeOpFrameSize = obj.parsedDataToWrite(0).length + 1;
                    sizeAndFlagsBuffer[0] = (byte) ((writeOpFrameSize & 0xff000000) >> 24);
                    sizeAndFlagsBuffer[1] = (byte) ((writeOpFrameSize & 0x00ff0000) >> 16);
                    sizeAndFlagsBuffer[2] = (byte) ((writeOpFrameSize & 0x0000ff00) >> 8);
                    sizeAndFlagsBuffer[3] = (byte) (writeOpFrameSize & 0x000000ff);
                    writeFile.write(sizeAndFlagsBuffer);
                    writeFile.write(0);
                    writeFile.write(obj.parsedDataToWrite(0));
                }
            }
            file.skipBytes((int) (readOpFrameSize-1));
            wroteTill += readOpFrameSize + 10;

        }

        Log.i("AlertZeek","Wrote all the frames");

        //Writing to temp file
        wroteTill += 700;
        byte[] buffer_here = new byte[700];
        for (;wroteTill <= file.length();wroteTill+=700){
            file.read(buffer_here);
            writeFile.write(buffer_here);
        }

        wroteTill-=700;
        for (;wroteTill <= file.length(); wroteTill++){
            writeFile.write(file.read());
        }


        //Starting to write to original file
        file.seek(0);
        writeFile.seek(0);

        wroteTill = 700;
        for (;wroteTill <= writeFile.length();wroteTill+=700){
            writeFile.read(buffer_here);
            file.write(buffer_here);
        }

        writeFile.close();
        file.close();

        wroteTill-=700;
        for (;wroteTill <= writeFile.length(); wroteTill++){
            file.write(writeFile.read());
        }


//                Log.i("AlertZeek", "Frame changed is = " + obj.frameID + " detail now = " + obj.frameDetail);
//                file.seek((int) obj.framePosition - 4);
//                byte [] buffer = new byte[4];
//                file.read(buffer);
//                int frameSize = 0;
//
//                frameSize |= ((long)file.read());
//                frameSize = frameSize << 8;
//
//                frameSize |= ((long)file.read());
//                frameSize = frameSize << 8;
//
//                frameSize |= ((long)file.read());
//                frameSize = frameSize << 8;
//
//                frameSize |= ((long)file.read());
//
//                Log.i("AlertZeek"," size " + frameSize);
    }

    public static void callDialogBox(int position, int option){
        Log.i("AlertZeek", "Calling DialogBox");
        DialogBox frag = new DialogBox();

        if (option != 0) { //it is normal TEXT frame
            frag.setValues(frameName[position], tagList.get(position).frameDetail, position);
        }
        else {
            frag.setValues(frameName[position], tagList.get(position).frameDetail, position,true);
        }
        frag.show(((Activity) context).getFragmentManager(), "ManagerZeek");
    }



    public ArrayList<String []> scanForMp3(){
        Log.i("AlertZeek","inside scanForMp3 in MainActivity");

        ArrayList<String[]> arr = new ArrayList<String[]>();
        Cursor list = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{ MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA},
                String.format("%s!=0", MediaStore.Audio.Media.IS_MUSIC),
                null,
                null);

        list.moveToFirst();
        while(!list.isAfterLast()) {
//            Log.i("ZeekMP3",String.format("Title=%s \n Data=%s",list.getString(0),list.getString(1)));
            arr.add(new String[]{list.getString(0), list.getString(1)});
            list.moveToNext();
//            Log.i("ZeekMP3", String.format("Title=%s \n Data=%s", arr.get(i)[0], arr.get(i)[1
        }

        list.close();
        return arr;
    }




    public static void buttonPressed_loadDetailView(final View rowView){

        Log.i("AlertZeek", "inside buttonPressed of MainActivity");

        tagList.clear();
        for (int i =0; i < frameName.length; i++) {
            tagList.add(new frameObject(supportedID3Frames[i],frameName[i],-1,null));
        }

        if (fileRead(mp3SongsList.get(rowView.getId())[1]) == true) {

            mainListView.setEnabled(false);

            String st;
            final View v = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.detail_view, null);
            TextView tx = (TextView) v.findViewById(R.id.detail_view_rwTextView);
            ImageView im = (ImageView) v.findViewById(R.id.detail_view_rwImageView);
            Button btn = (Button) v.findViewById(R.id.detail_view_rwButton);
            final ListView listView = (ListView) v.findViewById(R.id.detail_view_rwDetailListView);
            listViewSecond = listView;

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i("AlertZeek", "Detail list click postition =" + position);
                    if (tagList.get(position).frameID.compareTo("TXXX") == 0){
                        callDialogBox(position,0);
                    }else{
                        callDialogBox(position,1);
                    }

                }
            });


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View btV) {

                    Log.i("AlertZeek", "inside onClick to reach back to mp3List");

                    mainListView.setVisibility(View.VISIBLE);

                    Animation DetailListAnim = new AnimationUtils().loadAnimation(context, R.anim.remove_details_anim);
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
                            tagList.clear();

                            mainMenu.findItem(R.id.item_more).setVisible(false);
                            mainMenu.findItem(R.id.item_commitChanges).setVisible(false);
                            mainMenu.findItem(R.id.item_onlyExistingFrames).setTitle("Only existing frames");

                            tagList.clear();
                            for (int i =0; i < frameName.length; i++) {
                                tagList.add(new frameObject(supportedID3Frames[i],frameName[i],-1,null));
                            }
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
            Animation DetailListAnim = new AnimationUtils().loadAnimation(context, R.anim.show_details_anim);


            DetailViewAnim = new TranslateAnimation(
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.ABSOLUTE, rowView.getY(),
                    Animation.RELATIVE_TO_PARENT, 0.0f);
            DetailViewAnim.setDuration(500);
            DetailViewAnim.setFillAfter(true);

            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));


//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,noString);

            detail_list_class arrayAdapter = new detail_list_class(context, R.layout.detail_list_view, tagList);
            listView.setAdapter(arrayAdapter);
            listView.setVisibility(View.VISIBLE);

            v.startAnimation(DetailViewAnim);
            listView.startAnimation(DetailListAnim);
            android.os.Handler hand = new android.os.Handler();

            hand.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainListView.setVisibility(View.INVISIBLE);
                    mainMenu.findItem(R.id.item_more).setVisible(true);
                    mainMenu.findItem(R.id.item_commitChanges).setVisible(true);
                }
            }, 500);

        }else{
            Log.i("AlertZeek","File does not contains supported ID3 format");
            Toast.makeText(context,"Unsupported metadata format",Toast.LENGTH_SHORT).show();
        }
    }




    public static boolean fileRead (String address){

        Log.i("AlertZeek","Inside fileRead of MainActivity with address " + address);
        fileAddress = address;
        File fileToRead = new File(address);
        InputStream streamInput = null;
        boolean flag = false;
        byte[] bytesRead = new byte[3] ;
        int numberOfBytes;
        char[] readString;
        int i ;
        try{
            streamInput = new BufferedInputStream( new FileInputStream(address) ) ;
            streamInput.read(bytesRead, 0, 3);
            if ((new String(bytesRead)).substring(0,3).compareTo("ID3") == 0){
                Log.i("AlertZeek","Contains compatible ID3 data --" + (new String(bytesRead)).substring(0,3));
                flag =true;
                readID3Tags(new BufferedInputStream( new FileInputStream(address) ) );
            }
            else{
                Log.i("AlertZeek","Contains IN-compatible ID3 data --"+ (new String(bytesRead)).substring(0,3));
                flag = false;
            }
            streamInput.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }




    public static void readID3Tags(InputStream inStream) throws IOException {

        Log.i("AlertZeek","Inside readID3Tags in MainActivity");
        byte[] readByte = new byte[1] ;
        long goneThruoghBytes = 0 ;
        byte extendedHeaderPresentBit;
        long tagSize = 0;

        byte [] frameID = new byte[4];
        long frameSize ;
        byte frameFlags ;
        int frameIndex ;

        goneThruoghBytes += inStream.skip(5);


        //read header flags especially extended header bit
        extendedHeaderPresentBit =(byte) ((byte)inStream.read() & (1<<7) );



        //read total tag size - 10 (size of header)
        tagSize |= ((byte)inStream.read());
        tagSize = tagSize << 7;
        tagSize |= ((byte)inStream.read());
        tagSize = tagSize << 7;
        tagSize |= ((byte)inStream.read());
        tagSize = tagSize << 7;
        tagSize |= ((byte)inStream.read());



        if (extendedHeaderPresentBit != 0)
        {
            Log.i("AlertZeek","extended header IS present and tagSize = " + tagSize);
        }else{
            Log.i("AlertZeek","extended header NOT Present and tagSize = " + tagSize);

        }

        while(goneThruoghBytes < tagSize){

            goneThruoghBytes += inStream.read(frameID);

            frameSize = 0;

            frameSize |= ((long)inStream.read());
            frameSize = frameSize << 8;

            frameSize |= ((long)inStream.read());
            frameSize = frameSize << 8;

            frameSize |= ((long)inStream.read());
            frameSize = frameSize << 8;

            frameSize |= ((long)inStream.read());


            goneThruoghBytes += inStream.skip(1);

            frameFlags = (byte) inStream.read();

            goneThruoghBytes += 5;

            frameIndex = isSupportedFrame (frameID,frameFlags);
            if ( frameIndex != -1){
                byte[] frameDescription = new byte[(int) frameSize];

                tagList.get(frameIndex).framePosition = goneThruoghBytes;
                tagList.get(frameIndex).frameDetail = "";

                goneThruoghBytes += inStream.read(frameDescription);
                parseFrame (new String(frameID),frameSize,frameDescription,frameIndex);

            } else{
                goneThruoghBytes += inStream.skip( frameSize );
            }
        }
        inStream.close();
    }



    public static int isSupportedFrame(byte[] frameID,byte frameFlags){

        String IDToCompare = new String(frameID);
        Log.i("AlertZeek","FrameID encountered is = " + IDToCompare);
        if (IDToCompare != null)
        for (int i =0; i < supportedID3Frames.length; i++){
            if (supportedID3Frames[i].compareTo(IDToCompare) == 0){
                if ( ((frameFlags & (1<<7)) | (frameFlags & (1<<6))) == 0) {
                    Log.i("AlertZeek","Frame is supported");
                    return i;
                }else{
                    Log.i("AlertZeek","Compressed or encrypted frame");
                }
            }
        }
        return -1;
    }



    public static void  parseFrame (String frameID, long frameSize,  byte[] frameDescription, int frameIndex){

        Log.i("AlertZeek", "frame ID is = " + new String(frameID) + " frame size is = " + frameSize + " frame description = " + frameDescription);

        if (frameID.compareTo("TXXX") == 0){
            tagList.get(frameIndex).frameDetail = new String(frameDescription);
        }
        else if(frameID.charAt(0) == 'T') {
            Log.i("AlertZeek", "Reading " + frameID + " frame");
            tagList.get(frameIndex).frameDetail = textInformationParser(frameDescription);
        }


    }


    public static String textInformationParser (byte[] description) {

        if (description[0] == 1) {
            Log.i("AlertZeek","Text data encoded in 1");
            int length = (description.length - 3 ) / 2;
            if (length > 0) {
                byte[] transformed = new byte[length];
                for (int i = 0; i < length; i++) {
                    transformed[i] = description[(i * 2) + 3];
                }
                return new String(transformed);
            }
            else {
                return "";
            }

        } else if (description[0] == 0) {
            Log.i("AlertZeek","Text data encoded in 0");
            int length = (description.length - 1);
            if (length > 0) {
                byte[] transformed = new byte[length];
                for (int i = 0; i < length; i++) {
                    transformed[i] = description[i + 1];
                }
                return new String(transformed);
            } else {
                return "";
            }
        }
        return null;
    }


    @Override
    public void onDialogSaveClick(int position,String frameDetail) {
        Log.i("AlertZeek","Dialog frame data changed");
        tagList.get(position).frameDetail = frameDetail;
        tagList.get(position).frameDataChanged = true;
        ((ArrayAdapter)listViewSecond.getAdapter()).notifyDataSetChanged();
        frameDataChangedFlag = true;
        Toast.makeText(this,"Change added to list",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogCancelClick() {
        Log.i("AlertZeek", "Dialog cancel click");
    }


//  Sequence is as <itIs><><lyricsQuality><><popularityMark><><blacklistCount><>
    @Override
    public void prepareBtnClick(int frameIndex) {
        byte [] rj = new String("arjayMade").getBytes();
        byte [] byteToWrite = new byte[new String("arjayMade<><><><>").getBytes().length + 2];
        byte [] data = new String("<><><><>").getBytes();
        int i=0;Log.i("AlertZeek","inside TXXX_seperate");
        byteToWrite[0] = 0;
        for (i=0; i < rj.length; i++){
            byteToWrite[1+i] = rj[i];
        }
        byteToWrite[i+1]=0;
        for (int k=0; k < data.length; k++){
            byteToWrite[i+2+k] = data[k];
        }

        tagList.get(frameIndex).frameDetail = new String(byteToWrite);
        callDialogBox(frameIndex,0);
    }


    public static byte[][] TXXX_seperate(byte[] frameDescription){

        Log.i("AlertZeek","inside TXXX_seperate");
        byte encoding = (byte) frameDescription[0];
        Log.i("AlertZeek","encoding = "+ encoding);

        if (encoding ==1 || encoding==0) {

            int i = 0;
            int len  =0;
            int offset = 0;
            if (encoding == 1) {
                for (i = 1; i < frameDescription.length; i += 2) {
                    if (frameDescription[i] == 0) {
                        break;
                    }
                }
                len = frameDescription.length - i-1;
                offset = 2;
            } else if (encoding == 0) {
                for (i = 1; i < frameDescription.length; i += 1) {
                    if (frameDescription[i] == 0) {
                        break;
                    }
                }
                len = frameDescription.length - i;
                offset = 1;
            }

            byte[] transformed1 = new byte[i];
            transformed1[0] = encoding;
            for (int k = 1; k < i; k++) {
                transformed1[k] = frameDescription[k];
            }
            byte[] transformed2 = new byte[len];
            transformed2[0] = encoding;
            for (int k = i + offset; k < frameDescription.length; k++) {
                transformed2[k - offset -i +1] = frameDescription[k];
            }
            return new byte[][]{transformed1,transformed2};

        }else {
            return null;
        }

    }

//        void openUpDetails (View v){
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





//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//         Handle action bar item clicks here. The action bar will
//         automatically handle clicks on the Home/Up button, so long
//         as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
