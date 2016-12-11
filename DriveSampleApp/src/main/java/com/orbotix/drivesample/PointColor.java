package com.orbotix.drivesample;



/**
 * Created by mustafa on 9/30/2016.
 */

public class PointColor {
    public float x;
    public float y;
    public int color;

    PointColor(){
        this.x = 0.0f;
        this.y = 0.0f;
    }

    PointColor(float x, float y){
        this.x = x;
        this.y = y;
    }

    PointColor(float x, float y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }


    static float getAngle(PointColor from, PointColor to){
        double angle = Math.toDegrees(Math.atan2((to.y-from.y),(to.x - from.x)));
        if(angle<0)angle+=360;
        return (float) angle;
    }

    static float getDistance(PointColor from, PointColor to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y,2.0)+Math.pow(from.x-to.x,2.0));
    }

    static float getScaledDistance(PointColor from, PointColor to, float realDistance){
        return 0f;
    }

    static float getScaledDistance(float canvasDistance, float realDistance){
        return 0f;
    }

    static PointColor getDirectionVector(PointColor from, PointColor to){
        return new PointColor();
    }


}
