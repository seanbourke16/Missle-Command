package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Missle {
    float x,y,dx,dy;

    public void update(){
        x+=dx;
        y+=dy;
        if(x<0)dx=-dx;

    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,50,paint);
    }
}
