package com.example.misslecommand;

public class Command {
    int missles;
    int x1,x2;
    public Command(int x11,int x22){
        x1=x11;
        x2=x22;
        missles=9;
    }

    public void fire(int x,int y){
        CMissle cM=new CMissle((x2-x1)/2,x,y);
        missles--;
    }


}
