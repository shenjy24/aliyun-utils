package com.jonas.oss;

import com.aliyun.oss.model.PutObjectResult;
import com.jonas.utils.GsonUtils;
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
            String fileName = "C:\\Users\\Administrator\\Desktop\\work\\workspace\\openfactor\\smartkit_ai\\polls\\ai\\image\\5fdc3a1d22af465194d2df058c18fd81.png";
            File file = new File(fileName);
            PutObjectResult result = OssUtil.uploadFileStream("img/" + file.getName(), new FileInputStream(file));
            System.out.println(GsonUtils.toJson(result));
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
