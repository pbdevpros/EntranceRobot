package com.projects.sjccrr.entryrobot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

public class EntryMain extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // Hide the status bar

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



}
