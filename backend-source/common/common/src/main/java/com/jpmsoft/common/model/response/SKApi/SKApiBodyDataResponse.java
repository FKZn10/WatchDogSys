package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

import java.util.List;

@Data
public class SKApiBodyDataResponse<T> {
    private String ResultCode;
    private String ResultMsg;
    private String NextTag;
    private List<T> ResultSet;
}
