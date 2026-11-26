package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.fileUpload.pojo.entity.SecExpert;

import java.util.List;

public interface ISecExpertService extends IService<SecExpert> {


    BaseResponse importSecExpert(List<SecExpertModel> datas, String uId, String uName);

    /**
     * 专家选取记录查询专家库
     *
     * @param srId
     * @return
     */
    List<SecExpert> getSecExpertBySrId(String srId);
}
