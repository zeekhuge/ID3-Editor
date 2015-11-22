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
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zeekhuge on 20/11/15.
 */

public class DialogBox extends DialogFragment {

    int frameIndex;
    String title = null;
    String editTextString = null;


    public interface DialogBoxListner{
        public void onDialogSaveClick(int position, String frameDetail);
        public void onDialogCancelClick();
    }


    DialogBoxListner dialogBoxListner;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.i("AlertZeek","inside onCreateDialog");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.dialog_box_layout, null);
        final TextView textView= (TextView) view.findViewById(R.id.dialog_box_titleView);
        final EditText editText= (EditText) view.findViewById(R.id.dialog_box_editText);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = editText.getText().toString();
                        if (str.compareTo(editTextString) != 0){
                            dialogBoxListner.onDialogSaveClick(frameIndex,str );
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogBoxListner.onDialogCancelClick();
                    }
                });



        textView.setText(title);
        editText.setText(editTextString);

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
    }
}
