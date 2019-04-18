package com.bowie.notes.framework.common;

import java.io.Serializable;

/**
 * Created by Bowie on 2019/4/17 16:38
 **/
public class Result<T> implements Serializable {

    private Integer code;
    private Boolean success;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
