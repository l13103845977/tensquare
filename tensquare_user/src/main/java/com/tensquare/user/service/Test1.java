package com.tensquare.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        Object ddd = Test1.selectlist("asdfasdf");
        System.out.println(ddd);
    }

    public static Object selectlist(String str){
        List list = Test1.selectString(str);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String var1, String var2) {
                if (var1.length() < var2.length()) {
                    return 1;
                } else if (var1.length() == var2.length()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        String stra="";
        for (int i=0;i<list.size();i++){
            Object o1=list.get(i+1);
            Object o = list.get(i);
            stra=o.toString();
            if (o1.toString().length()==o.toString().length()){
                stra=stra+"  "+o1.toString();
            }else {
                return stra;
            }
        }
        return stra;

    }

    public static List selectString(String str) {
        List list=new ArrayList();
        list.add(str);
        int length = str.length();
        if (length < 2) {
            return list;
        }
        if (Test1.panduan(str)) {
            list.add(str);
        }
        for (int i = 0; i < length; i++) {
            String before = str.substring(i);
            if (Test1.panduan(before)) {
                list.add(before);
            }
            List list1 = Test1.selectString(before);
            list.addAll(list1);
        }
        return list;
    }



    public static boolean panduan(String str) {
        StringBuffer sb = new StringBuffer(str);
        String str2 = sb.reverse().toString();
        return str.equals(str2);
    }


}
