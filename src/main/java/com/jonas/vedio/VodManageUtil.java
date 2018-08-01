package com.jonas.vedio;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import com.google.common.base.Joiner;
import com.jonas.common.Constant;

import java.util.List;

/**
 * 【 阿里云视频点播管理SDK 】
 *
 * @author shenjy 2018/08/01
 */
public class VodManageUtil {

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
     * 获取视频信息
     * @param videoId
     * @return
     */
    public static GetVideoInfoResponse getVideoInfo(String videoId) {
        try {
            GetVideoInfoRequest request = new GetVideoInfoRequest();
            request.setVideoId(videoId);
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改视频信息
     * @param request
     * @return
     */
    public static UpdateVideoInfoResponse updateVideoInfo(UpdateVideoInfoRequest request) {
        try {
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 删除视频
     * @param videoIds
     * @return
     */
    public static DeleteVideoResponse deleteVideos(List<String> videoIds) {

        try {
            Joiner joiner = Joiner.on(",").skipNulls();
            String param = joiner.join(videoIds);

            DeleteVideoRequest request = new DeleteVideoRequest();

            //多个用逗号分隔，最多支持20个
            request.setVideoIds(param);

            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return null;
    }
}
