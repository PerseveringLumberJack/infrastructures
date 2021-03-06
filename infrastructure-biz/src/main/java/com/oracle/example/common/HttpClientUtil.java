package com.oracle.example.common;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * httpClient工具类
 */
@Slf4j
@Component
public class HttpClientUtil {


    //连接池对象
    private PoolingHttpClientConnectionManager pool = null;

    //请求配置
    private RequestConfig requestConfig;

    //连接池连接最大数
    private final Integer maxConnectionNum = 10;
    //最大路由，
    //这里route的概念可以理解为 运行环境机器 到 目标机器的一条线路。
    // 举例来说，我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com 的资源那么他就会产生两个route。
    //如果设置成200.那么就算上面的MAX_CONNECTION_NUM设置成9999，对同一个网站，也只会有200个可用连接
    private final Integer maxPerRoute = 10;
    //握手超时时间
    private final Integer socketTimeout = 10000;
    //连接请求超时时间
    private final Integer connectionRequestTimeout = 10000;
    //连接超时时间
    private final Integer connectionTimeout = 10000;


    /**
     * 发送post请求，返回String
     */
    public <T> String doPostForString(String url, T obj) {
        CloseableHttpResponse response = null;
        String result;
        try {
            //发送请求返回response
            response = doPost(url, obj);
            //response 转 string
            result = responseToString(response);
        } finally {
            //关闭
            closeResponseAndIn(null, response);
        }
        return result;
    }

    /**
     * 发送post请求,使用json数据,返回string
     */
    public String doPostForString(String url, String jsonString) {
        CloseableHttpResponse response = null;
        String result;
        try {
            //发送请求返回response
            response = doPost(url, jsonString);
            //response 转 string
            result = responseToString(response);
        } finally {
            //关闭
            closeResponseAndIn(null, response);
        }
        return result;
    }


    /**
     * 发起post请求,根据url，参数实体
     */
    public <T> CloseableHttpResponse doPost(String url, T obj) {
        Map<String, String> map = objectToMap(obj);
        //遍历map将其将如paramList
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> item : map.entrySet()) {
            params.add(new BasicNameValuePair(item.getKey(), item.getValue()));
        }
        //放入请求参数中
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        return execute(httpPost);
    }


    /**
     * 发起post请求,json格式
     */
    public CloseableHttpResponse doPost(String url, String jsonString) {
        //放入请求参数中
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(stringEntity);

        return execute(httpPost);
    }


    /**
     * 对象转map
     *
     * @param
     * @param obj
     * @return
     */
    private <T> Map<String, String> objectToMap(Object obj) {
        //实体类转map
        Map<String, String> map = new HashMap<>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value == null)
                value = "";
            map.put(keyName, String.valueOf(value));
        }
        return map;
    }

    /**
     * 执行请求并返回响应
     */
    private CloseableHttpResponse execute(HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient().execute(request);
        } catch (Exception e) {
            log.error("【httpClient】发送请求失败.Exception={}", e.getMessage(), e);
        }
        return response;
    }


    /**
     * 从response 中取出 html String
     * 如果没有访问成功，返回null
     */
    private String responseToString(CloseableHttpResponse response) {
        if (isSuccess(response)) {
            try {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            } catch (IOException e) {
                log.error("【httpClient】response转String,发生io异常.error={}", e.getMessage(), e);
            }
        }
        //这句不可能执行到...，返回值不会为null
        return null;
    }

    /**
     * 校验是否请求成功
     */
    private boolean isSuccess(CloseableHttpResponse response) {
        boolean flag = null != response && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        //成功直接返回
        if (flag)
            return flag;

        //如果失败，记录日志，关闭response，抛出异常
        log.error("【httpClient】请求成功，但状态码非200，状态码:{}", response.getStatusLine().getStatusCode());
        closeResponseAndIn(null, response);
        return Boolean.FALSE;
    }


    /**
     * 关闭  in 和 response
     */
    public void closeResponseAndIn(InputStream inputStream, CloseableHttpResponse response) {
        try {
            @Cleanup
            InputStream temp1 = inputStream;
            @Cleanup
            CloseableHttpResponse temp2 = response;
        } catch (Exception e) {
            log.error("【httpClient】关闭response失败.error={}", e.getMessage(), e);
            //不抛出异常
        }
    }

    /**
     * 获取HttpClient
     */
    public CloseableHttpClient getHttpClient() {
//        List<Header> l = new ArrayList<>();
//        l.add(new BasicHeader("User-Agent", "baidu"));
        return HttpClients.custom()
                //设置连接池
                .setConnectionManager(pool)
                //请求配置
                .setDefaultRequestConfig(requestConfig)
                //
//                .setDefaultHeaders(l)
                .build();
    }

    /**
     * 私有化构造方法，构造时，创建对应的连接池实例
     * 使用连接池管理HttpClient可以提高性能
     */
    public HttpClientUtil() {
        try {
            /**
             * 初始化连接池
             */
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                    sslContextBuilder.build());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", socketFactory)
                    .register("http", new PlainConnectionSocketFactory())
                    .build();
            pool = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            pool.setMaxTotal(maxConnectionNum);
            pool.setDefaultMaxPerRoute(maxPerRoute);

            /**
             * 初始化请求配置
             */
            requestConfig = RequestConfig.custom()
                    .setSocketTimeout(socketTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setConnectTimeout(connectionTimeout)
                    .build();
        } catch (Exception e) {
            log.error("【httpClient】连接池创建失败!");
        }
    }


}
