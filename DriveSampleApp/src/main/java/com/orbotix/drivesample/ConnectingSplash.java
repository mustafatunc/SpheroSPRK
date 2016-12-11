package com.orbotix.drivesample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.orbotix.ConvenienceRobot;
import com.orbotix.DualStackDiscoveryAgent;
import com.orbotix.common.DiscoveryException;
import com.orbotix.common.Robot;
import com.orbotix.common.RobotChangedStateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mustafa on 9/22/2016.
 */

public class ConnectingSplash extends Activity implements RobotChangedStateListener{
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 42;

    String comingFrom;
    private TextView tvConnecting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.connecting_splash);

        tvConnecting = (TextView) findViewById(R.id.tvConnecting);
        tvConnecting.setAllCaps(true);
        tvConnecting.setTextColor(Color.BLACK);
        tvConnecting.setTextSize(53f);
        tvConnecting.setTypeface(Typeface.createFromAsset( getAssets(),"fonts/poppyseed.ttf"));
        tvConnecting.setText("Connecting");
        comingFrom = getIntent().getStringExtra("buttonName");
        Log.w("INTENT_HERE","COMINGFROM IS "+comingFrom);

        DualStackDiscoveryAgent.getInstance().addRobotStateListener( this );

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            int hasLocationPermission = checkSelfPermission( Manifest.permission.ACCESS_COARSE_LOCATION );
            if( hasLocationPermission != PackageManager.PERMISSION_GRANTED ) {
                Log.e( "Sphero", "Location permission has not already been granted" );
                List<String> permissions = new ArrayList<String>();
                permissions.add( Manifest.permission.ACCESS_COARSE_LOCATION);
                requestPermissions(permissions.toArray(new String[permissions.size()] ), REQUEST_CODE_LOCATION_PERMISSION );
            } else {
                Log.d( "Sphero", "Location permission already granted" );
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startDiscovery();
        }
    }

    private void startDiscovery() {
        //If the DiscoveryAgent is not already looking for robots, start discovery.
        if( !DualStackDiscoveryAgent.getInstance().isDiscovering() ) {
            Log.w("SPHERO","STARTED DISCOVERING");
            try {
                DualStackDiscoveryAgent.getInstance().startDiscovery( this );
            } catch (DiscoveryException e) {
                Log.e("Sphero", "DiscoveryException: " + e.getMessage());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch ( requestCode ) {
            case REQUEST_CODE_LOCATION_PERMISSION: {
                for( int i = 0; i < permissions.length; i++ ) {
                    if( grantResults[i] == PackageManager.PERMISSION_GRANTED ) {
                        startDiscovery();
                        Log.d( "Permissions", "Permission Granted: " + permissions[i] );
                    } else if( grantResults[i] == PackageManager.PERMISSION_DENIED ) {
                        Log.d( "Permissions", "Permission Denied: " + permissions[i] );
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DualStackDiscoveryAgent.getInstance().addRobotStateListener( null );
    }
    @Override
    public void handleRobotChangedState(Robot robot, RobotChangedStateListener.RobotChangedStateNotificationType type) {
        switch (type) {
            case Online: {
                //Save the robot as a ConvenienceRobot for additional utility methods
                Log.w("SPHERO","SPHERO IS ONLINE");

                //mRobot = new ConvenienceRobot(robot);
                RobotObject.mRobot = new ConvenienceRobot(robot);
                if(comingFrom.equals("Connect")) {
                    //Log.w("START_ACTIVITY","CONNECTION WILL START");
                    //Intent intent = new Intent(ConnectingSplash.this, MovementActivity.class);
                    //startActivity(intent);
                }else if (comingFrom.equals("DrawPath")){
                    Log.w("START_ACTIVITY","DRAW_PATH WILL START");
                    Intent intent = new Intent(ConnectingSplash.this, DrawingActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        //If the DiscoveryAgent is in discovery mode, stop it.
        if( DualStackDiscoveryAgent.getInstance().isDiscovering() ) {
            DualStackDiscoveryAgent.getInstance().stopDiscovery();
        }
        super.onStop();
    }

}
