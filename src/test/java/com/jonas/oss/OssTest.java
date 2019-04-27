package com.jonas.oss;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2018/08/17
 */
public class OssTest {

    @Test
    public void testUpload() {
        try {
            String fileName = "/Users/shenjy/Documents/resource/video/卡鲁.mp4";
            File file = new File(fileName);
            OssUtil.uploadFileStream("img/" + file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetImageInfo() {
        String imgUrl = "https://shenjy.oss-cn-shanghai.aliyuncs.com/img/129741004_15107012483351n.jpg";
        System.out.println(OssUtil.getImageInfo(imgUrl));
    }

    @Test
    public void testGetVideoCoverImg() {
        String videoUrl = "https://shenjy.oss-cn-shanghai.aliyuncs.com/vod/%E7%8E%8B%E8%80%85%E8%8D%A3%E8%80%80%E6%A8%AA%E5%B1%8F.mp4";
        System.out.println(OssUtil.getVideoCoverImg(videoUrl));
    }

    @Test
    public void testGetVideoInfo() {
        String videoUrl = "https://shenjy.oss-cn-shanghai.aliyuncs.com/vod/%E7%8E%8B%E8%80%85%E8%8D%A3%E8%80%80%E6%A8%AA%E5%B1%8F.mp4";
        String coverImg = OssUtil.getVideoCoverImg(videoUrl);
        System.out.println(OssUtil.getImageInfo(coverImg));
    }
}
