package com.orbotix.drivesample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.orbotix.command.RollCommand;


public class DrawingActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private CanvasView2 canvasView;
    SeekBar seekBarCanvas;
    int progressOfSeekBar = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        canvasView = (CanvasView2) findViewById(R.id.signature_canvas);

        new Thread(new Runnable() {
            @Override
            public void run() {
                RobotObject.mRobot.setLedDefault(1.0f,0.0f,1.0f);//PURPLE
            }
        }).start();
        //RobotObject.mRobot.setLedDefault(1.0f,0.0f,1.0f);//PURPLE
        seekBarCanvas = (SeekBar) findViewById(R.id.seekBar);
        seekBarCanvas.setOnSeekBarChangeListener(this);
        seekBarCanvas.setMax(11);
        seekBarCanvas.setBackgroundColor(Color.WHITE);

    }

    public void clearCanvas(View v){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canvasView.clearCanvas();
            }
        });
    }
    public void showColorPicker(View v){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                canvasView.showColorPicker(DrawingActivity.this);
            }
        });

        //canvasView.showColorPicker(this);
    }
    public void generateMovement(View v) {
        if (canvasView.points.isEmpty())return;

        Thread thread = new Thread() {
            @Override
            public void run() {
                //Log.w("POINTS", "First: " + canvasView.points.get(0).x + "-" + canvasView.points.get(0).y);
                //Log.w("POINTS", "Second: " + canvasView.points.get(1).x + "-" + canvasView.points.get(1).y);

                final float maxDist = (float) Math.sqrt(Math.pow(canvasView.getWidth(), 2.0) + Math.pow(canvasView.getHeight(), 2.0));
                //Follow The Paths.
                for (int i = 1; i < canvasView.points.size(); i++) {
                    final float angle = PointColor.getAngle(canvasView.points.get(i - 1), canvasView.points.get(i));
                    final float dist = PointColor.getDistance(canvasView.points.get(i - 1), canvasView.points.get(i));

                    Log.w("DISTANCE", " = " + dist);
                    Log.w("ANGLE", "ANGLE IS " + angle);
                    Log.w("PROGRESS","progressOfSeekBar="+progressOfSeekBar);
                    int intColor = canvasView.points.get(i).color;

                    RobotObject.mRobot.setLed(
                            (float) (Color.red(intColor) / 255.0),
                            (float) (Color.green(intColor) / 255.0),
                            (float) (Color.blue(intColor) / 255.0)
                    );

                    RobotObject.mRobot.sendCommand(new RollCommand(angle, (dist / maxDist) * (progressOfSeekBar/10.f) , RollCommand.State.GO));
                    try {
                        this.sleep((long) (3600 * (dist / maxDist)));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    RobotObject.mRobot.sendCommand(new RollCommand(RobotObject.mRobot.getLastHeading(),0,RollCommand.State.STOP));
                }
            }
        };
        thread.start();


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.w("SEEKBAR","SEEK BAR IS STARTED");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        progressOfSeekBar = seekBar.getProgress();

    }




}