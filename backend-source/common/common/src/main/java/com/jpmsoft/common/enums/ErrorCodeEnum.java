package com.jpmsoft.common.enums;

import com.jpmsoft.common.base.BaseResService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.ServerErrorMsg;

/**
 * 服务内部异常消息声明
 */
public enum ErrorCodeEnum implements BaseResService {

    AllException(100201, "请求错误，请联系管理员!"),
    SQLException(100202, "请求错误(Bad SQL)，请联系管理员!"),
    ConnectException(100203, "请求错误(请求或连接失败)，请联系管理员！"),
    IntegrityConstraint(100204, "存在关联数据，操作失败！"),
    NotFoundFieldOrTable(100205, "数据表结构异常，请联系管理员！"),
    HttpMessageNotReadable(100206,"数据格式错误！"),
    MaxUploadSizeExceededException(100207,"附件大小超出限制！");


    private Integer code;
    private Boolean success;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.success = false;
        this.msg = msg;
    }

    @Override
    public BaseResponse errorReturn(ServerErrorMsg msg) {
        return new BaseResponse(this.code, this.success, this.msg, msg);
    }

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
