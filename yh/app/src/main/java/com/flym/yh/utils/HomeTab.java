package com.flym.yh.utils;

/**
 * Created by Administrator on 2017/10/17 0017.
 */

public class HomeTab {

    private static int curPosition;
    private static boolean flag;

    public static boolean getFlag(){
        return HomeTab.flag;
    }

    public static void setCurPosition(int curPosition){
        HomeTab.curPosition = curPosition;
        flag = true;
    }

    public static int getCurPosition() {
        flag = false;
        return curPosition;
    }
}
