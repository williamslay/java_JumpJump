package com.company;

import javax.swing.*;
import java.awt.*;

public class Plat extends JLabel {
    public Plat(){
       setBounds(0,300,70,20);
       setBackground(new Color(0,0,0));
       setOpaque(true);
       setVisible(true);
    }
    public Plat(int x1,int y1,int length1,int height1){
       setBounds(x1,y1,length1,height1);
       setBackground(new Color(0,0,0));
       setOpaque(true);
       setVisible(true);
    }
    public Plat(Icon image){
        setIcon(image);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
        setBounds(0,300,20,70);
        setVisible(true);
    }

}
