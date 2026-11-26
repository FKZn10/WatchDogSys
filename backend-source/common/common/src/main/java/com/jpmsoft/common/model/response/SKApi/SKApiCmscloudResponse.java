package com.jpmsoft.common.model.response.SKApi;
import com.jpmsoft.common.model.request.Contract.SKApiHead;
import lombok.Data;

@Data
public class SKApiCmscloudResponse<T> {
    public SKApiHead Head;
    public SKApiBodyResponse<T> Body;
}
