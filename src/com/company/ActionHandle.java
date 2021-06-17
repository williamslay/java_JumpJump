package com.company;
import javax.swing.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;

public class ActionHandle extends JFrame{
    public static Instant start;
    public static Instant end;
    public static double time;
    public void AddMousePressHandle(){
            super.addMouseListener(new MouseListener() {
                public void mousePressed(MouseEvent e) {
                    start = Instant.now();
                }
                public void mouseReleased(MouseEvent e) {
                    end = Instant.now();
                    time= Duration.between(start,end).toMillis();
                }
                public void mouseExited(MouseEvent e) {}
                public void mouseEntered(MouseEvent e) {}
                public void mouseClicked(MouseEvent e) {}
            });
    };
    public void AddKeyPressHandle(){
        super.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==83)
                {
                    start = Instant.now();
                }
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==83)
                {
                    end = Instant.now();
                    time= Duration.between(start,end).toMillis();
                }
            }
            public void keyTyped(KeyEvent e) {}
        });
    };
    public double getTime(){
        return this.time;
    }
    public void clear(){
        this.time=0;
    }
}
