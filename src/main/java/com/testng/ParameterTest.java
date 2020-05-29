package com.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {
/*在XML文件中运行
* */
    @Test
    @Parameters({"name","age"})  //用parameter注解，传入参数的名称
    public void paramTest(String name,int age){
        System.out.println("name = " + name + ",age = " + age);
        System.out.println("@@@@@@@@@@@@@#@%%%");
    }
}
