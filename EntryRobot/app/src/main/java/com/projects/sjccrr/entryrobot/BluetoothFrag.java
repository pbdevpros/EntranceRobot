package com.projects.sjccrr.entryrobot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class BluetoothFrag extends Fragment {

/**
 * A simple {@link Fragment} subclass.
 */

    // Create variable to commmunicate with base activity (Camera Activity)
    OnClickOKtoClose mCallback;
    Button buttonBasic;
    private Animation bounceAnimation;
    private Context mainContext;

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
        Log.d("blufrag", "Blutooth Fragment has been opened.");
        View view = inflater.inflate(R.layout.blutooth_frag, container, false);
        bounceAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shrink);
        Log.d("Animation", "This is the animation" + bounceAnimation);
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
        // Link Image Buttons to views and setOnClickListeners and animations
        Button buttonBasic = (Button) view.findViewById(R.id.blutoothButton);
        buttonBasic.setOnClickListener(buttonListener);

//        view.getBackground().setAlpha(200); // Alpha value is between 0 - 255
    }


    Button.OnClickListener buttonListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v){
            BounceInterpolator interpolator = new BounceInterpolator(0.2, 20); // interpolator - this adds bounce, there's a whole equation
            bounceAnimation.setInterpolator(interpolator); // set the interpolator to the animation
            bounceAnimation.setFillEnabled(true);
            bounceAnimation.setFillAfter(true); // lock view (the button) in place after animation - looks for smooth
            // TODO: Make animation work...
//            buttonBasic.startAnimation(bounceAnimation);

            // TODO: DEFINE function to connect to network...


            // Close fragment
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
        mainContext = activity;
    }


}