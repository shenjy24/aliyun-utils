package com.jonas.vedio;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import org.junit.Test;

import static com.jonas.auth.StsUtil.getStsAuthInfo;
import static com.jonas.vedio.VodUploadUtil.*;

/**
 * 【 阿里云视频点播SDK测试 】
 *
 * @author shenjy 2018/07/19
 */
public class VedeoTest {

    @Test
    public void testGetStsAuthInfo() {
        AssumeRoleResponse response = getStsAuthInfo();
        System.out.println(response.getRequestId());
        System.out.println(response.getCredentials().getAccessKeyId());
        System.out.println(response.getCredentials().getAccessKeySecret());
        System.out.println(response.getCredentials().getSecurityToken());
        System.out.println(response.getCredentials().getExpiration());
        System.out.println(response.getAssumedRoleUser());
    }

    @Test
    public void testRefreshVideoUploadAuth() {
        String videoId = "9f1473ec98dc4aaab0a97e2b327f2862";
        RefreshUploadVideoResponse response = refreshVideoUploadAuth(videoId);
        if (null != response) {
            System.out.println(response.getRequestId());
            System.out.println(response.getUploadAddress());
            System.out.println(response.getUploadAuth());
        }
    }

    /**
     * VideoId = 9f1473ec98dc4aaab0a97e2b327f2862
     * UploadAddress = eyJFbmRwb2ludCI6Imh0dHBzOi8vb3NzLWNuLXNoYW5naGFpLmFsaXl1bmNzLmNvbSIsIkJ1Y2tldCI6Im91dGluLTE2ODNhNzU1OGFmNDExZThiNGEyMDAxNjNlMWMzNWQ1IiwiRmlsZU5hbWUiOiJ2aWRlby9hN2Q1NGUyLTE2NGVmNWIyMDc1LTAwMDYtNDliYi1iNDUtMGE4YjQuZmx2In0=
     * UploadAuth = eyJTZWN1cml0eVRva2VuIjoiQ0FJUzB3UjFxNkZ0NUIyeWZTaklyNGlNS1B6Z203Skl4ZnFTUlJISmdtZ2tTTnhQanZ6RHBUejJJSGhKZVhOdkJPMGV0ZjQrbVdCWTdQY1lsclVxRk1FVUZCYWNOcG9ydDhvUG9WUDlKcGZadjh1ODRZQURpNUNqUVpCbHE0cGRtSjI4V2Y3d2FmK0FVQkxHQ1RtZDVNQVlvOWJUY1RHbFFDWnVXLy90b0pWN2I5TVJjeENsWkQ1ZGZybC9MUmRqcjhsbzF4R3pVUEcyS1V6U24zYjNCa2hsc1JZZTcyUms4dmFIeGRhQXpSRGNnVmJtcUpjU3ZKK2pDNEM4WXM5Z0c1MTlYdHlwdm9weGJiR1Q4Q05aNXo5QTlxcDlrTTQ5L2l6YzdQNlFIMzViNFJpTkw4L1o3dFFOWHdoaWZmb2JIYTlZcmZIZ21OaGx2dkRTajQzdDF5dFZPZVpjWDBha1E1dTdrdTdaSFArb0x0OGphWXZqUDNQRTNyTHBNWUx1NFQ0OFpYVVNPRHREWWNaRFVIaHJFazRSVWpYZEk2T2Y4VXJXU1FDN1dzcjIxN290ZzdGeXlrM3M4TWFIQWtXTFg3U0IyRHdFQjRjNGFFb2tWVzRSeG5lelc2VUJhUkJwYmxkN0JxNmNWNWxPZEJSWm9LK0t6UXJKVFg5RXoycExtdUQ2ZS9MT3M3b0RWSjM3V1p0S3l1aDRZNDlkNFU4clZFalBRcWl5a1Qwa0ZncGZUSzFSemJQbU5MS205YmFCMjUvelcrUGREZTBkc1Znb0pWS0RwaUdXRzNSTE5uK3p0Sjl4YmtlRStzS1VsZktjcUpvL1RWWis3OTREVkZpSUlkd3hwZ1krdS9Mc3RCbksrN1MrWGkzdDRHczA5OTZmdmFzM3NCYzdKS2o4M3JYTjVHZU81Q3pCUDVOVXdwbUhCRGRkSmoyc1lHRjh6ZnlvZ1hZS21nc01pV25jT1d4RXN3bk1qem50SVpWQWlLM1dsaU1lVS8xSjVjM2NTaWE5K0Z0bkJlbUE2cTB3UmZoWWUrUkRRajQxV0wydlExdU5Hb0FCUW9kNTMxSVpjNC9Ub2tsYlZtcWV3eWQ4aldKSmVzd1hEUVAvODhMRTYydWV6KzVpOXFnOFVCNFgycmZZMlROZU1yTjl1d1hBRzFSWGcvcFF3bkFFL2g5VUNQcjFLTjNoNVhhYUVXMmcreTlpMDByMWdyVFVnMi9JTjJWYUxRNVJwMkRzVTdSOWh0OElLNVNNNFNrcnl3MERTRDh3Vk11TGpDcnlOL0RWQkdvPSIsIkFjY2Vzc0tleUlkIjoiU1RTLk5LOWNGVHZtaXI4eUc3eGZocURTY2E5aEciLCJBY2Nlc3NLZXlTZWNyZXQiOiJIbm5ZdVNzTkhmZmZGblZrSlk1UThjVmY4R1pqQm1xRFBmTTdGcXRQd29HRiIsIkV4cGlyYXRpb24iOiIzNjAwIn0=
     * RequestId = 1FFA0666-1587-4993-B0DB-B986CA25795B
     */
    @Test
    public void testGetUploadInfo() {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("变形金刚3");
        request.setDescription("变形金刚3 机器人大战");
        request.setFileName("movie.flv");
        request.setTags("汽车人,飞天虎");
        request.setCoverURL("/Users/shenjy/Downloads/movie.flv");

        CreateUploadVideoResponse response = getVideoUploadInfo(request);
        System.out.println("VideoId = " + response.getVideoId());
        System.out.println("UploadAddress = " + response.getUploadAddress());
        System.out.println("UploadAuth = " + response.getUploadAuth());
        System.out.println("RequestId = " + response.getRequestId());
    }

    @Test
    public void testUploadLocalVideo() {
        //视频标题(必选)
        String title = "测试标题";
        //1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。任何上传方式文件名必须包含扩展名
        String fileName = "/Users/shenjy/Downloads/movie.flv";
        //本地文件上传
        uploadLocalVideo(title, fileName);
    }

    @Test
    public void testUploadURLStream() {
        //视频标题(必选)
        String title = "测试标题";
        //1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。任何上传方式文件名必须包含扩展名
        String fileName = "sample.mp4";
        //待上传视频的网络流地址
        String url = "http://video.sample.com/sample.mp4";
        //网络流上传
        uploadURLStream(title, fileName, url);
    }

    @Test
    public void testUploadFileStream() {
        //视频标题(必选)
        String title = "测试标题";
        //1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。任何上传方式文件名必须包含扩展名
        String fileName = "/Users/shenjy/Downloads/movie.flv";
        //文件流上传
        uploadFileStream(title, fileName);
    }
}