package com.zhiliao.hotel.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * @author xiegege
 * @date 2020/5/20
 * @param <T>
 */
public class ReturnString<T> {

    /**
     * 0：成功 -1：失败
     */
    private int code = 0;

    private String message = "数据获取成功";

    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ReturnString(T data) {
        this.data = (T) JSON.parse(JSON.toJSONString(data, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }

    public ReturnString(String message) {
        this.code = -1;
        this.message = message;
    }

    public ReturnString(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnString(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = (T) JSON.parse(JSON.toJSONString(data, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
    }

    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null) {
                return "";
            }
            return v;
        }
    };
}
