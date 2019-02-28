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
    public Command(int x11,int x22, int height1,int width1){
        x1=x11;
        x2=x22;
        missles=9;
        height=height1;
        width=width1;
        hit=false;
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

    public void draw(Canvas canvas, Paint paint,int x){
        //Rect r=new Rect((width/5*(x-1)),(height-100),(width/5*x),height);
        int y=height-50;
        if(!hit) {
            paint.setColor(Color.argb(255, 0, 0, 255));

        }
        else{
            paint.setColor(Color.argb(255, 255, 0, 0));
        }
        Rect r = new Rect(width / 5 * (x - 1), 630, width / 5 * x, height);
        canvas.drawRect(r,paint);
        for(int z=0;z<m.size();z++){
            paint.setColor(Color.argb(255,0,255,0));
            m.get(z).draw(canvas,paint);
            if(m.get(z).size==0)m.remove(z);
            //Log.e("M",""+x);

        }
    }


}
