package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommandView commandView= new CommandView(this);
        setContentView(commandView);
    }

    class CommandView extends SurfaceView implements Runnable {

        ArrayList<Missle> m;
        SurfaceHolder holder;
        Canvas canvas;
        Paint paint;


        public CommandView(Context context){
            super(context);
            holder=getHolder();
        }

        public void run(){
            Random r=new Random();
            //command a;
            //command b;
            //command c;
            try {
                wait(1000);
            }
            catch(InterruptedException e){}
            int x=20;
            while(x>0) {
                m = new ArrayList<Missle>();
                m.add(new Missle());
                m.get(20 - x).x = r.nextInt(50);
                m.get(20 - x).dx = r.nextInt(30) - 15;
                m.get(20 - x).y = 0;
                m.get(20 - x).dy = r.nextInt(30);
                update();
                draw();
            }
        }

        public void update(){
            for(Missle x:m){
                x.update();
            }
        }

        public void draw(){
            if(holder.getSurface().isValid()){
                canvas=holder.lockCanvas();
                canvas.drawColor(Color.argb(1,0,0,0));
                paint.setColor(Color.argb(1,1,1,1));
                canvas.drawLine(100,0,100,100,paint);

                for(Missle x:m){
                    x.draw(canvas,paint);
                }

                holder.unlockCanvasAndPost(canvas);
            }
        }

    }


}
