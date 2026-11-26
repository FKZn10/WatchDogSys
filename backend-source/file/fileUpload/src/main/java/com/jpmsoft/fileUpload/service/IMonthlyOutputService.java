package com.jpmsoft.fileUpload.service;


import com.jpmsoft.common.base.BaseResponse;

import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IMonthlyOutputService {
//    BaseResponse changeMonthlyOutputByInvId(String ipvId);

    BaseResponse importMonthlyOutput(MultipartFile file,String token);

    void insertData(String year, List<Map<String, String>> allRowData, LogUserInfoVo userInfo,String token);
}
