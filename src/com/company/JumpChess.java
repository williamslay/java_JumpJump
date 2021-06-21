package com.company;


import javax.swing.*;

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
     * @param MouseTime 从鼠标获取的时间
     */
    public void jump(double MouseTime,Plat plat)
    {
        //起跳的速度有点太快，可以考虑分段--HuaCL20210620 2222
        System.out.println("get it!!");
        //横向速度（匀速运动,初始值和鼠标按压时间成正比）
        double Vx=0.7+MouseTime*0.05;
        //纵向加速度（匀加速运动）
        double Ay=-0.1;
        //纵向速度（初始值和鼠标按压时间成正比）
        double Vy=0.5+MouseTime*0.01;
        //时间
        int actionTime=0;
        //系数，用于将动画更加精细化，10即为/10显示
        int Multiplayer=100;
        //单位时间，用于控制移动速度
        //double standardGapTime=0.5;
        //初始位置
        int initialX,initialY;
        initialX=super.getX();
        initialY=super.getY();
        //获取窗口大小（貌似好像获取不到）
        //Insets inset =frame.getInsets();
        //System.out.println("上下左右边框的宽度 = "+inset);

        System.out.println(super.getX()+"   "+super.getY());

        int distance = (int) MouseTime / 20;
        //通过不停的刷新棋子的位置实现动画
        while(true)
        {
            setLocation((int)(initialX + (Vx*actionTime)/Multiplayer),
                    (int)(initialY - (Vy*actionTime+0.5*Ay*actionTime*actionTime)/Multiplayer));
            System.out.println("position:("+super.getX()+","+super.getY()+")");
            System.out.println("speed:("+Vx+","+Vy+Vy*actionTime+")");
            try
            {
                sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            actionTime+=1;
            //actionTime+=standardGapTime;
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==1)
            {
                //setLocation((int)(initialX + Vx*actionTime), plat.getY()-this.getHeight());
                break;
            }
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==2)
                break;
            if(plat.Judge(this.getX(),this.getWidth(),this.getY(),this.getHeight())==3)
                break;
            if(super.getY()>600)
                break;
        }
    //    setLocation(initialX + distance, initialY - distance);
    }
}

