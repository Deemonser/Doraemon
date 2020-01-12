package com.deemons.baselib.net.model;

/**
 * author： Deemo
 * date:    2020-01-12
 * desc:
 */
public class PayOrder {


    public String return_code;

    public String return_msg;

    /**
     * 以下return_code为SUCCESS时候返回
     */
    public String appid;

    public String mch_id;

    public String device_info;

    public String nonce_str;

    public String sign;

    public String result_code;

    public String err_code;

    public String err_code_des;

    /**
     * 以下return_code和result_code都为SUCCESS时候有返回
     */
    public String trade_type;

    public String prepay_id;
}
