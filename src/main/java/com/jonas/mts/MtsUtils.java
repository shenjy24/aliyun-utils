package com.jonas.mts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.HttpClientConfig;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.mts.model.v20140618.QueryMediaListByURLRequest;
import com.aliyuncs.mts.model.v20140618.QueryMediaListByURLResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.jonas.common.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * 视频处理工具类
 *
 * @author shenjy 2019/01/10
 */
public class MtsUtils {

    private static DefaultAcsClient client;

    static {
        DefaultProfile profile = DefaultProfile.getProfile(Constant.MPS_REGION_ID, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }

    public static List<QueryMediaListByURLResponse.Media> getMediaInfo(String ossHost, String ossObject) {
        try {
            QueryMediaListByURLRequest request = new QueryMediaListByURLRequest();
            String rfc3986Object = encodeByRFC3986(ossObject);
            request.setFileURLs(ossHost + rfc3986Object);
            QueryMediaListByURLResponse response = client.getAcsResponse(request);
            return response.getMediaList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    private static String encodeByRFC3986(String object) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        String[] segments = object.split("/");
        for (int i = 0; i < segments.length; i++) {
            builder.append(percentEncode(segments[i]));
            if (i != segments.length - 1) {
                builder.append("/");
            }
        }
        return builder.toString();
    }

    private static String percentEncode(String value) throws UnsupportedEncodingException {
        if (value == null) {
            return null;
        }
        return URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    public static void main(String[] args) {
        try {
            String ossHost = "http://shenjy-in.oss-cn-hangzhou.aliyuncs.com/";
            String ossObject = "video/卡鲁.mp4";
            List<QueryMediaListByURLResponse.Media> medias = MtsUtils.getMediaInfo(ossHost, ossObject);
            System.out.println(JSON.toJSONString(medias));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
