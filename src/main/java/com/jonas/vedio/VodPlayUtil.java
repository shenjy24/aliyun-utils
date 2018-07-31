package com.jonas.vedio;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jonas.common.Constant;

import java.util.List;

/**
 * 【 阿里云视频点播播放SDK 】
 *
 * @author shenjy 2018/07/19
 */
public class VodPlayUtil {

    private static final DefaultAcsClient client = initAcsClient(Constant.REGION_ID, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);

    /**
     * 初始化客户端
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public static DefaultAcsClient initAcsClient(String regionId, String accessKeyId, String accessKeySecret) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取视频播放地址
     * @param videoId
     * @return
     */
    public static GetPlayInfoResponse getPlayInfo(String videoId) {
        try {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取视频播放凭证
     * @param videoId
     * @return
     */
    public static GetVideoPlayAuthResponse getVideoPlayAuth(String videoId) {
        try {
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }
}
