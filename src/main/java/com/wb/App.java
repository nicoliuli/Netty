package com.wb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        List<Integer> list = new ArrayList<>(
        );

        for(int i=0;i<10;i++)
            list.add(i);

        List<Integer> integers = list.subList(0, 10);
        for(Integer i:integers){
            System.out.println(i);
        }
    }
}
