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
        do{
            System.out.println(frame.getTime());
            if(frame.getTime()>0) {
                jlChess.jump(frame.getTime(),thisOne);
                frame.clear();
                thisOne.Random();
                if(thisOne.getX()+thisOne.getWidth()+70>800)
                    break;
                Plat nextOne= new Plat( (int)(thisOne.getX()+thisOne.xDistance),(int)(thisOne.getY()- thisOne.yDistance),thisOne.getWidth(),thisOne.getHeight());
                nextOne.Asign(thisOne);
                panel.add(nextOne);
                panel.repaint();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Can't sleep!!!");
                }
            }
        }while(true);

    }
}