package com.httpclient.cookies;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesFIrstPost {
    private  String url ;
    //获取配置文件的数据
    private ResourceBundle bundle;
    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
        System.out.println("url:"+url);
    }
    @Test(description = "不带参数的POST请求！！！")
    public void testPost() throws IOException {
        String testUrl = this.url+"/postDemo";
        //获取客户端
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(testUrl);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if(httpEntity != null){
            System.out.println("响应内容为：：：：："+ EntityUtils.toString(httpEntity));
        }
    }
    @Test(description = "带参数的POST请求！！！！")
    public void postwithparams() throws IOException {
        UserEntity user = new UserEntity();
        user.setName("MOMO");
        user.setAge("18");
        user.setAdd("buzhidao");
        // 将user对象转换为json字符串，并放入entity中
        StringEntity entity = new StringEntity(JSON.toJSONString(user), "UTF-8");
        String testUrl = this.url+"/postwithparam";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        StringBuffer params = new StringBuffer();
        params.append("name=MOMO"+ URLEncoder.encode("utf-8"));
        params.append("&");
        params.append("age=18");
        params.append("&");
        params.append("add=buzhidao");
        HttpPost httpPost = new HttpPost(testUrl);
        httpPost.setEntity(entity);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
         CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
         if(httpResponse.getEntity() != null){
             System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
             System.out.println("带参数的POST响应内容为："+EntityUtils.toString(httpResponse.getEntity()));
         }


    }
}
