package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import static com.company.Main.*;

public class Level extends JFrame{
    public static Instant start;
    public static Instant end;
    public static double pressTime;//按压时间
    public static double start1p;
    public static double end1p;
    public static double pressTime1p;//按压时间
    public static double start2p;
    public static double end2p;
    public static double pressTime2p;//按压时间
    public static int End2p=0;
    //游戏关卡的设置
    private int level;//游戏关卡难度，1,2,3分别为简单，中等，困难
    private int pass;//本局游戏通关情况，0为未通关，1为通关
    private int pass1p;//本局游戏通关情况，0为未通关，1为通关
    private int pass2p;//本局游戏通关情况，0为未通关，1为通关

    public Level(int level1)
    {
        setLevel(level1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);//固定大小
        //创建面板
        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);

        //创建标签
        Image img1 = null;
        try {
            img1 = ImageIO.read(new File("src/images/chess.png"));
        } catch (IOException e) {
            System.out.println("not find img!!!");
        }
        ImageIcon chess = new ImageIcon(img1);
        //    chess=scaleImage(chess,50,50);
        //生成一个棋子对象
        JumpChess jlChess = new JumpChess(chess);
        setBounds(350, 300, 1000, 400);
        panel.setBounds(0, 0, 1000, 400);
        panel.add(jlChess);
        //绘制第一个踏板
        Plat plat1=new Plat();
        panel.add(plat1);
        plat1.Random();
        Plat plat2=new Plat((int)(plat1.getX()+plat1.xDistance),(int)(plat1.getY()- plat1.yDistance),plat1.getWidth(),plat1.getHeight());
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
            //获取当前时间
            if(gameTime.getGameTime()==gameTime.getLimitTime(this.level))//如果超时，则游戏失败
            {
                pass=0;
                break;
            }
            //显示时间
            gameTime.timeChange(this);
            panel.repaint();
            if(getPressTime()>0) {//如果没有超时
                //起跳！
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
                //生成新的平台
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
        JButton playNextMode=new JButton("Play Next Model");
        JButton BackHome=new JButton("Back to Home");
        playAgain.setBounds(800,60,140,30);
        playNextMode.setBounds(800,100,140,30);
        BackHome.setBounds(800,140,140,30);
        panel.add(playAgain);
        panel.add(playNextMode);
        panel.add(BackHome);
        if(pass==0)//未通关
        {
            playNextMode.setVisible(false);
            result.setText("You Lose!");
            result.setForeground(Color.black);
            panel.repaint();
        }
        else  if(pass==1)//通关
        {
            if(level==3)
                playNextMode.setVisible(false);
            else playNextMode.setVisible(true);
            result.setText("You Win!");
            result.setForeground(Color.black);
            panel.repaint();
            if(gameTime.getGameTime()<= getMinTime(level))//刷新纪录
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
        playAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               playGame(getLevel());
            }
        });
        playNextMode.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int level=getLevel();
                playGame(++level);
            }
        });
        BackHome.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                backToHome();
            }
        });

    }
    public Level(int level1,int mode)
    {
        setLevel(level1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);//固定大小
        //创建面板
        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel1.setBackground(new Color(250, 250, 250));
        panel2.setBackground(new Color(250, 250, 250));
        panel.setLayout(null);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel.add(panel1);
        panel.add(panel2);
        panel.setBounds(0, 0, 1000, 800);
        panel1.setBounds(0, 0, 1000, 400);
        panel2.setBounds(0, 400, 1000, 400);
        //创建标签
        Image img1 = null;
        Image img2 = null;
        try {
            img1 = ImageIO.read(new File("src/images/chess.png"));
            img2 = ImageIO.read(new File("src/images/chess.png"));
        } catch (IOException e) {
            System.out.println("not find img!!!");
        }
        ImageIcon chess1 = new ImageIcon(img1);
        ImageIcon chess2 = new ImageIcon(img2);
        //    chess=scaleImage(chess,50,50);
        //生成一个棋子对象
        JumpChess jlChess1 = new JumpChess(chess1);
        JumpChess jlChess2 = new JumpChess(chess2);
        setBounds(350, 300, 1000, 800);
        panel1.add(jlChess1);
        panel2.add(jlChess2);
        //绘制第一个踏板
        Plat plat1=new Plat();
        panel1.add(plat1);
        plat1.Random();
        Plat plat2=new Plat((int)(plat1.getX()+plat1.xDistance),(int)(plat1.getY()- plat1.yDistance),plat1.getWidth(),plat1.getHeight());
        panel1.add(plat2);
        Plat plat3=new Plat();
        panel2.add(plat3);
        plat1.Random();
        Plat plat4=new Plat((int)(plat3.getX()+plat1.xDistance),(int)(plat1.getY()- plat1.yDistance),plat1.getWidth(),plat1.getHeight());
        panel2.add(plat4);
        add(panel);
        Plat thisOne1=new Plat();
        plat2.Asign(thisOne1);
        panel1.add(thisOne1);
        Plat thisOne2=new Plat();
        plat4.Asign(thisOne2);
        panel2.add(thisOne2);
        panel1.repaint();
        panel2.repaint();
        panel.repaint();
        repaint();
        setVisible(true);

        addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==83)
                {
                    long TimeNow =System.currentTimeMillis();
                     start1p =(double)(TimeNow/1000.0);
                }
                if(e.getKeyCode()==75)
                {
                    long TimeNow =System.currentTimeMillis();
                     start2p =(double)(TimeNow/1000.0);
                }
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==83)
                {
                    long TimeNow =System.currentTimeMillis();
                     end1p =(double)(TimeNow/1000.0);
                    pressTime1p=end1p-start1p;
                    System.out.println("1p:"+pressTime1p);

                }
                if(e.getKeyCode()==75)
                {
                    long TimeNow =System.currentTimeMillis();
                     end2p =(double)(TimeNow/1000.0);
                    pressTime2p=end2p-start2p;
                    System.out.println("2p:"+pressTime2p);
                }
            }
            public void keyTyped(KeyEvent e) {}
        });
        //从此开始计时
        TimeCounter gameTime1 = new TimeCounter(this);
        TimeCounter gameTime2 = new TimeCounter();
        panel1.add(gameTime1);
        panel2.add(gameTime2);
        panel1.repaint();
        double startTime=gameTime1.recordTime();
        new Thread(new Runnable() {
            @Override
            public void run() {
                End2p=0;
                do {
                    System.out.println("2p:"+getPressTime2p());
                    double TimeNow = gameTime2.recordTime();
                    gameTime2.setGameTime(TimeNow - startTime);
                    //获取当前时间
                    if (gameTime2.getGameTime() == gameTime2.getLimitTime(level))//如果超时，则游戏失败
                    {
                        pass2p = 0;
                        break;
                    }
                    //显示时间
                    if (getPressTime2p() > 0) {
                        //起跳！
                        jlChess2.jump(getPressTime2p(), thisOne2);
                        if (jlChess2.getState() == 2) {
                            pass2p = 0;
                            break;
                        }
                        clearPressTime2p();
                        thisOne2.Random();
                        if (thisOne2.getX() + thisOne2.getWidth() + 70 > 1000) {
                            pass2p = 1;
                            break;
                        }
                        //生成新的平台
                        Plat nextOne2 = new Plat((int) (thisOne2.getX() + thisOne2.xDistance), (int) (thisOne2.getY() - thisOne2.yDistance), thisOne2.getWidth(), thisOne2.getHeight());
                        nextOne2.Asign(thisOne2);
                        panel2.add(nextOne2);
                        panel2.repaint();
                    }
                } while (true);
                End2p=1;
            }
            }).start();
        do{
            //System.out.println(frame.getTime());
            double TimeNow=gameTime1.recordTime();
            gameTime1.setGameTime(TimeNow-startTime);
            //获取当前时间
            if(gameTime1.getGameTime()==gameTime1.getLimitTime(this.level))//如果超时，则游戏失败
            {
                pass1p=0;
                break;
            }
            //显示时间
            gameTime1.timeChange(this);
            panel1.repaint();
            if(getPressTime1p()>0) {//如果没有超时
                //起跳！
                jlChess1.jump(getPressTime1p(),thisOne1);
                if(jlChess1.getState()==2)
                {
                    pass1p=0;
                    break;
                }
                clearPressTime1p();
                thisOne1.Random();
                if(thisOne1.getX()+thisOne1.getWidth()+70>1000)
                {
                    pass1p=1;
                    break;
                }
                //生成新的平台
                Plat nextOne1= new Plat( (int)(thisOne1.getX()+thisOne1.xDistance),(int)(thisOne1.getY()- thisOne1.yDistance),thisOne1.getWidth(),thisOne1.getHeight());
                nextOne1.Asign(thisOne1);
                panel1.add(nextOne1);
                panel1.repaint();
            }
        }while(true);
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(End2p==0);
            JLabel result=new JLabel();
            result.setBounds(450,0,200,40);
            result.setFont(new Font("Times New Roman",Font.BOLD,20));
            result.setForeground(Color.black);
            result.setVisible(true);
            panel1.add(result);
            //添加再玩一次和玩下一模式的按钮
            JButton playAgain=new JButton("Play Again");
            JButton playNextMode=new JButton("Play Next Model");
            JButton BackHome=new JButton("Back to Home");
            playAgain.setBounds(800,60,140,30);
            playNextMode.setBounds(800,100,140,30);
            BackHome.setBounds(800,140,140,30);
            panel1.add(playAgain);
            panel1.add(playNextMode);
            panel1.add(BackHome);
            if(pass1p==0&&pass2p==0)//未通关
            {
                playNextMode.setVisible(false);
                result.setText("Both of you Lose!");
                result.setForeground(Color.black);
                panel1.repaint();
            }
            else if(pass1p==1&&pass2p==0)
            {
                result.setText("1P Win!");
                result.setForeground(Color.black);
                panel1.repaint();
                if(gameTime1.getGameTime()<= getMinTime(level))//刷新纪录
                {
                    setMinTime(level,gameTime1.getGameTime());
                    JLabel congratulation=new JLabel();
                    congratulation.setBounds(275,50,500,40);
                    congratulation.setFont(new Font("Times New Roman",Font.BOLD,20));
                    congratulation.setForeground(Color.black);
                    congratulation.setVisible(true);
                    congratulation.setText("Congratulations!!! 1P have break record!!!");
                    congratulation.setForeground(Color.black);
                    panel1.add(congratulation);
                    panel1.repaint();
                }
            }
            else if(pass1p==0&&pass2p==1)
            {
                result.setText("2P Win!");
                result.setForeground(Color.black);
                panel1.repaint();
                if(gameTime2.getGameTime()<= getMinTime(level))//刷新纪录
                {
                    setMinTime(level,gameTime1.getGameTime());
                    JLabel congratulation=new JLabel();
                    congratulation.setBounds(275,50,500,40);
                    congratulation.setFont(new Font("Times New Roman",Font.BOLD,20));
                    congratulation.setForeground(Color.black);
                    congratulation.setVisible(true);
                    congratulation.setText("Congratulations!!! 2P have break record!!!");
                    congratulation.setForeground(Color.black);
                    panel1.add(congratulation);
                    panel1.repaint();
                }
            }
            else  if(pass1p==1&&pass2p==1)//通关
            {
                if (level == 3)
                    playNextMode.setVisible(false);
                else playNextMode.setVisible(true);
                if (gameTime1.getGameTime() < gameTime2.getGameTime()) {
                    result.setText("1P Win!");
                    result.setForeground(Color.black);
                    panel1.repaint();
                    if (gameTime1.getGameTime() <= getMinTime(level))//刷新纪录
                    {
                        setMinTime(level, gameTime1.getGameTime());
                        JLabel congratulation = new JLabel();
                        congratulation.setBounds(275, 50, 500, 40);
                        congratulation.setFont(new Font("Times New Roman", Font.BOLD, 20));
                        congratulation.setForeground(Color.black);
                        congratulation.setVisible(true);
                        congratulation.setText("Congratulations!!! 1P have break record!!!");
                        congratulation.setForeground(Color.black);
                        panel1.add(congratulation);
                        panel1.repaint();
                    }
                } else {
                    result.setText("2P Win!");
                    result.setForeground(Color.black);
                    panel1.repaint();
                    if (gameTime2.getGameTime() <= getMinTime(level))//刷新纪录
                    {
                        setMinTime(level, gameTime1.getGameTime());
                        JLabel congratulation = new JLabel();
                        congratulation.setBounds(275, 50, 500, 40);
                        congratulation.setFont(new Font("Times New Roman", Font.BOLD, 20));
                        congratulation.setForeground(Color.black);
                        congratulation.setVisible(true);
                        congratulation.setText("Congratulations!!! 2P have break record!!!");
                        congratulation.setForeground(Color.black);
                        panel1.add(congratulation);
                        panel1.repaint();
                    }
                }
            playAgain.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    play2pGame(getLevel());
                }
            });
            playNextMode.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int level=getLevel();
                    play2pGame(++level);
                }
            });
            BackHome.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    backToHome();
                }
            });
        }
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
                }
            }
            public void keyTyped(KeyEvent e) {}
        });
    };
    public double getPressTime(){
        return this.pressTime;
    }
    public double getPressTime1p(){
        return pressTime1p;
    }
    public double getPressTime2p(){
        return pressTime2p;
    }
    public void clearPressTime(){
        this.pressTime =0;
    }
    public void clearPressTime1p(){
        this.pressTime1p =0;
    }
    public void clearPressTime2p(){
        this.pressTime2p =0;
    }
    public int getLevel()
    {
        return level;
    }
    public void setLevel(int level1)
    {
        level=level1;
    }
    public void playGame(int level)
    {
        this.clearPressTime();
        this.dispose();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Level newFrame = new Level(level);
            }
        }).start();
    }
    public void play2pGame(int level)
    {
        this.clearPressTime1p();
        this.clearPressTime2p();
        this.dispose();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Level newFrame = new Level(level,2);
            }
        }).start();
    }
    public void backToHome()
    {
        this.clearPressTime();
        this.dispose();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getToHome();
            }
        }).start();
    }
}
