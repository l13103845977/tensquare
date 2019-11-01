package com.tensquare.user.service;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class Test2 {
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String,String> map = new HashMap<>();
        while (true) {
            String input = scanner.nextLine();
            if (isNumber(input)) {
                if (isMobile(input)) {
                    if (map.get(input)==null){
                        System.out.println("通过此手机号注册成功");
                        map.put(input,input);
                    } else {
                        System.out.println("此手机号已被其他用户注册");
                    }
                } else {
                    System.out.println("该手机号为大陆非法手机号码");
                }
            } else {
                System.out.println("该手机号为非法手机号");
            }
        }

    }

    public static boolean isMobile(String str) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        boolean isMatch=false;
        if (str.length() != 11) {
            System.out.println("手机号应为11位数");
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
             isMatch = m.matches();
        }
        return isMatch;
    }

    /**
     * 判断是否为数字格式不限制位数
     * @param o
     *     待校验参数
     * @return
     *     如果全为数字，返回true；否则，返回false
     */
    public static boolean isNumber(Object o){
        return  (Pattern.compile("[0-9]*")).matcher(String.valueOf(o)).matches();
    }
}
