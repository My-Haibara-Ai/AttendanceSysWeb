package com.zmy.demo.util;

/**
 * 统一接口的返回值类型
 */
@lombok.Data
public class Result {
    private int code;           // 状态码,标识接口调用的情况
    private boolean success;    // 是否成功,表示接口调用是否成功
    private String info;        // 返回消息,简要描述接口调用的情况
    private Object data;        // 返回数据,接口返回给前端的数据

    public Result(int code, boolean success, String info, Object data) {
        this.code = code;
        this.success = success;
        this.info = info;
        this.data = data;
    }
}
