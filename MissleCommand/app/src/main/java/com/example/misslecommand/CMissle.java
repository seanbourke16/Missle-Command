package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class CMissle {
    double x,y,dx,dy;
    int tarx,tary;

    public CMissle(int launch,int height,int x,int y){
        this.x=launch;
        this.y=height-150;
        tarx=x;
        tary=y;
        setTrajectorty();
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
        //Log.e("Set",dx+" "+dy);
    }

    public void update(int height, int width){
        //Log.e("Width", "" + width);
        //Log.e("height", "" + height);
        x+=dx;
        y+=dy;
        if(x<0)x=width;
        if(x>width)x=0;
        if((Math.abs(tarx-x)<2&&Math.abs(tary-y)<2)||y<tary){
            dx=0;
            dy=0;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        Log.e("Draw","d");
        canvas.drawCircle((float)x,(float)y,5,paint);
    }
}
