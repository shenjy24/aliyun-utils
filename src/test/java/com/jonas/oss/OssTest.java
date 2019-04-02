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
            OssUtil.upload("img/" + file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
