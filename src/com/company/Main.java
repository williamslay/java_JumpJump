package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static java.lang.Thread.sleep;

public class Main {
    public static ImageIcon scaleImage(ImageIcon icon, int w, int h) //该函数用于按照比例拉伸ImageIcon
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if (nw > w) {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if (nh > h) {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }
        icon = new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
        return icon;
    }
    public static void main(String[] args) {
        //游戏关卡的设置
        int level=1;//游戏关卡难度，1,2,3分别为简单，中等，困难
        int pass=0;//本局游戏通关情况，0为未通关，1为通关
        //创建窗口
        ActionHandle frame = new  ActionHandle();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);//固定大小

        //创建面板
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);

        //创建标签
        Image img1 = null;
        try {
            img1 = ImageIO.read(new File("src/images/chess.jpeg"));
        } catch (IOException e) {
            System.out.println("not find img!!!");
        }
        ImageIcon chess = new ImageIcon(img1);
        //    chess=scaleImage(chess,50,50);
        JumpChess jlChess = new JumpChess(chess);
        frame.setBounds(0, 0, 1000, 400);
        panel.setBounds(0, 0, 1000, 400);
        //关卡开始
//        //首先初始化一下平台的个数
//        int platCountAll =4;
//        int platCountNow=1;
        panel.add(jlChess);
        Plat plat1=new Plat();
        panel.add(plat1);
        plat1.Random();
        Plat plat2=new Plat( (int)(plat1.getX()+plat1.xDistance),(int)(plat1.getY()- plat1.yDistance),plat1.getWidth(),plat1.getHeight());
        panel.add(plat2);
        frame.add(panel);
        frame.AddMousePressHandle();
        frame.AddKeyPressHandle();
        Plat thisOne=new Plat();
        plat2.Asign(thisOne);
        panel.add(thisOne);
        panel.repaint();

        //从此开始计时
        TimeCounter gameTime = new TimeCounter();
        panel.add(gameTime);
        panel.repaint();
        double startTime=gameTime.recordTime();
        gameTime.setMinTime(level,gameTime.getGameTime());
        do{
            //System.out.println(frame.getTime());
            double TimeNow=gameTime.recordTime();
            gameTime.setGameTime(TimeNow-startTime);
            if(gameTime.getGameTime()==gameTime.getLimitTime(level))
            {
                pass=0;
                break;
            }
            gameTime.timeChange(gameTime);
            panel.repaint();
            if(frame.getTime()>0) {
                jlChess.jump(frame.getTime(),thisOne);
                if(jlChess.getState()==2)
                {
                    pass=0;
                    break;
                }
                frame.clear();
                thisOne.Random();
                if(thisOne.getX()+thisOne.getWidth()+70>1000)
                {
                    pass=1;
                    break;
                }
                Plat nextOne= new Plat( (int)(thisOne.getX()+thisOne.xDistance),(int)(thisOne.getY()- thisOne.yDistance),thisOne.getWidth(),thisOne.getHeight());
                nextOne.Asign(thisOne);
                panel.add(nextOne);
                panel.repaint();
//                try {
//                    sleep(100);
//                } catch (InterruptedException e) {
//                    System.out.println("Can't sleep!!!");
//                }
            }
        }while(true);
        JLabel result=new JLabel();
        result.setBounds(450,0,200,40);
        result.setFont(new Font("Times New Roman",Font.BOLD,20));
        result.setForeground(Color.black);
        result.setVisible(true);
        panel.add(result);
        //添加再玩一次和玩下一模式的按钮
        JButton playAgain=new JButton("Play Again");
        JButton playNextModel=new JButton("Play Next Model");
        playAgain.setBounds(800,60,140,30);
        playNextModel.setBounds(800,100,140,30);
        panel.add(playAgain);
        panel.add(playNextModel);
        if(pass==0)//未通关
        {
            result.setText("You Lose!");
            result.setForeground(Color.black);
            panel.repaint();
        }
        else  if(pass==1)//通关
        {
            result.setText("You Win!");
            result.setForeground(Color.black);
            panel.repaint();
            if(gameTime.getGameTime()<=gameTime.getMinTime(level))//刷新纪录
            {
                JLabel congratulation=new JLabel();
                congratulation.setBounds(275,50,500,40);
                congratulation.setFont(new Font("Times New Roman",Font.BOLD,20));
                congratulation.setForeground(Color.black);
                congratulation.setVisible(true);
                congratulation.setText("Congratulations!!! You have break your record!!!");
                congratulation.setForeground(Color.black);
                panel.add(congratulation);
                panel.repaint();
            }
        }

    }
}