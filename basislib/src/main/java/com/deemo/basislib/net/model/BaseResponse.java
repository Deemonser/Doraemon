package com.deemo.basislib.net.model;

/**
 * author： deemo
 * date:    2019-07-20
 * desc:    网络请求基本响应体
 */
public class BaseResponse<T> {

    private int code;
    private String msg;
    private T data;

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isNotSuccess() {
        return code != 0;
    }

    /**
     * 错误码大于 1 ，则展示给用户
     */
    public boolean isShowErrorMsg() {
        return code > 1;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

}
