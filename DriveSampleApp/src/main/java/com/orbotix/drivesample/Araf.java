package com.orbotix.drivesample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;


public class Araf extends Activity implements SeekBar.OnSeekBarChangeListener{

    private CanvasView canvasView;
    SeekBar seekBarCanvas;
    int progressOfSeekBar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.araf_layout);
        canvasView = (CanvasView) findViewById(R.id.signature_canvas);
        seekBarCanvas = (SeekBar) findViewById(R.id.seekBar);
        seekBarCanvas.setOnSeekBarChangeListener(this);
        seekBarCanvas.setMax(10);
        seekBarCanvas.setBackgroundColor(Color.WHITE);
    }

    public void clearCanvas(View v){
        canvasView.clearCanvas();
    }
    public void showColorPicker(View v){canvasView.showColorPicker(this);}
    public void generateMovement(View v) {    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        Log.w("SEEKBAR","SEEK BAR IS ON PROGRESS getProgress()="+seekBar.getProgress()+" progress="+progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.w("SEEKBAR","SEEK BAR IS STARTED");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.w("SEEKBAR","SEEK BAR IS STOPPED");
        progressOfSeekBar = seekBar.getProgress();

    }
}