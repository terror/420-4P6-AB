package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> x = new ArrayList<>();
        for(int i = 0; i < 10; ++i)
            x.add(2 * i);
        for(int  i = 0 ; i < 10; ++i)
            System.out.println(x.get(i));
    }
}
