package com.company;

import javax.swing.*;
import java.awt.*;

public class Plat extends JLabel {
    double xDistance, yDistance;//下一个平台的距离
    public Plat(){
       setBounds(0,300,70,20);
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
        setBounds(0,300,20,70);
        setVisible(true);
    }
    public void Random()//生成下一个平台随机距离和高度
    {
        double y = Math.random();
        double height=this.getHeight();
        if(y>0.5)
        {
            this.yDistance=height+Math.random()*50;
        }
        else
        {
            this.yDistance=-1*(height+Math.random()*50);
        }
        if(Math.abs(this.yDistance)>50)
        {
            this.xDistance=1.5*Math.abs(this.yDistance);
        }
        else{
            double width=this.getWidth();
           this.xDistance=width+Math.random()*(200-width);
        }
    }
    public void Asign(Plat plat2)//将当前plat变量赋给另一个plat变量
    {
        plat2.setLocation(this.getX(),this.getY());
    }

    /**
     * 碰撞判断
     * @param chessX1
     * @param chessWidth
     * @param chessY1
     * @param chessHeight
     * @return
     */
    public int Judge(int chessX1,int chessWidth, int chessY1,int chessHeight)//判断碰撞函数
    {
        int platX1=this.getX();
        int platY1=this.getY();
        int platX2=this.getX()+this.getWidth();
        int platY2=this.getY()+this.getHeight();
        int chessX2=chessX1+chessWidth;
        int chessY2=chessY1+chessHeight;
        if((platY1<=chessY2+3)&&(platY1>=chessY2-3))
        {
            if(Math.abs(chessX1-platX1)<=0.5*chessWidth||Math.abs(chessX2-platX2)<=0.5*chessWidth||((chessX1>=platX1)&&(chessX2<=platX2)))
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
