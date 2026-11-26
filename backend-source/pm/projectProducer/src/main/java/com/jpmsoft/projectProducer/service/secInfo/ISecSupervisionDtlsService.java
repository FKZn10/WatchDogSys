package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSupervisionDtlsDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSupervisionDtls;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:49
 */
public interface ISecSupervisionDtlsService extends IService<SecSupervisionDtls> {

    void createOrUpdateSecSupervisionDtls(List<SecSupervisionDtlsDTO> data,String scsId);

    void deleteByScsId(String scsId);
}
