package com.orbotix.drivesample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by mustafa on 9/10/2016.
 */
public class CanvasView extends View implements ColorPickerDialog.OnColorChangedListener{

    private Canvas canvas;
    private float X, Y;
    private boolean isFirst= true;
    private int color=Color.RED;

    protected ArrayList<PaintPath> pathPaints = new ArrayList<>();
    protected ArrayList<PointColor> points = new ArrayList<>();

    Bitmap bitmap;
    final float radius = 20.0f;



    public CanvasView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);

        Paint paint;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(this.color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(10.0f);

        pathPaints.add(new PaintPath(paint, new Path()));

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (PaintPath p: pathPaints){
            canvas.drawPath(p.path,p.paint);
        }
       // canvas.drawPath(this.path, this.paint);
        canvas.save();
    }

    private void startTouch(float x, float y){
        Path p = new Path();
        p.addCircle(x, y, radius, Path.Direction.CW);
        p.moveTo(x, y);
        if (!isFirst)
            p.lineTo(this.X, this.Y);
        this.X = x;
        this.Y = y;
        pathPaints.add(new PaintPath(getNewPaint(),p));

       points.add(new PointColor(x,y,this.color));

    }

    public void clearCanvas(){
        pathPaints.clear();
        isFirst = true;
        //points.clear();
        invalidate();
        System.gc();
    }

    public void showColorPicker(Context context){
        ColorPickerDialog cpd =  new ColorPickerDialog(context,this,this.color);
        cpd.show();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                isFirst = false;
                invalidate();
                break;
        }

        return true;
    }

    @Override
    public void colorChanged(int color) {
        this.color = color;
    }

    Paint getNewPaint(){
        Paint p = new Paint();
        p.setColor(this.color);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeWidth(10.0f);
        return p;
    }

}