package com.company;

import javax.swing.*;
import java.awt.*;

public class Plat extends JLabel
{
    //下一个平台的距离距离原有踏板的距离
    double xDistance, yDistance;

    public Plat()
    {
        setBounds(0, 200, 70, 20);
        setBackground(new Color(0, 0, 0));
        setOpaque(true);
        setVisible(true);
    }

    public Plat(int x1, int y1, int length1, int height1)
    {
        setBounds(x1, y1, length1, height1);
        setBackground(new Color(0, 0, 0));
        setOpaque(true);
        setVisible(true);
    }

    public Plat(Icon image)
    {
        setIcon(image);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        setBounds(0, 200, 20, 70);
        setVisible(true);
    }

    /**
     * 随机生成踏板
     */
    public void Random()
    {
        //首先我们先获取一下
        double height = this.getHeight();
        double width = this.getWidth();
        do
        {
            double y = Math.random();
            if (y > 0.5)
            {
                this.yDistance = height + Math.random() * 30;
            } else
            {
                this.yDistance = -1 * (height + Math.random() * 30);
            }
        } while (this.getY() + this.yDistance > 400 || this.getY() + this.yDistance < 70);
        if (Math.abs(this.yDistance) > 50)
        {
            this.xDistance = 1.5 * Math.abs(this.yDistance);
            if (this.getX() + this.xDistance + 2 * width > 1000)
                this.xDistance = 1000 - this.getX() - width;
        } else
        {
            this.xDistance = width + 30 + Math.random() * (170 - width);
            if (this.getX() + this.xDistance + 2 * width > 1000)
                this.xDistance = 1000 - this.getX() - width;
        }
    }

    public void Asign(Plat plat2)//将当前plat变量赋给另一个plat变量
    {
        plat2.setLocation(this.getX(), this.getY());
    }

    public int Judge(int
                             chessX1, int chessWidth, int chessY1, int chessHeight)//判断碰撞函数
    {
        int platX1 = this.getX();
        int platY1 = this.getY();
        int platX2 = this.getX() + this.getWidth();
        int platY2 = this.getY() + this.getHeight();
        int chessX2 = chessX1 + chessWidth;
        int chessY2 = chessY1 + chessHeight;
        //if((platY1<=chessY2+3)&&(platY1>=chessY2-3))
        //碰撞判断开始：当棋子的右侧与平台的左侧处于同一水平
        if (chessX2 >= platX1)
        {
            if (chessY2 <= platY1)//碰撞判断条件1，此时棋子下边缘大于平台上边缘
            {
                //棋子底部碰到台子,我们设置一个冗余度3
                if (0 <= chessY2 - platY1 && chessY2 - platY1 <= 3)
                {
                    //棋子右侧减去平台左侧大于0.75倍棋子宽度&&平台右侧减去棋子左侧大于0.25倍棋子宽度（视为跳跃成功）
                    if ((chessX2-platX1) >= 0.75 * chessWidth &&(platX2-chessX1) >= 0.25 * chessWidth)
                    {
                        return 1;//跳到台上
                    } else
                    {
                        //棋子左侧小于等于平台右侧（也就是说两个还是连着的）
                        if (chessX1<=platX2)
                            return 3;//跳到台上，但重心较远
                        else
                            return 0;//根本没碰着（也不会再碰着了）
                    }
                } else
                    return 0;
//                {
//                    if ((platY2 <= chessY1 + 2) && (platY2 >= chessY1 - 2))
//                    {
//                        if (((chessX1 >= platX1) && (chessX1 <= platX2)) || ((chessX2 >= platX1) && (chessX2 <= platX2)))
//                            return 2;//碰撞到平台
//                    }
//                }
            } else
            {
                //若此时棋子侧面和平台的侧面已经接触，肯定是碰不到了，直接失败
                if(chessX1<=platX2)
                {
                    if (chessY2 >= platY1 && chessY1 <= platY2)
                    {
                        return 2;//碰撞到平台
                    } else//最后一种情况，从底部碰撞到台面,此时比对棋子的顶部是否接触到平台底部，若接触到返回2
                    {
                        if (chessY1 <= platY2)
                            return 2;//碰撞到平台
                        else
                            return 0;//未碰撞到平台
                    }
                }
                else
                    return 0;
            }
        }
        return 0;
    }
}
