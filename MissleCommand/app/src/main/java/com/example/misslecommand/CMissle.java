package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class CMissle {
    double x,y,dx,dy;
    int tarx,tary;
    boolean tar;
    float size;

    public CMissle(int launch,int height,int x,int y){
        this.x=launch;
        this.y=height-150;
        tarx=x;
        tary=y;
        tar=false;
        size=25;
        setTrajectorty();
    }

    public void setTrajectorty(){
        double diffX=tarx-x;
        double diffY=tary-y;
        if(x<0)diffX=-diffX;
        if(y<0)diffY=-diffY;
        double dist=Math.sqrt((diffX*diffX)+(diffY*diffY));
        dist/=7;
        dx=diffX/dist;
        dy=diffY/dist;
        //Log.e("Set",dx+" "+dy+" "+diffX+" "+diffY+" "+dist);
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
            tar=true;
        }
    }

    public void draw(Canvas canvas, Paint paint){
        Log.e("Draw","d");
        if(tar){
            paint.setColor(Color.argb(255,255,0,0));
            canvas.drawCircle((float)x,(float)y,size,paint);
            size-=0.5;
        }
        else{
            canvas.drawCircle((float)x,(float)y,5,paint);
        }
    }
}
