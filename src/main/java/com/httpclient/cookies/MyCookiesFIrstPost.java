package com.httpclient.cookies;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesFIrstPost {
    private String url;
    //获取配置文件的数据
    private ResourceBundle bundle;
    private CookieStore cookieStore;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
        System.out.println("url:" + url);


    }

    @Test(description = "不带参数的POST请求！！！")
    public void testPost() throws IOException {
        String testUrl = this.url + "/postDemo";
        //获取客户端
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(testUrl);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        if (httpEntity != null) {
            System.out.println("响应内容为：：：：：" + EntityUtils.toString(httpEntity));
        }
    }

    @Test(description = "带参数的POST请求！！！！")
    public void postwithparams() throws IOException {

        JSONObject param = new JSONObject();
        param.put("name", "MOMO");
        param.put("age", "18");
        param.put("add", "buzhidao");
        StringEntity stringEntity = new StringEntity(param.toString());

        String testUrl = this.url + "/postwithparam";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
       /* StringBuffer params = new StringBuffer();
        params.append("name=MOMO"+ URLEncoder.encode("utf-8"));
        params.append("&");
        params.append("age=18");
        params.append("&");
        params.append("add=buzhidao");*/
        HttpPost httpPost = new HttpPost(testUrl);
        //  httpPost.setEntity(entity);
        // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if (httpResponse.getEntity() != null) {

            System.out.println("带参数的POST响应内容为：" + EntityUtils.toString(httpResponse.getEntity()));
        }
        //获取cookies信息
        this.cookieStore = new BasicCookieStore();

        List<Cookie> cookielist = cookieStore.getCookies();
        System.out.println("数组的长度：" + cookielist.size());
        for (Cookie cookie : cookielist) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name =" + name);
            System.out.println("Cookie value=" + value);
        }


    }

    @Test(description = "带参数的POST请求,并获取cookies信息")
    public void postwithparams2() throws IOException {

        JSONObject param = new JSONObject();
        param.put("name", "MOMO");
        param.put("age", "18");
        param.put("add", "buzhidao");
        StringEntity stringEntity = new StringEntity(param.toString());

        String testUrl = this.url + "/postwithparam";
        //设置POST
        HttpPost httpPost = new HttpPost(testUrl);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setEntity(stringEntity);
        //创建客户端
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse httpResponse = client.execute(httpPost);
        if (httpResponse.getEntity() != null) {
            System.out.println("带参数的POST响应内容为：" + EntityUtils.toString(httpResponse.getEntity()));
        }
        this.cookieStore = client.getCookieStore();
        List<Cookie> cookieList = this.cookieStore.getCookies();
        for (int i = 0; i < cookieList.size(); i++) {
            System.out.println("获取数组的长度：+++++++" + cookieList.get(i) + "长度*************" + cookieList.size());
        }


    }

    @Test
    public void postwithparams3() throws IOException {
        JSONObject param = new JSONObject();
        param.put("name", "MOMO");
        param.put("age", "18");
        param.put("add", "buzhidao");
        StringEntity stringEntity = new StringEntity(param.toString());
        String testUrl = this.url + "/postwithparam";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置POST
        HttpPost httpPost = new HttpPost(testUrl);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setEntity(stringEntity);
        HttpClientContext context = HttpClientContext.create();
        HttpResponse httpResponse = httpClient.execute(httpPost, context);

        System.out.println(">>>>>>cookies:");
        this.cookieStore = context.getCookieStore();

        List<Cookie> cookieList = this.cookieStore.getCookies();
        for (int i = 0; i < cookieList.size(); i++) {
            System.out.println("获取数组的长度：333333" + cookieList.get(i) + "长度33333333::::::" + cookieList.size());
        }
    }

    @Test(description = "这是一个带cookie和参数的post请求", dependsOnMethods = {"postwithparams3"})
    public void postwithcookies() throws IOException {
        List<Cookie> cookieList = this.cookieStore.getCookies();

        for (int i = 0; i < cookieList.size(); i++) {
            System.out.println("获取数组的长度：222222222" + cookieList.get(i));
        }
        //获取json数据
        JSONObject param = new JSONObject();
        param.put("name", "MOMO");
        param.put("age", "18");
        param.put("add", "buzhidao");
        StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
        String testUrl = this.url + "/postwithcookies";
        HttpPost httpPost = new HttpPost(testUrl);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        httpPost.setEntity(stringEntity);
        //声明一个client对象，用来进行方法的执行,并设置cookies信息
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
        // List<Cookie> cookies = cookieStore.getCookies();
        //System.out.println("获取cookies数据的大小：：：：："+cookies.size());

        CloseableHttpResponse httpResponse = httpclient.execute(httpPost);
        String rusult = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        //就是判断返回结果是否符合预期
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);
        if (statusCode == 200) {
            System.out.println(rusult);
        } else {
            System.out.println("访问/get/with/cookies接口失败");
        }
    }
}
