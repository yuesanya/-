package com.chinasofti.ordersys.controller.duanxin;

import java.util.Random;

public class keyUtil {
    public static String keyUtils() {
        String str="0123456789";
        StringBuilder st=new StringBuilder(4);
        for(int i=0;i<4;i++){
            char ch=str.charAt(new Random().nextInt(str.length()));
            st.append(ch);
        }
        String findkey=st.toString().toLowerCase();
        return findkey;
    }
}