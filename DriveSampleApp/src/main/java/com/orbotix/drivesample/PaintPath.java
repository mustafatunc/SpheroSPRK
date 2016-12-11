package com.orbotix.drivesample;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by mustafa on 11/9/2016.
 */

public class PaintPath {
    public Path path;
    public Paint paint;

    public PaintPath(){
        path = new Path();
        paint = new Paint();
    }

    public PaintPath(Paint paint, Path path){
        this.paint = paint;
        this.path = path;
    }

}
