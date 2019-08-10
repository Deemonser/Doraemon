package com.deemo.basislib.net.model;

import java.io.Serializable;

/**
 * author： deemo
 * date:    2019-07-20
 * desc:
 */
public class UserBean implements Serializable {


    /**
     * credit_status	integer($int32)
     * 授信状态码:1. 授信中;2. 人工审核;3. 授信成功;4. 授信失败;5. 不在授信服务区
     * <p>
     * fb_email	string
     * 用户facebook邮箱
     * <p>
     * first_login_type	integer($int32)
     * 用户首次登录方式:facebook:1;gmail:2;sms:3;
     * <p>
     * gg_email	string
     * 用户google邮箱
     * <p>
     * mobile	string
     * 用户手机号码
     * <p>
     * self_email	string
     * 用户自有邮箱
     * *
     * userStatus	integer($int32)
     * 用户状态：1正常/2冻结
     * <p>
     * useruserTag	integer($int32)
     * 用户标签：1.测试用户/2,白名单/3.黑名单
     * <p>
     * access_token : gyg88f8xgjo5furql0
     */


    public int creditStatus;
    public String fbEmail;
    public int firstLoginType;
    public String ggEmail;
    public String mobile;
    public String selfEmail;
    public int userStatus;
    public int userTag;
    public String access_token;
}
