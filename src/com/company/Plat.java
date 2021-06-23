package com.company;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Plat extends JLabel
{
    //下一个平台的距离距离原有踏板的距离
    double xDistance, yDistance;

    public Plat()
    {
        setBounds(0, 200, 70, 20);
        //让颜色五颜六色起来！
        switch((int)(Math.random()*10))
        {
            case 1://道奇蓝
                setBackground(new Color(30,144,255));
                break;
            case 2://淡蓝色
                setBackground(new Color(135,206,250));
                break;
            case 3://深紫罗兰色
                setBackground(new Color(148,0,211));
                break;
            case 4://猩红
                setBackground(new Color(220,20,60));
                break;
            case 5://靛青
                setBackground(new Color(75,0,130));
                break;
            case 6://水鸭色
                setBackground(new Color(0,128,128));
                break;
            case 7://海洋绿
                setBackground(new Color(46,139,87));
                break;
            case 8://金
                setBackground(new Color(255,215,0));
                break;
            case 9://橙色
                setBackground(new Color(255,165,0));
                break;
            case 10://巧克力
                setBackground(new Color(210,105,30));
                break;
            default://耐火砖（默认）
                setBackground(new Color(178,34,34));
        }
        setOpaque(true);
        setVisible(true);
    }

    public Plat(int x1, int y1, int length1, int height1)
    {
        setSize(length1,height1);
        //让颜色五颜六色起来！
        switch((int)(Math.random()*10))
        {
            case 1://道奇蓝
                setBackground(new Color(30,144,255));
                break;
            case 2://淡蓝色
                setBackground(new Color(135,206,250));
                break;
            case 3://深紫罗兰色
                setBackground(new Color(148,0,211));
                break;
            case 4://猩红
                setBackground(new Color(220,20,60));
                break;
            case 5://靛青
                setBackground(new Color(75,0,130));
                break;
            case 6://水鸭色
                setBackground(new Color(0,128,128));
                break;
            case 7://海洋绿
                setBackground(new Color(46,139,87));
                break;
            case 8://金
                setBackground(new Color(255,215,0));
                break;
            case 9://橙色
                setBackground(new Color(255,165,0));
                break;
            case 10://巧克力
                setBackground(new Color(210,105,30));
                break;
            default://耐火砖（默认）
                setBackground(new Color(178,34,34));
        }
        setLocation(x1,410);
        new Thread(new Runnable() {
            @Override
            public void run() {
                do
                {
                    setLocation(getX(),getY()-2);
                    try
                    {
                        sleep(5);
                    } catch (InterruptedException e)
                    {
                        if(getY()>=y1)
                            break;
                    }
                }while(getY()>=y1);
            }
        }).start();
        setLocation(x1,y1);
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
     * 随机生成踏板(指两个距离）
     */
    public void Random()
    {
        //首先我们先获取一下现有踏板的大小
        double height = this.getHeight();
        double width = this.getWidth();
        do
        {
            //double y = Math.random();
            this.yDistance=(Math.random()-0.5)*50;
//            if (y > 0.5)
//            {
//                this.yDistance = height + Math.random() * 20;
//            } else
//            {
//                this.yDistance = -1 * (height + Math.random() * 20);
//            }
        } while (this.getY() +height+ this.yDistance > 350 || this.getY() + this.yDistance < 120);//保证踏板y轴的生成范围在120-350之间
        //在确定了y轴偏移量之后，根据跳跃函数确定x轴偏移量，因为跳跃函数是由鼠标按压时间确定的，在这里我们给出一个范围。
        //根据y轴偏移量计算时间单位
        int Multiplayer=50;
        int MouseTimeMin=200;
        int MouseTimeMax=600;
        double Ay=-0.2;
        double VxMin=15+MouseTimeMin*0.02;
        double VxMax=15+MouseTimeMax*0.02;
        double VyMin=10+MouseTimeMin*0.06;
        double VyMax=10+MouseTimeMax*0.06;
        double YtMin=VyMin/Math.abs(Ay)+Math.sqrt(2*Ay*this.yDistance+VyMin*VyMin);
        double YtMax=VyMax/Math.abs(Ay)+Math.sqrt(2*Ay*this.yDistance+VyMax*VyMax);
        double YtAve=(YtMin+YtMax)/2;
        int platRangeleft=(int)(VxMin*YtMin/Multiplayer);
        int platRangeRight=(int)(VxMax*YtMax/Multiplayer);
        do{
            this.xDistance=platRangeleft+Math.random()*(platRangeRight*1.2-platRangeleft);
        }while(this.xDistance+width>=300||this.xDistance<=width+50);//距离最长不超过200,最低不少于50
//        if (Math.abs(this.yDistance) > 50)
//        {
//            this.xDistance = 1.5 * Math.abs(this.yDistance);
//        } else
//        {
//            this.xDistance = width + 30 + Math.random() * (170 - width);
//        }
        if (this.getX()+width + 300  > 1000)//考虑最后一个踏板
            this.xDistance = 1000 - this.getX() - width;
    }

    public void Asign(Plat plat2)//将当前plat变量赋给另一个plat变量
    {
        plat2.setLocation(this.getX(), this.getY());
    }

    public int Judge(int chessX1, int chessWidth, int chessY1, int chessHeight)//判断碰撞函数
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
