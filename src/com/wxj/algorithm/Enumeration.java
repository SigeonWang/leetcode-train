package com.wxj.algorithm;

/**
 * [枚举]
 *
 * 算法原理：
 * 最为简单的思想，枚举也叫穷举，顾名思义，就是穷尽列举。枚举思想的应用场景十分广泛，也非常容易理解。
 * 简单来说，枚举就是将问题的可能解依次列举出来，然后一一带入问题检验，从而从一系列可能解中获得能够解决问题的精确解。
 *
 */
public class Enumeration {
    /**
     * 1. 百钱买百鸡问题
     *
     * 描述：
     * 公鸡一个五块钱，母鸡一个三块钱，小鸡三个一块钱，现在要用一百块钱买一百只鸡，问公鸡、母鸡、小鸡各多少只？
     *
     * 5x + 3y + 1/3 z = 100  => 15x + 9y + z = 300
     * x + y + z = 100
     * => 7x + 4y = 200  => y = 25-7/4 x
     *
     * 令 x= 4k, 得 y = 25-7k, z = 75+3k
     *
     * 又因为 0 < x, y, z < 100, 得 k的取值范围只能是1, 2, 3
     *
     */
    public static void oneHundredChickens() {
        int x, y, z;
        for (int k = 1; k < 4; k++) {
            x = 4 * k;
            y = 25 - 7 * k;
            z = 3 * k + 75;
            System.out.println("公鸡=" + x + "; 母鸡=" + y + "; 小鸡=" + z);
        }
    }

    public static void main(String[] args) {
        oneHundredChickens();
    }
}
