package com.jonas.auth;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jonas.common.Constant;

/**
 * 【 阿里云STS授权服务工具 】
 *
 * @author shenjy 2018/07/31
 */
public class StsUtil {

    /**
     *  获取STS授权
     * @return
     */
    public static AssumeRoleResponse getStsAuthInfo() {

        try {
            DefaultAcsClient  client = initAcsClient(Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(Constant.STS_ROLE_ARN);
            request.setRoleSessionName("session-name");
            return client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 初始化客户端
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     */
    public static DefaultAcsClient initAcsClient(String accessKeyId, String accessKeySecret) throws ClientException {
        DefaultProfile.addEndpoint("", "", "Sts", Constant.STS_ENDPOINT);
        IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
