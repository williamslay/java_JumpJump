package com.company;


import javax.swing.*;

public class JumpChess extends JLabel {
    int x;
    int y;
    public JumpChess(Icon image) {
        this(null, image);
        setBounds(0, 250, image.getIconWidth(), image.getIconHeight());
    }
    public JumpChess(String text, Icon icon) {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    public void jump(double time){
        System.out.println("get it!!");
        int distance=(int)time/20;
        setLocation(super.getX()+distance,super.getY()-distance);
    }

}

