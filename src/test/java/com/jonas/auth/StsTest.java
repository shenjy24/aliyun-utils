package com.jonas.auth;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import org.junit.Test;

/**
 * 【 enter the class description 】
 *
 * @author shenjy 2018/12/03
 */
public class StsTest {

    @Test
    public void testSts() {
        AssumeRoleResponse response = StsUtil.getStsAuthInfo();
        System.out.println("AccessKeyId: " + response.getCredentials().getAccessKeyId());
        System.out.println("AccessKeySecret: " + response.getCredentials().getAccessKeySecret());
        System.out.println("Token: " + response.getCredentials().getSecurityToken());
    }
}
