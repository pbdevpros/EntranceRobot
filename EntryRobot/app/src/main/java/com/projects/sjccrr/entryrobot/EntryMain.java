package com.projects.sjccrr.entryrobot;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class EntryMain extends AppCompatActivity implements BluetoothFrag.OnClickOKtoClose  {

    float x1, x2, y1, y2;
    static boolean isFragmentDisplayed = false;
    public Button enterButton;
    Animation animation;

    public enum ERROR_CODE {
        OK,
        WARNING,
        ERROR
    }


    public enum FragmentType {                              // used for deciding which fragment to display
        FRAG1,
        FRAG2
    }

    private SharedPreferences userInfo;
    private String UserNameKey = "username";
    private String PasswordKey = "password";
    private String defaultNA = "NA";


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
            String address = "http://null"; // TODO: Edit the address of the endpoint!
            if (isFragmentDisplayed) {
                closeFragment(FragmentType.FRAG1);
            } else {
                displayFragment(FragmentType.FRAG1);
                new SendHTTPRequestInBackground().execute(address);
            }
        }
    };


    public void displayFragment(FragmentType type) {

        Log.d("Fragment", "creating the fragment...");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if ( type == FragmentType.FRAG1 ) {
            BluetoothFrag bluFrag = BluetoothFrag.newInstance();
            fragmentTransaction.add(R.id.entryBlutooth_Frag, bluFrag).addToBackStack(null).commit();
        }

        // Set boolean flag to indicate fragment is open.
        Log.d("Fragment", "fragment created.");
        isFragmentDisplayed = true;
    }


    /**
     * Closes the VidGalFragment, i.e. the fragment which contains the view to show the videos that have been recorded through the app.
     * Changes a boolean "isFragmentDisplayed" to indicate the Fragment is closed.
     */
    public void closeFragment(FragmentType type) {
        // Get the FragmentManager.
        Log.d("blufrag", "Closing the fragment");
        FragmentManager fragmentManager = getSupportFragmentManager();


        if (type == FragmentType.FRAG1) {
            // Check to see if the fragment is already showing.
            BluetoothFrag bluFrag = (BluetoothFrag) fragmentManager
                    .findFragmentById(R.id.entryBlutooth_Frag);
            if (bluFrag != null) {
                // Create and commit the transaction to remove the fragment.
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.remove(bluFrag).commit();
            }
        }


        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void onArticleSelected() {
        closeFragment(FragmentType.FRAG1);
    }


    /**
     *  HTTP Connection
     *  This section is for handling connection requests in order to connect with a local server
     *  Application expects the user to configure the WiFi connection such that the device
     *  can be connected to on the LAN.
     *
     *  Nested class which inherits from AysncTask in order to run networking operation
     *  on background thread
     */

    class SendHTTPRequestInBackground extends AsyncTask<String , Void ,String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            Boolean success = SendHTTPRequest(strings[0]);
            if (success) {
                return "successful!";
            }
            return "unsuccessful.";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + server_response);
            Toast.makeText(getApplicationContext(), "Request was " + s, Toast.LENGTH_SHORT).show();
        }

        /**
         * Send a http request to a endpoint specified by the input.
         * @param domain
         * @return
         */
        public boolean SendHTTPRequest(String domain) {
            HttpURLConnection urlConnection = null;
            // Read the input stream
            try {
                URL url = new URL(domain);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                readStream(in);
            } catch (IOException e) {
                Log.e("HTTP", "Error! Could not reach endpoint!");
            } finally {
                if ( urlConnection != null ) {
                    urlConnection.disconnect();
                } else {
                    return false;
                }
            }
            return true;
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "Error!";
            }
        }
    }


    public Map<String, String> GetUserInfo() {
        // read shared preferences
        Map<String, String> userData = new HashMap<>();
        userData = CheckUserInfo();
        if ( userData.get(UserNameKey) == defaultNA || userData.get(PasswordKey) == defaultNA ) {
            userData = AskUserForInfo();
        }
        return userData;
    }


    public Map<String, String> AskUserForInfo() {
        Map<String, String> userData = new HashMap<>();
        // placeholder
        // A fragment should be displayed to ask the user for input

        // When the user hits enter, their settings should be saved and then returned to this function.

        // then return the data
        return userData;
    }



    public Map<String, String> CheckUserInfo() {
        userInfo = getSharedPreferences("UserInformation", MODE_PRIVATE);
        String usr = userInfo.getString(UserNameKey,  defaultNA);
        String pswd = userInfo.getString(PasswordKey, defaultNA);
        Map<String, String> userData = new HashMap<>();
        userData.put(usr, pswd);
        return userData;
    }

}


