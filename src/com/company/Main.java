package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main {
    private static double[] minTime = new double[]{30.00,25.00,20.00};//关卡难度最短时间
    public static double getMinTime(int level)
    {
        return minTime[level-1];
    }
    public static void setMinTime(int level,double time)
    {
        minTime[level-1]=time;
    }
    public static void getToHome()
    {
        JFrame homePage=new JFrame();
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.setVisible(true);
        homePage.setResizable(false);//固定大小
        homePage.setBounds(350, 300,400,400);
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);
        panel.setBounds(0,0,400,400);
        homePage.add(panel);
        JLabel Welcome=new JLabel();
        Welcome.setBounds(70,70,300,40);
        Welcome.setFont(new Font("Times New Roman",Font.BOLD,20));
        Welcome.setForeground(Color.black);
        Welcome.setVisible(true);
        panel.add(Welcome);
        Welcome.setText("Welcome To Play JumpJump!!!");
        Welcome.setForeground(Color.black);
        JButton play1P=new JButton("Play1P");
        JButton play2P=new JButton("Play2P");
        play1P.setBounds(130,140,140,30);
        play2P.setBounds(130,200,140,30);
        panel.add(play1P);
        panel.add(play2P);
        play1P.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Level frame = new Level(1);
                    }
                }).start();
            }
        });
        play2P.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                homePage.setVisible(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Level frame = new Level(1,2);
                    }
                }).start();
            }
        });
    }
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
        //创建窗口
        getToHome();//
    }
}