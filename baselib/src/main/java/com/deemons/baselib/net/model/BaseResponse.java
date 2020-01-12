package com.deemons.baselib.net.model;

/**
 * author： deemo
 * date:    2019-07-20
 * desc:    网络请求基本响应体
 */
public class BaseResponse<T> {

    private int code;
    private String message;
    private T data;
    private long systemTime;

    public BaseResponse(int code, String message, T data, long systemTime) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.systemTime = systemTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                ", message='" + message + '\'' +
                '}';
    }

}
