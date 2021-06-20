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
        if(y>0.5)
        {
            double height=this.getHeight();
            this.yDistance=height+Math.random()*70;
        }
        else
        {
            double height=this.getHeight();
            this.yDistance=-1*(height+Math.random()*70);
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
    public int Judge(JumpChess chess)//判断碰撞函数
    {
        int chessX=chess.getX();
        int chessWidth=chess.getWidth();
        int chessY=chess.getY();
        int chessHieght=chess.getHeight();
        int platX=this.getX();
        int platWidth=this.getWidth();
        int platY=this.getY();
        int platHieght=this.getHeight();

    }
}
