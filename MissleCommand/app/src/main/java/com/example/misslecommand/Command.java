package com.example.misslecommand;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Command {
    int missles;
    int x1,x2;
    int height;
    int width;
    boolean hit;
    ArrayList<CMissle> m=new ArrayList<>();
    public Command(int x11,int x22){
        x1=x11;
        x2=x22;
        missles=9;
        height=100;
        width=100;
        hit=false;
        Log.e("Height",height+" e");
    }

    public void fire(int x,int y){
        m.add(new CMissle((x2+x1)/2,height,x,y));
        missles--;
        //Log.e("FIRE",""+m.size());
    }

    public void update(int height, int width,Canvas canvas, Paint paint){
        //Log.e("Width", "" + width);
        //Log.e("height", "" + height);
        if(hit)missles=0;
        for(int x=0;x<m.size();x++){
            m.get(x).update(height,width);
            //Log.e("M",""+x);
        }
    }

    public ArrayList<CMissle> explosions(){
        ArrayList<CMissle> ret=new ArrayList<>();
        for(int x=0;x<m.size();x++){
            if(m.get(x).tar)ret.add(m.get(x));
        }
        return ret;
    }

    public void draw(Canvas canvas, Paint paint,int x,int height1, int width){
        //Rect r=new Rect((width/5*(x-1)),(height-100),(width/5*x),height);
        height=height1;
        int y=height-50;
        if(!hit) {
            paint.setColor(Color.argb(255, 0, 0, 255));

        }
        else{
            paint.setColor(Color.argb(255, 255, 0, 0));
        }
        Rect r = new Rect(width / 5 * (x - 1), y, width / 5 * x, height);
        canvas.drawRect(r,paint);
        paint.setColor(Color.argb(255,255,255,255));
        paint.setTextSize(25);
        //Log.e("Height",height+" i");
        if(!hit){
            canvas.drawText(missles+"",(float) ((x1+x2)/2-12.5),height-(50/4),paint);
        }
        for(int z=0;z<m.size();z++){
            paint.setColor(Color.argb(255,0,255,0));
            m.get(z).draw(canvas,paint);
            if(m.get(z).size==0)m.remove(z);
            //Log.e("M",""+x);

        }
    }


}
