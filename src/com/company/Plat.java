package com.company;

import javax.swing.*;
import java.awt.*;

public class Plat extends JLabel {
    double xDistance, yDistance;//下一个平台的距离
    public Plat(){
       setBounds(0,200,70,20);
       setBackground(new Color(0,0,0));
       setOpaque(true);
       setVisible(true);
    }
    public Plat(int x1,int y1,int length1,int height1){
       setBounds(x1,y1,length1,height1);
       setBackground(new Color(0,0,0));
       setOpaque(true);
       setVisible(true);
    }
    public Plat(Icon image){
        setIcon(image);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        setBounds(0,200,20,70);
        setVisible(true);
    }


    public void Random()
    {
        double height=this.getHeight();
        double width=this.getWidth();
        do{
            double y = Math.random();
            if(y>0.5)
            {
                this.yDistance=height+Math.random()*30;
            }
            else
            {
                this.yDistance=-1*(height+Math.random()*30);
            }
        } while(this.getY()+this.yDistance>400||this.getY()+ this.yDistance<70);
        if(Math.abs(this.yDistance)>50)
        {
            this.xDistance=1.5*Math.abs(this.yDistance);
            if(this.getX()+this.xDistance+2*width>1000)
                this.xDistance=1000-this.getX()-width;
        }
        else{
           this.xDistance=width+30+Math.random()*(170-width);
            if(this.getX()+this.xDistance+2*width>1000)
                this.xDistance=1000-this.getX()-width;
        }
    }
    public void Asign(Plat plat2)//将当前plat变量赋给另一个plat变量
    {
        plat2.setLocation(this.getX(),this.getY());
    }
    public int Judge(int chessX1,int chessWidth, int chessY1,int chessHeight)//判断碰撞函数
    {
        int platX1=this.getX();
        int platY1=this.getY();
        int platX2=this.getX()+this.getWidth();
        int platY2=this.getY()+this.getHeight();
        int chessX2=chessX1+chessWidth;
        int chessY2=chessY1+chessHeight;
        //if((platY1<=chessY2+3)&&(platY1>=chessY2-3))
        if(platY1<=chessY2)//棋子底部碰到台子
        {
            if(Math.abs(chessX1-platX1)<=0.6*chessWidth||Math.abs(chessX2-platX2)<=0.6*chessWidth||((chessX1>=platX1)&&(chessX2<=platX2)))
                return 1;//跳到台上
            else if(Math.abs(chessX1-platX1)<=chessWidth||Math.abs(chessX2-platX2)<=chessWidth)
                return 3;//跳到台上，但重心较远
        }
        else if((platY2<=chessY1+2)&&(platY2>=chessY1-2))
        {
            if(((chessX1>=platX1)&&(chessX1<=platX2))||((chessX2>=platX1)&&(chessX2<=platX2)))
                return 2;//从底部碰撞到平台
        }
        return 0;//未碰撞到平台
    }
}
