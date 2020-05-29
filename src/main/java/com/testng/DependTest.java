package com.testng;


import org.testng.annotations.Test;

public class DependTest {

    @Test
    public void test1(){
        System.out.println("test1 run");
        throw  new RuntimeException();
    }
//当test1 抛出一个异常的时候，test2就不会执行。
//依赖的名字就是方法的名字
    @Test(dependsOnMethods = {"test1"})
    public void test2(){
        System.out.println("test2 run");
    }
}