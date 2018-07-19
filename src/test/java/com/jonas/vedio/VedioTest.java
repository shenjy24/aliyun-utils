package com.jonas.vedio;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.jonas.common.Constant;
import org.junit.Test;

import static com.jonas.vedio.VodUploadUtil.*;


/**
 * 【 阿里云视频点播SDK测试 】
 *
 * @author shenjy 2018/07/19
 */
public class VedioTest {

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
