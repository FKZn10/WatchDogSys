package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

@Data
public class SKApiBodyResponse<T> {
    public SKApiBodyDataResponse<T> Data;
    public String Sign;
}
