package com.testngSuit;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
/*在套件测试中，运行XML文件
* */
public class SuiteConfig {
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("before suite 运行");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("after suite 运行");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("BeforeTest 运行");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("AfterTest 运行");}
}
