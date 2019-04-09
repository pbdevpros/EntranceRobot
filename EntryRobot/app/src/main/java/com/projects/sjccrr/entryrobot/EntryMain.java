package com.projects.sjccrr.entryrobot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class EntryMain extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_main);
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
                    Intent intent = new Intent(this, Home.class);
                    startActivity(intent);
                    Log.v("Activity", "Starting new activity, not closing the CameraActivity");
                    // this.finish(); // NB PB: DO you want to finish here?
                }
                break;
        }
        return false;
    }



}
