package com.company;


import javax.swing.*;

public class JumpChess extends JLabel {
    public JumpChess(Icon image) {
            this(null, image, CENTER);
    }

    public JumpChess(String text, Icon icon, int horizontalAlignment) {
        setText(text);
        setIcon(icon);
        setHorizontalAlignment(horizontalAlignment);
        updateUI();
        setAlignmentX(LEFT_ALIGNMENT);
    }

    public void jump(double time){
        int distance=(int)time/20;
        super.setLocation(super.getX()+distance,super.getY()-distance);
    }
}

