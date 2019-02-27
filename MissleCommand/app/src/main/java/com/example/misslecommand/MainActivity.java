package com.example.misslecommand;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CommandView commandView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        commandView= new CommandView(this);
        //Button b=new Button(this);
        //b.setText("Start Game");
        //commandView.setBackgroundColor(Color.argb(1,0,0,1));
        setContentView(commandView);
        Log.i("onCreate","App");
        }

    class CommandView extends SurfaceView implements Runnable {

        Thread gameThread = null;
        SurfaceHolder ourHolder;
        volatile boolean playing;
        boolean paused = true;
        Canvas canvas;
        Paint paint;
        int y;
        int posx, posy;
        int dx, dy;
        int height, width;
        ArrayList<Missle> m;

        private long thisTimeFrame;

        int level;
        int missles;


        public CommandView(Context context) {
            super(context);

            ourHolder = getHolder();
            paint = new Paint();
        }

        @Override
        public void run() {
            Random r = new Random();
            m = new ArrayList();
            posx = 50;
            posy = 50;
            dx = 20;
            dy = 45;
            level=1;
            missles=20;
            for (int i = 0; i < 20; ++i) {
                m.add(new Missle());
                m.get(i).x = r.nextInt(500);
                m.get(i).y = 0;
                m.get(i).dx = r.nextInt(20) - 10;
                m.get(i).dy = 5;
            }


            while (playing)
            {
                if (!paused) {
                    update();
                }
                checkCollision();
                draw();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {

                }
            }
        }

        public void checkCollision(){
            for(int x=0;x<m.size();x++){
                if
            }
        }
        public void update() {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            y = y + 5;
            if (y > 200)
                y = 5;

            posx += dx;
            posy += dy;
            if ((posx > width) || (posx < 0))
                dx = -dx;
            if ((posy > height) || (posy < 0))
                dy = -dy;

            for (int i = 0; i < m.size(); ++i)
                m.get(i).update(height,width);

        }
        public void draw() {
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                canvas = ourHolder.lockCanvas();

                width = canvas.getWidth();
                height = canvas.getHeight();

                // Draw the background color
                canvas.drawColor(Color.argb(255, 26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 255, 255, 255));
                canvas.drawLine(0, 0, 300, y, paint);


                // canvas.drawCircle(posx, posy, 30l, paint);
                for (int i = 0; i < 5; ++i) {
                    //m.get(i).width = width;
                    //m.get(i).height = height;
                    m.get(i).draw(canvas, paint);
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
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN)
                paused = !paused;
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
        commandView.pause();
    }


}
