package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Paint;

public class CMissle {
    float x,y,dx,dy;
    int tarx,tary;

    public CMissle(int launch,int x,int y){
        this.x=launch;
        this.y=0;
        tarx=x;
        tary=y;
    }

    public void update(){
        x+=dx;
        y+=dy;
        if(x<0)dx=-dx;

    }

    public void draw(Canvas canvas, Paint paint){
        canvas.drawCircle(x,y,50,paint);
    }
}
