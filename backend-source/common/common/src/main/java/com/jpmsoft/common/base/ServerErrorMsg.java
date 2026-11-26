package com.jpmsoft.common.base;


/**
 * 服务内部错误
 * 全局异常捕获消息返回定义
 */
public class ServerErrorMsg {

    //异常出现的文件
    private String file;
    //异常的代码行数
    private Integer line;
    //异常时执行的方法名
    private String method;
    //异常的exception message
    private String msg;
    //服务名
    private String server;


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
