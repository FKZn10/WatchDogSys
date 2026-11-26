package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecFormDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFormDetail;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 17:01
 */
public interface ISecFormDetailService extends IService<SecFormDetail> {

    BaseResponse createOrUpdateSecFormDetail(SecFormDetailDTO request);

    BaseResponse getSecFormDetailList(SecFormDetailDTO request);

    BaseResponse deleteSecFormDetailById(String id);

    BaseResponse getSecFormDetailLatestList(String teId);

    void createOrUpdateSecFormDetail(List<SecFormDetailDTO> data, String fid,String teId);

    void deleteByFid(String fid);


    List<SecFormDetailDTO> getByFid(String fid,String state);

    List<SecFormDetailDTO> getByFids(List<String> fIds);
}
