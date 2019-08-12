package com.projects.sjccrr.entryrobot;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class Home extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, BluetoothFrag.OnClickOKtoClose {

    private Point mSize; // storing screen size dimensions
    float x1, x2, y1, y2; // define co-ordinates for swiping animations...
    RecyclerViewAdapter adapter; // Declare recycler view adapter
    RecyclerView recyclerView;  // declare recycler view
    private boolean isFragmentDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide the status bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Remove ability to rotate the screen horizontal

        // data to populate the RecyclerView with
        String data[] = new String[20]; // TODO: Change the values for data
        for (int i = 0; i < 20; i++){
            data[i] = null;
        }


        // Set up the RecyclerView
        // First find the screen size
        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        try {
            display.getSize(mSize);
        } catch (NullPointerException e) {
            display.getSize(mSize);
            Log.v("RecyclerView", "Error..");
        }

        // Call the RecyclerView class and attach the view to the video data files
        int numberOfColumns = 4; // number of columns to be displayed in the recyclerView
        recyclerView = findViewById(R.id.rvNumbers); // attach the view
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerViewAdapter(Home.this.getApplicationContext(), data, mSize);
        adapter.setClickListener(this); // set click listener
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("CLICKTAG", "You chose " + position);
        if (isFragmentDisplayed) {
            closeFragment();
        }
        if (position == 0) {
            displayFragment();
        }

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
                if(y1 < (y2 + 50) ) {
                    Intent intent = new Intent(this, EntryMain.class);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.slide_in_bottom,
                            R.anim.slide_out_top);

                }
                break;
        }
        return false;
    }


    public void displayFragment() {
        BluetoothFrag bluFrag = BluetoothFrag.newInstance();
        Log.d("blufrag", "creating the fragment...");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.homeBlutooth_Frag, bluFrag).addToBackStack(null).commit();
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
                .findFragmentById(R.id.homeBlutooth_Frag);
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
