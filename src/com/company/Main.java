package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static java.lang.Thread.sleep;

public class Main {
    public static ImageIcon scaleImage(ImageIcon icon, int w, int h) {
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
        ActionHandle frame = new  ActionHandle();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);//固定大小


        //创建面板
        JPanel  panel = new JPanel();
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
        frame.setBounds(0, 0, 800, 600);
        panel.setBounds(0, 0, 800, 600);
        panel.add(jlChess);
        Plat plat1=new Plat();
        panel.add(plat1);
        frame.add(panel);
        frame.AddMousePressHandle();
        frame.AddKeyPressHandle();
        do{
            System.out.println(frame.getTime());
            if(frame.getTime()>0) {
                System.out.println("Jump!!!");
                jlChess.jump(frame.getTime());
                frame.clear();
                double y = Math.random();
                int yDistance,xDistance;
                if(y>0.5)
                {
                    y = Math.random();
                    yDistance=(int)(y*100);
                }
                else
                {
                    y = Math.random();
                    yDistance=(int)(-1*y*100);
                }
                if(Math.abs(yDistance)>50)
                {
                    xDistance=(int)(1.5*Math.abs(yDistance));
                }
                else{
                    xDistance=50+(int)(Math.random()*(300-50));
                }
                Plat nextOne =new Plat(jlChess.getX()+ plat1.getX()+xDistance,jlChess.getY()+jlChess.getHeight()-yDistance,plat1.getWidth(),plat1.getHeight());
                panel.add(nextOne);
                panel.repaint();
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    System.out.println("Can't sleep!!!");
                }
            }
        }while(true);
    }
}
