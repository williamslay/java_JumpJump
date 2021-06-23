package com.company;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.*;

public class Audios {

    List<File> clickBottom = new ArrayList<File>();//鼠标点击按钮
    List<File> countDown = new ArrayList<File>();//倒计时音效
    List<File> dead = new ArrayList<File>();//游戏结束
    List<File> jumpingUp = new ArrayList<File>();//起跳
    List<File> landingDown = new ArrayList<File>();//着陆
    List<File> newPlate = new ArrayList<File>();//生成新地块
    List<File> ready = new ArrayList<File>();//玩家准备好了
    List<File> timeOut = new ArrayList<File>();//超时
    List<File> win = new ArrayList<File>();//胜利
    List<File> zipping = new ArrayList<File>();//压缩音效

    public Audios(){

    }

    /**填充List内容 */
void fillList(List<File> list, String address){
    File theFolder=new File(address);//读取目标文件夹
    for(File f:theFolder.listFiles()){//将文件夹里的文件遍历添加到List里面
        list.add(f);
    }
}


    /**初始化Audio内的静态变量 */
    void initing(){
        fillList(clickBottom, "src/audio/clickBottom");
    }

}
