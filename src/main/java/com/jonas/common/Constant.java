package com.jonas.common;

/**
 * 【 常量类 】
 *
 * @author shenjy 2018/07/19
 */
public class Constant {
    /**
     * 账号AK信息请填写(必选)
     */
    public static final String ACCESS_KEY_ID = "LTAIfWYurnARVa80";

    /**
     * 账号AK信息请填写(必选)
     */
    public static final String ACCESS_KEY_SECRET = "3xqtScsCMXc0Oa54zI0dMB6SdjUf2S";

    /**
     * 点播服务所在的Region，国内请填cn-shanghai，不要填写别的区域
     */
    public static final String REGION_ID = "cn-shanghai";

    /**
     * STS授权服务角色arn
     */
    public static final String STS_ROLE_ARN = "acs:ram::1769920393160884:role/zhangxiaofan";

    /**
     * STS endpoint
     */
    public static final String STS_ENDPOINT = "sts.aliyuncs.com";

    /**
     * STS 角色权限
     */
    public static final String STS_ROLE_POLICY = "{\n" +
            "  \"Statement\": [\n" +
            "    {\n" +
            "      \"Action\": \"sts:AssumeRole\",\n" +
            "      \"Effect\": \"Allow\",\n" +
            "      \"Principal\": {\n" +
            "        \"RAM\": [\n" +
            "          \"acs:ram::1769920393160884:root\"\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"Version\": \"1\"\n" +
            "}";
}
