package com.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesFirstGet {
    private  String url ;
    //获取配置文件的数据
    private ResourceBundle bundle;
   //用来存储cookies信息的变量
    private CookieStore cookieStore;

     @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
         System.out.println("url:"+url);
    }
    @Test
    public void testGetCookies() throws IOException {
         String result;
         //从配置文件中，拼接测试的url
         String uri = bundle.getString("getCookies.uri");
         String testUrl = this.url+uri;
        System.out.println("testUrl:"+testUrl);
         //测试过程
         HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(get);
       result = EntityUtils.toString(response.getEntity(), Charset.defaultCharset());
        System.out.println("响应的结果========"+result);
        //获取cookie信息
        this.cookieStore = client.getCookieStore();
        List<Cookie> cookieList = this.cookieStore.getCookies();

        for(int i = 0 ; i<cookieList.size(); i++){
            System.out.println("获取数组的长度：+++++++"+cookieList.get(i));
        }
        for(Cookie cookie : cookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name = "+name +";cookie value = "+value);
        }
    }
    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        List<Cookie> cookieList = this.cookieStore.getCookies();

        for(int i = 0 ; i<cookieList.size(); i++){
            System.out.println("获取数组的长度：222222222"+cookieList.get(i));
        }
        String testUrl ="http://localhost:18888/get/with/cookies";
        //创建带cookies的请求对象
        CloseableHttpClient client=HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
        HttpGet get = new HttpGet("http://localhost:18888/get/with/cookies");
        CloseableHttpResponse response=client.execute(get);
        HttpEntity entity=response.getEntity();
        String result=EntityUtils.toString(entity,"utf-8");
        System.out.println("带cookies的get请求：：：：：：：：：：："+result);

       // response.close();
        client.close();
    }


}
