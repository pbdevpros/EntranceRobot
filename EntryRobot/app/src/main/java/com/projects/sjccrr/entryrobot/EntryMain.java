package com.projects.sjccrr.entryrobot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class EntryMain extends AppCompatActivity implements BluetoothFrag.OnClickOKtoClose  {

    float x1, x2, y1, y2;
    static boolean isFragmentDisplayed = false;
    public Button enterButton;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide the status bar
        enterButton = (Button) findViewById(R.id.entryButton);
        enterButton.setOnClickListener(mylistener);
        animation = AnimationUtils.loadAnimation(this, R.anim.shrink);

    }

    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if(y2 < (y1 - 50) ) { // TODO: Check x, y co-ordinates...
                    Intent intent = new Intent(this, Home.class);
                    startActivity(intent);
                    Log.v("Activity", "Starting new activity, not closing the EntryMain");
                    this.overridePendingTransition(R.anim.slide_in_top,
                            R.anim.slide_out_bottom);
                    // this.finish(); // NB PB: DO you want to finish here?
                }
                break;
        }
        return false;
    }



    Button.OnClickListener mylistener = new Button.OnClickListener() {
        @Override
        public void onClick(View v){
            // function
            BounceInterpolator interpolator = new BounceInterpolator(0.2, 20); // interpolator - this adds bounce, there's a whole equation
            animation.setInterpolator(interpolator); // set the interpolator to the animation
            animation.setFillEnabled(true);
            animation.setFillAfter(true); // lock view (the button) in place after animation - looks for smooth
            enterButton.startAnimation(animation); // start the animation with for the main enter button
            if (isFragmentDisplayed) {
                closeFragment();
            } else {
                displayFragment();
            }
//            AsyncTaskRunner runner = new AsyncTaskRunner();
//            runner.execute(enterButton, animation);
        }
    };


    public void displayFragment() {
        BluetoothFrag bluFrag = BluetoothFrag.newInstance();
        Log.d("blufrag", "creating the fragment...");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.entryBlutooth_Frag, bluFrag).addToBackStack(null).commit();
        Log.d("blufrag", "fragment created.");
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }


    /**
     * Closes the VidGalFragment, i.e. the fragment which contains the view to show the videos that have been recorded through the app.
     * Changes a boolean "isFragmentDisplayed" to indicate the Fragment is closed.
     */
    public void closeFragment() {
        // Get the FragmentManager.
        Log.d("blufrag", "Closing the fragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        BluetoothFrag bluFrag = (BluetoothFrag) fragmentManager
                .findFragmentById(R.id.entryBlutooth_Frag);
        if (bluFrag != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(bluFrag).commit();
        }
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void onArticleSelected() {
        closeFragment();
    }


}

/**
 * Asyc task
 * @brief Run an animation in the background
 */
//
//private class AsyncTaskRunner extends AsyncTask<Button, Animation, String> {
//
//    @Override
//    protected String doInBackground(Button myButton, Animation myAnimation) {
//        myButton.startAnimation(myAnimation);
//    }
//
//
//    @Override
//    protected void onPostExecute() {
//        Log.d("AsyncAnimate", "Finished animation");
//    }
//
//
//    @Override
//    protected void onPreExecute() {
//        Log.d("AsyncAnimate", "Starting animation");
//    }
//
//
//    @Override
//    protected void onProgressUpdate() {
//
//    }
//}

