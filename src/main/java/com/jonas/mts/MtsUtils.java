package com.jonas.mts;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.mts.model.v20140618.SubmitJobsRequest;
import com.aliyuncs.mts.model.v20140618.SubmitJobsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.jonas.common.Constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 视频处理工具类
 *
 * @author shenjy 2019/01/10
 */
public class MtsUtils {

    private static final String ossInputObject = "video/卡鲁.mp4";

    private static final String ossOutputObject = "卡鲁转码.mp4";

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile(Constant.MPS_REGION_ID, Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

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
}
