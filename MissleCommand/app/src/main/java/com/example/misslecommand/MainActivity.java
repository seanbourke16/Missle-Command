package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CommandView commandView;
    float[] xy=new float[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        commandView= new CommandView(this);
        //Button b=new Button(this);
        //b.setText("Start Game");
        //commandView.setBackgroundColor(Color.argb(1,0,0,1));


        //LinearLayout layout=new LinearLayout(this);
        //layout.addView(textView);
        setContentView(commandView);
        Log.i("onCreate","App");
        }

    class CommandView extends SurfaceView implements Runnable {

        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        boolean paused = false;
        Canvas canvas;
        Paint paint;
        int y;
        int posx, posy;
        int dx, dy;
        int height, width;
        ArrayList<Missle> m;
        Command a;
        Command b;
        Command c;
        boolean start;
        boolean game;

        private long thisTimeFrame;

        int level;
        int missles;
        int speed;
        TextView textView;

        public CommandView(Context context) {
            super(context);

            ourHolder = getHolder();
            paint = new Paint();
            textView=new TextView(context);
            textView.setText("Start Game");
            //textView.setWidth(100);
            //textView.setHeight(100);
            textView.setLayoutParams(new AbsoluteLayout.LayoutParams(100,100,100,100));
            textView.setTextColor(Color.argb(255,255,255,255));
            game=true;
            start=false;
        }

        @Override
        public void run() {
            Random r = new Random();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            width=displayMetrics.widthPixels;
            height=displayMetrics.heightPixels;
            m = new ArrayList();
            Log.e("Height",height+" "+"o");
            a=new Command(0,width/5);
            b=new Command(width/5*2,width/5*3);
            c=new Command(width/5*4,width);
            posx = 50;
            posy = 50;
            dx = 20;
            dy = 45;
            level=1;
            missles=5;
            speed=5;
            /*for (int i = 0; i < 10; ++i) {
                int o=r.nextInt(3);
                if(o==0) {
                    m.add(new Missle(r.nextInt(width), 0, 1, height - 50, r.nextInt(5) + 2));
                }
            }*/
            //startLevel(5,5);


            while (playing)
            {

                if (start) {
                    if(m.size()==0){
                        startLevel(missles,speed);
                        missles+=5;
                        speed++;
                    }
                    update();
                    a.update(height,width,canvas,paint);
                    b.update(height,width,canvas,paint);
                    c.update(height,width,canvas,paint);
                }
                checkGroundCollision();
                checkAirCollision(a.explosions());
                checkAirCollision(b.explosions());
                checkAirCollision(c.explosions());
                height=a.height;
                width=a.width;
                draw();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
            }
        }

        public void gameOver(){
            a.hit=false;
            b.hit=false;
            c.hit=false;
            missles=0;
            speed=4;
            game=true;
            start=true;
        }

        public void startLevel(int missles,int speed){
            /*try{
                wait(1000);
            }
            catch(InterruptedException e){
                Log.e("Wait","wait error");
            }*/
            Random r=new Random();
            int o;
            if(!a.hit)a.missles=9+(missles/5*2);
            if(!b.hit)b.missles=9+(missles/5*2);
            if(!c.hit)c.missles=9+(missles/5*2);
            for (int i = 0; i < missles; ++i) {
                if(a.hit&&b.hit&&c.hit){
                    //gameOver();
                    game=false;
                    break;
                }
                o=r.nextInt(3);
                while(true) {
                    if (o == 0) {
                        if (!a.hit) {
                            m.add(new Missle(r.nextInt(width), 0, r.nextInt(width/10*3), height, r.nextInt(speed) + 2));
                            m.get(i).setTrajectorty();
                            break;
                        }
                        else{
                            o++;
                        }
                    }
                    if (o == 1) {
                        if (!b.hit) {
                            m.add(new Missle(r.nextInt(width), 0, (width/10*3)+r.nextInt(width/5*2), height, r.nextInt(speed) + 2));
                            m.get(i).setTrajectorty();
                            break;
                        }
                        else{
                            o++;
                        }
                    }
                    if (o == 2) {
                        if (!c.hit) {
                            m.add(new Missle(r.nextInt(width), 0, (width/10*7)+r.nextInt(width/10*3), height, r.nextInt(speed) + 2));
                            m.get(i).setTrajectorty();
                            break;
                        }
                        else{
                            o=0;
                        }
                    }
                }
            }
        }

        public void checkGroundCollision(){
            for(int x=0;x<m.size();x++){
                if(m.get(x).y>height-50) {
                    m.get(x).dx = 0;
                    m.get(x).dy = 0;
                    if ((m.get(x).x > (width / 5 * 0)) && (m.get(x).x < (width / 5 * 1))){
                        a.hit = true;
                        Log.e("Ground",m.get(x).x+" "+(width / 5 * 0)+" "+(width / 5 * 1)+" "+(m.get(x).x > (width / 5 * 0))+" "+(m.get(x).x < (width / 5 * 1)));
                    }
                    if ((m.get(x).x > (width / 5 * 2)) && (m.get(x).x < (width / 5 * 3))){
                        b.hit = true;
                        Log.e("Ground",m.get(x).x+" "+(width / 5 * 2)+" "+(width / 5 * 3)+" "+(m.get(x).x > (width / 5 * 2))+" "+(m.get(x).x < (width / 5 * 3)));
                    }
                    if ((m.get(x).x > (width / 5 * 4)) && (m.get(x).x < (width / 5 * 5))){
                        c.hit = true;
                        Log.e("Ground",m.get(x).x+" "+(width / 5 * 4)+" "+(width / 5 * 5)+" "+(m.get(x).x > (width / 5 * 4))+" "+(m.get(x).x < (width / 5 * 5)));
                    }
                    m.remove(x);
                }
            }
        }

        public void checkAirCollision(ArrayList<CMissle> g){
            for(int x=0;x<g.size();x++){
               for(int y=0;y<m.size();y++){
                   if(dist(g.get(x),m.get(y))<g.get(x).size){
                       m.get(y).dx=0;
                       m.get(y).dy=0;
                       m.get(y).explosion=true;
                   }
               }
            }
        }

        public double dist(CMissle g1, Missle m1){
            return Math.sqrt(((g1.x-m1.x)*(g1.x-m1.x))+((g1.y-m1.y)*(g1.y-m1.y)));
        }

        public void update() {

            y = y + 5;
            if (y > 200)
                y = 5;

            posx += dx;
            posy += dy;
            if ((posx > width) || (posx < 0))
                dx = -dx;
            if ((posy > height) || (posy < 0))
                dy = -dy;
            Log.e("Height",height+" u");
            for (int i = 0; i < m.size(); ++i)
                m.get(i).update(height,width);

        }
        public void draw() {
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                width = canvas.getWidth();
                height = canvas.getHeight();
                Log.e("Height",height+" d");

                // Draw the background color
                canvas.drawColor(Color.argb(255, 0, 0, 0));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawLine(0, height-50, width, height-50, paint);


                // canvas.drawCircle(posx, posy, 30l, paint);
                for (int i = 0; i < m.size(); ++i) {
                    //m.get(i).width = width;
                    //m.get(i).height = height;
                    paint.setColor(Color.argb(255, 255, 255, 255));
                    m.get(i).draw(canvas, paint);
                    if(m.get(i).size<=0)m.remove(i);
                }
                paint.setStyle(Paint.Style.FILL);
                a.draw(canvas,paint,1,height,width);
                b.draw(canvas,paint,3,height,width);
                c.draw(canvas,paint,5,height,width);

                textView.draw(canvas);

                if(!start){
                    paint.setColor(Color.argb(255,0,255,0));
                    Rect r = new Rect(width/2-100, height/2-50, width/2+100, height/2+50);
                    canvas.drawRect(r,paint);
                    paint.setColor(Color.argb(255,255,255,255));
                    paint.setTextSize(25);
                    canvas.drawText("Start Game", width/2-(paint.measureText("Start Game")/2),(float)(height/2+12.5),paint);
                }

                if(!game){
                    paint.setColor(Color.argb(255,0,255,0));
                    Rect r = new Rect(width/2-100, height/2-50, width/2+100, height/2+50);
                    canvas.drawRect(r,paint);
                    paint.setColor(Color.argb(255,255,255,255));
                    paint.setTextSize(25);
                    canvas.drawText("Game Over", width/2-(paint.measureText("Game Over")/2),(float)(height/2-12.5),paint);
                    canvas.drawText("Restart?", width/2-(paint.measureText("Restart?")/2),(float)(height/2+37.5),paint);
                }

                // canvas.drawCircle(b.x, b.y, 50, paint);

                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }

        }

        public void resume() {
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                /*if(paused) {
                    paused = !paused;
                    return true;
                }*/
                if(!start){
                    if((motionEvent.getX()>width/2-100)&&(motionEvent.getX()<width/2+100)&&(motionEvent.getY()<height/2+50)&&(motionEvent.getY()>height/2-50)){
                        gameOver();
                    }
                    Log.e("Click","start");
                    return true;
                }
                if(!game){
                    if((motionEvent.getX()>width/2-100)&&(motionEvent.getX()<width/2+100)&&(motionEvent.getY()<height/2+50)&&(motionEvent.getY()>height/2-50)){
                        gameOver();
                    }
                    Log.e("Click","game");
                    return true;
                }
                Log.e("Click","fire");
                xy[0] = motionEvent.getX();
                xy[1] = motionEvent.getY();
                int fire=0;
                double g=xy[0];
                while(fire<3) {
                    if (g < width / 3) {
                        if (a.missles > 0) {
                            a.fire((int) xy[0], (int) xy[1]);
                            break;
                        } else {
                            g+=width/3;
                            fire++;
                        }
                    }
                    if(g<(width/3*2)){
                        if (b.missles > 0) {
                            b.fire((int) xy[0], (int) xy[1]);
                            break;
                        } else {
                            g+=width/3;
                            fire++;
                        }
                    }
                    if(g<width){
                        if (c.missles > 0) {
                            c.fire((int) xy[0], (int) xy[1]);
                            break;
                        } else {
                            g=width/3;
                            fire++;
                        }
                    }
                }
            }
            return true;
        }

        /*ArrayList<Missle> m;
        SurfaceHolder holder;
        Canvas canvas;
        Paint paint;
        Command a;
        Command b;
        Command c;


        public CommandView(Context context){
            super(context);
            holder=getHolder();
            paint=new Paint();
            run();
            Log.i("Command","Start");
        }

        public void run(){
            Log.i("Run","Run");
            Random r=new Random();
            /*try {
                //wait(1000);
            }
            catch(InterruptedException e){}*/
            /*int x=20;
            while(x>0) {
                m = new ArrayList<Missle>();
                m.add(new Missle());
                m.get(20 - x).x = r.nextInt(50);
                m.get(20 - x).dx = r.nextInt(30) - 15;
                m.get(20 - x).y = 0;
                m.get(20 - x).dy = r.nextInt(30);
                update();
                draw();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
                x--;
            }

            while(m.size()!=0){
                update();
                draw();
                try{
                    Thread.sleep(50);
                }
                catch(InterruptedException e){}
            }
        }

        public void update(){
            for(Missle x:m){
                x.update();
            }
        }

        public void draw(){
            if(holder.getSurface().isValid()){
                Log.i("Draw","Draw");
                canvas=holder.lockCanvas();
                canvas.drawColor(Color.argb(1,0,0,0));
                paint.setColor(Color.argb(1,0,0,1));
                canvas.drawLine(100,0,100,100,paint);

                for(Missle x:m){
                    x.draw(canvas,paint);
                }

                holder.unlockCanvasAndPost(canvas);
            }
            else{
                Log.i("Draw","No Draw");
            }
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Tell the gameView resume method to execute
        commandView.resume();
    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();

        // Tell the gameView pause method to execute
        //commandView.pause();
    }


}
