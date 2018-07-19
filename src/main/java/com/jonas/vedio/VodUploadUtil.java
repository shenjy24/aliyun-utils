package com.jonas.vedio;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.jonas.common.Constant;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2018/07/19
 */
public class VodUploadUtil {


    public static void main(String[] args) {
        DefaultAcsClient client = initVodClient(Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        CreateUploadVideoResponse response = createUploadVideo(client);
        System.out.println("VideoId = " + response.getVideoId());
        System.out.println("UploadAddress = " + response.getUploadAddress());
        System.out.println("UploadAuth = " + response.getUploadAuth());
        System.out.println("RequestId = " + response.getRequestId());
    }

    /**
     * 初始化客户端
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        DefaultProfile profile = DefaultProfile.getProfile(Constant.REGION_ID, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取上传地址和凭证
     * @param client
     * @return
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) {
        try {
            CreateUploadVideoRequest request = new CreateUploadVideoRequest();
            request.setTitle("视频标题");
            request.setDescription("视频描述");
            request.setFileName("文件名称.mov");
            request.setTags("标签1,标签2");
            request.setCoverURL("/Users/shenjy/Downloads/movie.flv");
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
