package com.projects.sjccrr.entryrobot;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class Home extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    private Point mSize; // storing screen size dimensions

    // Declare recycler view
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
//    private boolean isFragmentDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

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
        int numberOfColumns = 2; // number of columns to be displayed in the recyclerView
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
//        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
//        if (isFragmentDisplayed) {
//            closeFragment();
//        }
//        displayFragment(adapter.getItem(position), false);

    }


}
