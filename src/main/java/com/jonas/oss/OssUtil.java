package com.jonas.oss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.jonas.common.Constant;
import com.jonas.utils.HttpService;
import com.jonas.utils.impl.HttpServiceImpl;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 【 OSS对象存储工具 】
 *
 * @author shenjy 2018/08/17
 */
public class OssUtil {

    private static OSS ossClient;

    private static HttpService httpService;

    static {
        try {
            ossClient = new OSSClientBuilder().build(Constant.OSS_ENDPOINT, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
            httpService = new HttpServiceImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件流
     *
     * @param key
     * @param inputStream
     */
    public static void uploadFileStream(String key, InputStream inputStream) {
        ossClient.putObject(Constant.OSS_BUCKET, key, inputStream);
    }

    /**
     * 上传本地文件
     *
     * @param key
     * @param filePath
     */
    public static void uploadLocalFile(String key, String filePath) {
        File file = new File(filePath);
        ossClient.putObject(Constant.OSS_BUCKET, key, file);
    }

    /**
     * 获取OSS图片信息
     *
     * @param imgUrl
     * @return
     */
    public static ImageInfo getImageInfo(String imgUrl) {
        try {
            String ossLink = imgUrl + "?x-oss-process=image/info";
            Response response = httpService.synGet(ossLink);
            String body = response.body().string();
            JSONObject jsonObject = JSON.parseObject(body);
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setUrl(imgUrl);
            jsonObject.forEach((key, value) -> {
                JSONObject valueObject = JSON.parseObject(JSON.toJSONString(value));
                switch (key) {
                    case "FileSize":
                        imageInfo.setSize(valueObject.getLong("value"));
                        break;
                    case "ImageHeight":
                        imageInfo.setHeight(valueObject.getLong("value"));
                        break;
                    case "ImageWidth":
                        imageInfo.setWidth(valueObject.getLong("value"));
                        break;
                    default:
                        break;
                }
            });
            return imageInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取视频封面图
     *
     * @param videoUrl
     * @return
     */
    public static String getVideoCoverImg(String videoUrl) {
        if (StringUtils.isBlank(videoUrl)) {
            return "";
        }

        try {
            String ossLink = videoUrl + "?x-oss-process=video/snapshot,t_0,w_0,h_0,f_jpg,m_fast";
            Response response = httpService.synGet(ossLink);
            byte[] bytes = response.body().bytes();
            InputStream inputStream = new ByteArrayInputStream(bytes);

            StringBuffer key = new StringBuffer();
            key.append(UUID.randomUUID().toString()).append(".jpg");

            uploadFileStream(key.toString(), inputStream);

            return Constant.OSS_HOST + key.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
