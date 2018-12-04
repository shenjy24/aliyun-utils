package com.jonas.utils;

import okhttp3.Response;

import java.util.Map;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2018/10/25
 */
public interface HttpService {

    /**
     * 同步get请求
     * @param url
     * @return
     */
    Response synGet(String url);

    /**
     * 同步get请求
     * @param url
     * @param params
     * @return
     */
    Response synGet(String url, Map<String, Object> params);

    /**
     * 同步post请求
     * @param url
     * @param params
     * @return
     */
    Response synPost(String url, Map<String, Object> params);
}
