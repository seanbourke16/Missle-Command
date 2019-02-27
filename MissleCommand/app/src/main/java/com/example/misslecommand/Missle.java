package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Missle {
    float x,y,dx,dy;

    public void update(int height, int width){
        Log.e("Width", "" + width);
        Log.e("height", "" + height);
        x+=dx;
        y+=dy;
        if(x<0)x=width;
        if(x>width)x=0;

    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,5,paint);
    }
}
