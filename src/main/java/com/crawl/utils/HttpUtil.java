package com.crawl.utils;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@Log
public final class HttpUtil {
    private HttpUtil() {}
    private static final PoolingHttpClientConnectionManager CONNECTION_MANAGER;
    private static final CloseableHttpClient CLIENT;
    private static final int MAX_PER_ROUTE = 200;
    private static final int MAX_TOTAL = 200;
    private static final int VALIDATE_AFTER_INACTIVITY = 1000;
    private static final int CONNECTION_TIMEOUT = 30000;
    private static final String ACCEPT = "Accept";
    private static final String UTF8 = "";

    static {
        LayeredConnectionSocketFactory ssl = null;
        try {
            ssl = SSLConnectionSocketFactory.getSystemSocketFactory();
        } catch (final SSLInitializationException ex) {
            final SSLContext sslcontext;
            try {
                sslcontext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
                sslcontext.init(null, null, null);
                ssl = new SSLConnectionSocketFactory(sslcontext);
            } catch (Exception exception) {
                log.log(Level.WARNING, "PinnacleSportBook >> HttpUtils >> exception >> {0} ", exception);
            }
        }
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
                .setCookieSpec(CookieSpecs.STANDARD)
                .setSocketTimeout(CONNECTION_TIMEOUT).build();
        final Registry<ConnectionSocketFactory> sfr = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", ssl != null ? ssl : SSLConnectionSocketFactory.getSocketFactory())
                .build();
        CONNECTION_MANAGER = new PoolingHttpClientConnectionManager(sfr);
        CONNECTION_MANAGER.setDefaultMaxPerRoute(MAX_PER_ROUTE);
        CONNECTION_MANAGER.setMaxTotal(MAX_TOTAL);
        CONNECTION_MANAGER.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        CLIENT = HttpClients.custom().setConnectionManager(CONNECTION_MANAGER).setDefaultRequestConfig(config).build();
    }

    @SneakyThrows
    public static String get(String url) {
        HttpUriRequest request = new HttpGet(url);
        request.addHeader(ACCEPT, "application/json");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        var response = CLIENT.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = "";
        if (entity != null) {
            responseString = EntityUtils.toString(entity, "UTF-8");
        }
        return responseString;
    }

    @SneakyThrows
    public static String get(String url, Map<String, String> queryParams, Map<String, String> headers) {
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        CloseableHttpResponse response;
        StringBuilder requestParams = new StringBuilder();
        for (NameValuePair queryParam : params) {
            requestParams.append(URLEncoder.encode(queryParam.getName(), StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(queryParam.getValue()), StandardCharsets.UTF_8)).append("&");
        }
        if (requestParams.capacity() > 0) {
            url = url + "?" + StringUtils.stripEnd(requestParams.toString(), "&");
        }
        HttpUriRequest request = new HttpGet(url);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> i : headers.entrySet()) {
                request.addHeader(i.getKey(), i.getValue());
            }
        }
        request.addHeader("Authorization", "");
        request.addHeader(ACCEPT, " application/json");
        request.addHeader("Accept-Encoding", "gzip, deflate");
        response = CLIENT.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = "";
        if (entity != null) {
            responseString = EntityUtils.toString(entity, "UTF-8");
        }
        return responseString;
    }

    @SneakyThrows
    public static String post(String url, Map<String, String> queryParams, Map<String, String> headers) {
        StringBuilder requestParams = new StringBuilder();
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, String> queryParam : queryParams.entrySet()) {
                requestParams.append(URLEncoder.encode(queryParam.getKey(), StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(queryParam.getValue()), "UTF-8")).append("&");
            }
        }
        if (requestParams.capacity() > 0) {
            url = url + "?" + StringUtils.stripEnd(requestParams.toString(), "&");
        }
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", "");
        httpPost.addHeader(ACCEPT, " application/json");
        httpPost.setHeader("Content-type", "application/json");
        for (Map.Entry<String, String> i : headers.entrySet()) {
            httpPost.addHeader(i.getKey(), i.getValue());
        }
        CloseableHttpResponse response = CLIENT.execute(httpPost);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }
}