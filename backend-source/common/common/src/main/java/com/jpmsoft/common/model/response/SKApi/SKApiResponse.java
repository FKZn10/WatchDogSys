package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

@Data
public class SKApiResponse<T> {
    public SKApiCmscloudResponse<T> Cmscloud;
}
