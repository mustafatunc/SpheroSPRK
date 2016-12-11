package com.orbotix.drivesample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mustafa on 9/15/2016.
 */
public class SelectionMenu extends Activity implements View.OnClickListener{

    Button btnDrawPath, btnConnect;
    TextView tvJoystick, tvDraw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_menu);
        btnConnect = (Button) findViewById(R.id.btnConnect);
        btnDrawPath = (Button) findViewById(R.id.btnDrawPath);
        btnDrawPath.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
        tvJoystick = (TextView) findViewById(R.id.tvJoy);
        tvDraw = (TextView) findViewById(R.id.tvDraw);

        tvJoystick.setTypeface(Typeface.createFromAsset( getAssets(),"fonts/poppyseed.ttf"));
        tvJoystick.setText("Free Control");
        tvJoystick.setTextSize(35.0f);
        tvDraw.setTypeface(Typeface.createFromAsset( getAssets(),"fonts/poppyseed.ttf"));
        tvDraw.setText("Draw Your Own Path");
        tvDraw.setTextSize(35.0f);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnConnect:

                Intent intent = new Intent(SelectionMenu.this,MainActivity.class);
                startActivity(intent); finish();

                break;
            case R.id.btnDrawPath:
                //Intent intent1 = new Intent(SelectionMenu.this,DrawingActivity.class);
                Intent intent1 = new Intent(SelectionMenu.this,ConnectingSplash.class);
                intent1.putExtra("buttonName","DrawPath");
                startActivity(intent1);
                finish();
                break;
        }
    }
}
