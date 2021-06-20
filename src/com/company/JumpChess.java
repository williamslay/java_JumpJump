package com.company;


import javax.swing.*;

import java.awt.*;

import static java.lang.Thread.sleep;

public class JumpChess extends JLabel
{
    public JumpChess(Icon image)
    {
        this(null, image);
        setBounds(0, 250, image.getIconWidth(), image.getIconHeight());
    }

    public JumpChess(String text, Icon icon)
    {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    /**
     * 跳跃函数，对于跳跃函数，我们
     *
     * @param time 从鼠标获取的时间
     */
    public void jump(double time)
    {
        //起跳的速度有点太快，可以考虑分段--HuaCL20210620 2222
        System.out.println("get it!!");
        //横向速度（匀速运动）
        double Vx=2;
        //纵向加速度（匀加速运动）
        double Ay=0.1;
        //纵向速度（初始值可设定）
        double Vy=5;
        //时间
        int actionTime=0;
        //初始位置
        int initialX,initialY;
        initialX=super.getX();
        initialY=super.getY();
        //获取窗口大小（貌似好像获取不到）
        //Insets inset =frame.getInsets();
        //System.out.println("上下左右边框的宽度 = "+inset);

        System.out.println(super.getX()+"   "+super.getY());

        int distance = (int) time / 20;
        //通过不停的刷新棋子的位置实现动画
        while(true)
        {
            setLocation((int)(initialX + Vx*actionTime),
                    (int)(initialY - Vy*actionTime+0.5*Ay*actionTime*actionTime));
            System.out.println("position:("+super.getX()+","+super.getY()+")");
            System.out.println("speed:("+Vx+","+Vy+Vy*actionTime+")");
            try
            {
                sleep(5);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            actionTime+=1;
            if(super.getY()>800)
                break;
        }
        setLocation(initialX + distance, initialY - distance);
    }
}

