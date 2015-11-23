package com.wordpress.zubeentolani.arjayv010;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zeekhuge on 20/11/15.
 */

public class DialogBox extends DialogFragment {

    int frameIndex;
    String title = null;
    String editTextString = null;
    String descriptionString = null;
    String valueString = null;
    boolean isTXXX ;
    boolean isTXXXstate;

    public interface DialogBoxListner{
        public void onDialogSaveClick(int position, String frameDetail);
        public void onDialogCancelClick();
        public void prepareBtnClick(int frameIndex);
    }


    DialogBoxListner dialogBoxListner;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = null;
        View view = null;

        Log.i("AlertZeek","inside onCreateDialog");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        if (isTXXXstate) {
            Log.i("AlertZeek", "is in TXXXstate");
            view = inflater.inflate(R.layout.isintxxxstate_dialog_box_layout,null);
            final Spinner spinnerIts = (Spinner) view.findViewById(R.id.spinner_itIs);
            final Spinner spinnerLyricsQuality = (Spinner) view.findViewById(R.id.spinner_lyricsQuality);
            final Spinner spinnerPopularityMark = (Spinner) view.findViewById(R.id.spinner_popularityMark);

            final String[] its = new String[1];
            final String[] lyricsQuality = new String[1];
            final int[] popularityMark = new int[1];


            final ArrayAdapter<String>Its = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,new String[]{"","Gazal","DanceNumber","PerkySong","DevotionalSong","Advertisement","DevotionalSong"});
            final ArrayAdapter<String>Lyrics = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,new String[]{"","Excellent","Good","Bad","Very Bad"});
            final ArrayAdapter<Integer>Popularity=new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item,new Integer[]{0,1,2,3,4,5,6,7,8,9,10});

            spinnerIts.setAdapter(Its);
            spinnerLyricsQuality.setAdapter(Lyrics);
            spinnerPopularityMark.setAdapter(Popularity);

            spinnerIts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    its[0] = parent.getItemAtPosition(position).toString();
                    Log.i("Alert","-----"+its[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerLyricsQuality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    lyricsQuality[0] = parent.getItemAtPosition(position).toString();
                    Log.i("Alert","-----"+lyricsQuality[0]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerIts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    popularityMark[0] = Integer.parseInt(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            builder = new AlertDialog.Builder(getActivity())
            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    byte[] rj = new String("arjayMade").getBytes();
                    byte[] finalString = new String("<"+its[0]+"><"+lyricsQuality+"><"+String.valueOf(popularityMark[0])+">").getBytes();
                    byte[] bt = new byte[finalString.length + 2 + rj.length ];
                    int i=0;
                    bt[0] = 0;
                    for (i=0; i < rj.length;i++){
                        bt[i+1] = rj[i];
                    }
                    bt[i+1] = 0;
                    for (int k=0; k< finalString.length; k++){
                        bt[i+2+k] = finalString[k];
                    }

                     dialogBoxListner.onDialogSaveClick(frameIndex, new String(bt));

                }

            })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogBoxListner.onDialogCancelClick();
                        }
                    });

        }

        else if (isTXXX) {

            Log.i("AlertZeek", "isTXXX");

            view = inflater.inflate(R.layout.txxx_dialog_box_layout, null);
            final TextView textView = (TextView) view.findViewById(R.id.dialog_box_titleView);
            final EditText editText1 = (EditText) view.findViewById(R.id.dialog_box_editText1);
            final EditText editText2 = (EditText) view.findViewById(R.id.dialog_box_editText2);
            final Button btn = (Button) view.findViewById(R.id.dialog_box_button);


            editText1.setText(this.descriptionString);
            editText2.setText(this.valueString);
            textView.setText(title);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBoxListner.prepareBtnClick(frameIndex);
                }
            });
            builder = new AlertDialog.Builder(getActivity())
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String str = editText1.getText().toString();
                            if (str.compareTo(editTextString) != 0) {
                                dialogBoxListner.onDialogSaveClick(frameIndex, str);
                            }
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogBoxListner.onDialogCancelClick();
                        }
                    });

            Log.i("AlertZeek", "isTXXX");
        }else {
            view= inflater.inflate(R.layout.dialog_box_layout, null);
            final TextView textView = (TextView) view.findViewById(R.id.dialog_box_titleView);
            final EditText editText = (EditText) view.findViewById(R.id.dialog_box_editText);

            editText.setText(editTextString);
            textView.setText(title);

             builder = new AlertDialog.Builder(getActivity())
                 .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         String str = editText.getText().toString();
                         if (str.compareTo(editTextString) != 0) {
                             dialogBoxListner.onDialogSaveClick(frameIndex, str);
                         }
                     }

                 })
                 .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialogBoxListner.onDialogCancelClick();
                     }
                 });

        }



            builder.setView(view);

            return builder.create();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().setGravity(Gravity.TOP);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimator(transit, enter, nextAnim);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            dialogBoxListner = (DialogBoxListner) activity;
        }catch (ClassCastException e){
            throw new ClassCastException("AlertZeek:" + activity.toString() + "should implement this interface " );
        }
    }

    public void setValues(String title, String editTextString, int frameIndex){
        Log.i("AlertZeek","inside setValues");
        this.editTextString = editTextString;
        this.title = "Edit " + title + " tag";
        this.frameIndex = frameIndex;
        this.isTXXX = false;

    }

    public void setValues(String title, String editTextString, int frameIndex, boolean isTXXX){
        Log.i("AlertZeek","inside setValues");

        byte[][] bt = MainActivity.TXXX_seperate(editTextString.getBytes());
        if (bt != null) {

            this.descriptionString = MainActivity.textInformationParser(bt[0]);
            this.valueString = MainActivity.textInformationParser(bt[1]);
            Log.i("AlertZeek","des="+this.descriptionString+" val="+valueString);
            if (this.descriptionString.contains("arjayMade")){
                this.isTXXXstate = true;
            }else {
                this.isTXXXstate = false;
            }
        }else {
            Log.i("AlertZeek","bt null");
            this.descriptionString = "Encoding unsupported";
            this.valueString = "Encoding unsupported";
            this.isTXXXstate = true;
        }
        this.title = "Edit " + title + " tag";
        this.frameIndex = frameIndex;
        this.isTXXX = isTXXX;
    }
}
