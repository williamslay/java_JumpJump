package com.company;

import java.awt.*;
import javax.swing.*;

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
        Level frame = new Level(1);
//        System.out.println("我返回了");
//        Level frame2 = new Level(1);

    }
}