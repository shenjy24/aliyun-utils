package com.jonas.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jonas.common.Constant;

import java.io.InputStream;

/**
 * 【 OSS对象存储工具 】
 *
 * @author shenjy 2018/08/17
 */
public class OssUtil {

    private static OSS ossClient;

    static {
        try {
            ossClient = new OSSClientBuilder().build(Constant.OSS_ENDPOINT, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        } catch (Exception e) {
        }
    }

    /**
     * 上传文件流
     * @param key
     * @param inputStream
     */
    public static void upload(String key, InputStream inputStream) {
        ossClient.putObject(Constant.OSS_BUCKET, key, inputStream);
    }
}
