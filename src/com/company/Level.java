package com.company;
import javax.imageio.ImageIO;
import javax.swing.*;
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
    private int level;//游戏关卡难度，1,2,3分别为简单，中等，困难
    private int pass;//本局游戏通关情况，0为未通关，1为通关

    public Level(int level1)
    {
        setLevel(level1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        playAgain.setBounds(800,60,140,30);
        playNextMode.setBounds(800,100,140,30);
        panel.add(playAgain);
        panel.add(playNextMode);
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
            if(gameTime.getGameTime()<=Main.getMinTime(level))//刷新纪录
            {
                Main.setMinTime(level,gameTime.getGameTime());
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
                int level=getLevel();
                System.out.println(level);
               playGame(getLevel());
            }
        });
        playNextMode.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int level=getLevel();
                System.out.println(level);
                if(level<3)
                playGame(++level);
                else
                {
                    JLabel NoMore=new JLabel();
                    NoMore.setBounds(380,50,250,40);
                    NoMore.setFont(new Font("Times New Roman",Font.BOLD,20));
                    NoMore.setForeground(Color.black);
                    NoMore.setVisible(true);
                    panel.add(NoMore);
                    NoMore.setText("There is no more for you!!!");
                    NoMore.setForeground(Color.black);
                    NoMore.repaint();
                }
            }
        });
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
    public void setLevel(int level1)
    {
        level=level1;
    }
    public void playGame(int level1)
    {
        this.clearPressTime();
        this.dispose();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Level newFrame = new Level(level1);
            }
        }).start();
    }
}
