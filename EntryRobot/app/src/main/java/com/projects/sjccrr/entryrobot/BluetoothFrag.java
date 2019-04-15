package com.projects.sjccrr.entryrobot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class BluetoothFrag extends Fragment {

/**
 * A simple {@link Fragment} subclass.
 */

    // Create variable to commmunicate with base activity (Camera Activity)
    OnClickOKtoClose mCallback;
    Button buttonBasic;

    public BluetoothFrag() {
        // Required empty public constructor
    }

    public static BluetoothFrag newInstance() {
        return new BluetoothFrag();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Link all the buttons to their layouts and add OnClickListener
        Log.d("OPEN", "Blutooth Fragment has been opened.");
        View view = inflater.inflate(R.layout.blutooth_frag, container, false); // TODO: Change the recycler as view

        return view;
    }


    /**
     * Create the view and set the background to a faded version of the chosen image file.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        // Link Image Buttons to views and setOnClickListeners
        Button buttonBasic = (Button) view.findViewById(R.id.buttonBasic);
        buttonBasic.setOnClickListener(buttonListener);
        view.getBackground().setAlpha(200); // Alpha value is between 0 - 255
    }


    Button.OnClickListener buttonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v){
            mCallback.onArticleSelected();
        }
    };


    /**
     * Interface to close the fragment
     */
    public interface OnClickOKtoClose {
        public void onArticleSelected();
    }

    /**
     * Ensures fragment is created safely.
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnClickOKtoClose) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnClickOKtoClose");
        }
    }


}