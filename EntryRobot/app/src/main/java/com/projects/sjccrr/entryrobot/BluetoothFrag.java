package com.projects.sjccrr.entryrobot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BluetoothFrag extends Fragment {

/**
 * A simple {@link Fragment} subclass.
 */

    // Create variable to commmunicate with base activity (Camera Activity)
    OnClickOKtoClose mCallback;

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
        View view = inflater.inflate(R.layout.recycler, container, false); // TODO: Change the recycler as view
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
        view.getBackground().setAlpha(200); // Alpha value is between 0 - 255
    }


    /**
     * Interface to close the fragment
     */
    public interface OnClickOKtoClose {
        public void TagFragOKButtonClick();
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
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}