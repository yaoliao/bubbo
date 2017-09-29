package com.bubbo;

/**
 * Created by DELL on 2017/9/27.
 */
public class RpcResponse {

    private String requestID;
    private Throwable error;
    private Object result;

    public RpcResponse() {
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
