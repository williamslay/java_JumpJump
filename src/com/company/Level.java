package com.company;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class Level extends JFrame{
    public static Instant start;
    public static Instant end;
    public static double pressTime;//按压时间
    //游戏关卡的设置
    private int level=1;//游戏关卡难度，1,2,3分别为简单，中等，困难
    private int pass=0;//本局游戏通关情况，0为未通关，1为通关
    private double[] minTime = new double[]{30.00,25.00,20.00};;//关卡难度最短时间
    public Level(int level)
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);//固定大小

        repaint();
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
        setBounds(350, 300, 1000, 400);
        panel.setBounds(0, 0, 1000, 400);
        panel.add(jlChess);
        Plat plat1=new Plat();
        panel.add(plat1);
        plat1.Random();
        Plat plat2=new Plat( (int)(plat1.getX()+plat1.xDistance),(int)(plat1.getY()- plat1.yDistance),plat1.getWidth(),plat1.getHeight());
        panel.add(plat2);
        add(panel);
        AddMousePressHandle();
        AddKeyPressHandle();
        Plat thisOne=new Plat();
        plat2.Asign(thisOne);
        panel.add(thisOne);
        panel.repaint();
        repaint();
        setVisible(true);

        //从此开始计时
        TimeCounter gameTime = new TimeCounter(this);
        panel.add(gameTime);
        panel.repaint();
        double startTime=gameTime.recordTime();
        do{
            //System.out.println(frame.getTime());
            double TimeNow=gameTime.recordTime();
            gameTime.setGameTime(TimeNow-startTime);
            if(gameTime.getGameTime()==gameTime.getLimitTime(level))//如果超时，则游戏失败
            {
                pass=0;
                break;
            }
            gameTime.timeChange(this);
            panel.repaint();
            if(getPressTime()>0) {//如果没有超时
                jlChess.jump(getPressTime(),thisOne);
                if(jlChess.getState()==2)
                {
                    pass=0;
                    break;
                }
                clearPressTime();
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
            if(gameTime.getGameTime()<=getMinTime(level))//刷新纪录
            {
                setMinTime(level,gameTime.getGameTime());
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
        final boolean[] flag = {false};
        System.out.println("flag");

        playAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                flag[0] =true;
                System.out.println("233");




                //System.exit(0);
                // return;

               playGame(getLevel());
            }
        });
//        if(flag[0])
//        {
//            System.out.println("if");
//
//            System.exit(0);
//        }
    }
    public void AddMousePressHandle(){
            super.addMouseListener(new MouseListener() {
                public void mousePressed(MouseEvent e) {
                    start = Instant.now();
                }
                public void mouseReleased(MouseEvent e) {
                    end = Instant.now();
                    pressTime = Duration.between(start,end).toMillis();
                    System.out.println(pressTime);
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
                    pressTime = Duration.between(start,end).toMillis();
                    System.out.println(pressTime);
                }
            }
            public void keyTyped(KeyEvent e) {}
        });
    };
    public double getPressTime(){
        return this.pressTime;
    }
    public void clearPressTime(){
        this.pressTime =0;
    }
    public int getLevel()
    {
        return level;
    }
    public double getMinTime(int level)
    {
        return minTime[level-1];
    }
    public void setMinTime(int level,double time)
    {
        this.minTime[level-1]=time;
    }
    public void playGame(int level)
    {
        System.out.println(level);
        this.clearPressTime();
        this.dispose();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Level newFrame = new Level(1);
            }
        }).start();

        //Level newFrame = new Level(3);


//        JPanel panel = new JPanel();
//        panel.setBackground(new Color(0,0,0));
//        panel.setLayout(null);
//        panel.setBounds(0, 0, 1000, 400);
//        this.removeAll();
//        this.repaint();
//        this.add(panel);
//        this.revalidate();
//        this.repaint();
    }
}
