//public class Main {
//    public static void main(String[] args) {
//        new Frame();
//
//    }
//}

import java.util.*;
import java.lang.*;
import java.io.*;

public class Main
{
    public static void main (String[] args) throws java.lang.Exception
    {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        String chk="";
        int max=0;
        String ans="";
        for(int i=0;i<str.length();i++) {
            if(str.charAt(i)==' '){
                int x=chk.length();
                if(max<x){
                    max=x;
                    ans=chk;

                }
                chk="";
            }
            else {
                chk += str.charAt(i);
            }
        }
        System.out.println(ans);
    }
}