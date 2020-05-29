package com.testngSuit;

import org.testng.annotations.Test;
/*testng的忽略测试
enabled：标记方法是否要执行，默认为true执行
* */
public class IgnoreTest {
    @Test
    public void ignore1(){
        System.out.println("ignore1 执行！");
    }

    @Test(enabled = false)
    public void ignore2(){
        System.out.println("ignore2 执行！");
    }

    @Test(enabled = true)
    public void ignore3(){
        System.out.println("ignore3 执行！");
    }
}
