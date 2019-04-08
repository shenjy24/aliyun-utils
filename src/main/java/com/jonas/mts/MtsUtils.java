package com.jonas.mts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.mts.model.v20140618.QueryMediaListByURLRequest;
import com.aliyuncs.mts.model.v20140618.QueryMediaListByURLResponse;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;
import com.jonas.common.Constant;
import com.jonas.utils.HttpService;
import com.jonas.utils.impl.HttpServiceImpl;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 视频处理工具类
 *
 * @author shenjy 2019/01/10
 */
public class MtsUtils {

    private static final String ossInputObject = "video/古剑奇谭横屏.mp4";

    private static final String ossOutputObject = "video/古剑奇谭转码.mp4";

    private static IAcsClient client;

    static {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 提供媒体处理任务
     */
    public static void submitJob() {
        SubmitJobsRequest request = new SubmitJobsRequest();

        JSONObject input = new JSONObject();
        input.put("Location", "oss-cn-hangzhou");
        input.put("Bucket", "shenjy-in");

        try {
            input.put("Object", URLEncoder.encode(ossInputObject, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        request.setInput(input.toJSONString());

        String outputOSSObject = "";
        try {
            outputOSSObject = URLEncoder.encode(ossOutputObject, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        JSONObject output = new JSONObject();
        output.put("OutputObject", outputOSSObject);

        // Ouput->TemplateId
        output.put("TemplateId", "9b4cbbeaccbe4e0ea35826c3149a2c61");

        JSONArray outputs = new JSONArray();
        outputs.add(output);

        request.setOutputs(outputs.toJSONString());
        request.setOutputBucket("shenjy-out");
        request.setOutputLocation("oss-cn-hangzhou");
        //管道ID
        request.setPipelineId("22fd74b65c5a4843a3a30e0e31846e3e");

        // 发起请求并处理应答或异常
        SubmitJobsResponse response;

        try {
            response = client.getAcsResponse(request);
            System.out.println("RequestId is: " + response.getRequestId());
            if (response.getJobResultList().get(0).getSuccess()) {
                System.out.println("JobId is:" + response.getJobResultList().get(0).getJob().getJobId());
            } else {
                System.out.println("SubmitJobs Failed code:" + response.getJobResultList().get(0).getCode() +
                        " message:" + response.getJobResultList().get(0).getMessage());
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    public static void getMediaInfo() throws UnsupportedEncodingException, ClientException {
//        HttpService httpService = new HttpServiceImpl();
//
//        Map<String, String> input = Maps.newHashMap();
//        input.put("Bucket", "shenjy");
//        input.put("Location", "oss-cn-hangzhou");
//        input.put("Object", "vod/古剑奇谭横屏.mp4");
//
//        Map<String, Object> params = Maps.newHashMap();
//        params.put("Action", "SubmitMediaInfoJob");
//        params.put("Input", JSON.toJSONString(input));
//
//        String url = "http://mts.cn-hangzhou.aliyuncs.com";
//        Response response = httpService.synGet(url, params);
//        if (null != response && response.isSuccessful()) {
//            try {
//                System.out.println(response.body().string());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        QueryMediaListByURLRequest request = new QueryMediaListByURLRequest();
        String ossHost = "http://shenjy.oss-cn-shanghai.aliyuncs.com/";
        String ossObject = "vod/古剑奇谭横屏.mp4";
        String rfc3986Object = encodeByRFC3986(ossObject);

        request.setFileURLs(ossHost + rfc3986Object);
        request.setSysProtocol(ProtocolType.HTTPS);

        QueryMediaListByURLResponse response = client.getAcsResponse(request);
        System.out.println(JSONObject.toJSONString(response.getMediaList()));
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
            getMediaInfo();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
