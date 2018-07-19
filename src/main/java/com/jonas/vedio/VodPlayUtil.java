package com.jonas.vedio;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.jonas.common.Constant;

import java.util.List;

/**
 * 【 阿里云视频点播播放SDK 】
 *
 * @author shenjy 2018/07/19
 */
public class VodPlayUtil {

    private static final String VIDEO_ID = "2cf85350547844a797ce604f2b7e58f3";

    public static void main(String[] args) {
        DefaultAcsClient client = initVodClient(Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        GetPlayInfoResponse response = getPlayInfo(VIDEO_ID, client);
        List<GetPlayInfoResponse.PlayInfo> playInfos = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfos) {
            System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL());
        }
        System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle());
        System.out.println("RequestId = " + response.getRequestId());
    }

    /**
     * 初始化客户端
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
     * 获取视频播放地址
     * @param videoId
     * @param client
     * @return
     */
    public static GetPlayInfoResponse getPlayInfo(String videoId, DefaultAcsClient client) {
        try {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }
}
