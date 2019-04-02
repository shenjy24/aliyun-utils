package com.jonas.http;

import com.jonas.utils.impl.HttpServiceImpl;
import okhttp3.Response;

import java.io.IOException;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2019/02/19
 */
public class HttpTest {

    public static void main(String[] args) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=218.192.3.42";
        HttpServiceImpl httpService = new HttpServiceImpl();
        Response response = httpService.synGet(url);
        try {
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
