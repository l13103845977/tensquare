package com.tensquare.user.service;

import java.util.Scanner;

public class Text1 {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String str=scanner.nextLine();
        int len = str.length();
        int[][] dp=new int[len][len];
        int maxlen = 0, start = 0;
        for (int i = 0; i < str.length(); i++) {
            dp[i][i] = 1;
            if(i < len -1 && str.charAt(i) == str.charAt(i+1)) {
                dp[i][i+1] = 1;
                start = i;
                maxlen = 2;
            }
        }

        for (int i = 3; i < str.length(); i++) { //分析整个串长度
            for (int j = 0; j < len - i; j++) { //子串其实地址
                int m = i+j - 1; //子串结束地址
                if(dp[j+1][i-1] == 1 && str.charAt(i) == str.charAt(j)) {
                    dp[j][i] = 1;
                    maxlen = i;
                    start = j;
                }
            }
        }
        System.out.println(str.substring(start,start+maxlen-1));
    }



}
