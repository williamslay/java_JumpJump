package com.company;

import java.util.ArrayList;

public class TimeCounter
{
    //我们用一个ArrayList来存储从程序运行开始产生的所有游戏时间（注意多用户使用多个对象）
    private ArrayList<Integer> GameTime = new ArrayList<>();
    //当前全部关卡，从1开始
    private int level = 0;

    public void startNewTime()
    {
        int TimeNow = (int) System.currentTimeMillis();
        GameTime.add(level * 2, TimeNow);
        level++;
    }

    public void endTime()
    {
        int TimeNow = (int) System.currentTimeMillis();
        GameTime.add(level * 2 + 1, TimeNow);
    }

    public int getTimeUsed_int()
    {
        return GameTime.get(level * 2 + 1) - GameTime.get(level * 2);
    }

    /**
     *
     * @param level 指定回合的所用时间返回
     * @return 返回int格式的秒数（单位毫秒）
     */
    public int getTimeUsed_int(int level)
    {
        return GameTime.get(level * 2 + 1) - GameTime.get(level * 2);
    }

//    public String getTimeUsed_String()
//    {
//        ;
//    }
}
