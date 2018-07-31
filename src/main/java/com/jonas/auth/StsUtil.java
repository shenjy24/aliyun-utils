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
            //在RAM控制台的角色管理页面RAM控制台的角色管理列表中，进入角色详情页可以查看一个角色的RoleArn。
            request.setRoleArn(Constant.STS_ROLE_ARN);
            //用户自定义参数。此参数用来区分不同的Token，可用于用户级别的访问审计。
            request.setRoleSessionName("sts-role");
            //授权策略Policy 语法结构，Policy长度限制为1024字节；您可以通过此参数限制生成的Token的权限，不指定则返回的Token将拥有指定角色的所有权限。
            request.setPolicy(Constant.STS_ROLE_POLICY);
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
