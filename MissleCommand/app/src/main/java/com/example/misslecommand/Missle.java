package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Missle {
    double x,y,dx,dy,tarx,tary;
    double speed;
    float size;
    boolean explosion;

    public Missle(int x1,int y1, int tx, int ty, int s){
        tary=ty;
        tarx=tx;
        speed=s;
        x=x1;
        y=y1;
        size=10;
        explosion=false;
    }
    public void update(int height, int width){
        //Log.e("Width", "" + width);
        //Log.e("height", " ui" + height);
        x+=dx;
        y+=dy;
        if(x<0)x=width;
        if(x>width)x=0;

    }

    public void setTrajectorty(){
        double diffX=tarx-x;
        double diffY=tary-y;
        if(x<0)diffX=-diffX;
        if(y<0)diffY=-diffY;
        double dist=Math.sqrt((diffX*diffX)+(diffY*diffY));
        dist/=5;
        dx=diffX/dist;
        dy=diffY/dist;
        //Log.e("Set",dx+" "+dy+" "+diffX+" "+diffY+" "+dist);
    }

    public void draw(Canvas canvas, Paint paint){
        if(!explosion) {
            canvas.drawCircle((float) x, (float) y, 5, paint);
        }
        else{
            paint.setColor(Color.argb(255,255,0,0));
            canvas.drawCircle((float) x, (float) y, size, paint);
            size-=0.5;
        }
    }
}
