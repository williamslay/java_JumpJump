package com.company;

import javax.swing.*;
import java.awt.*;

public class TimeCounter extends JLabel
{
    private double[] limitTime = new double[]{30.00,25.00,20.00};//关卡难度时间
    private double gameTime;//单局游戏时间
    //当前全部关卡，从1开始
    public TimeCounter(Level level)
    {
        setBounds(800,0,200,40);
        double limitTime=getLimitTime(1);
        double gameTime=0;
        double minTime=Main.getMinTime(level.getLevel());
        setFont(new Font("Times New Roman",Font.BOLD,15));
        setText("<html>Count Down："+String.format("%.2f", limitTime-gameTime)+"秒<br/>Fastest Record："+String.format("%.2f", minTime)+"秒</html>");
        setForeground(Color.black);
        setVisible(true);
    }

     public double getLimitTime(int level)
     {
         return limitTime[level-1];
     }
    public double getGameTime()
    {
        return gameTime;
    }
    public void setLimitTime(int level,double time)
    {
        this.limitTime[level-1]=time;
    }
    public void setGameTime(double time)
    {
        this.gameTime=time;
    }
    public double recordTime()
    {
        long TimeNow =System.currentTimeMillis();
        double Time=(double)(TimeNow/1000.0);
        return Time;
    }
    public void timeChange (Level level)
    {
        double limitTime=getLimitTime(1);
        double minTime=Main.getMinTime(level.getLevel());
        setFont(new Font("Times New Roman",Font.BOLD,15));
        setText("<html>Count Down："+String.format("%.2f", (limitTime-gameTime))+"秒<br/>Fastest Record："+String.format("%.2f", minTime)+"秒</html>");
        setForeground(Color.black);
    }
}

