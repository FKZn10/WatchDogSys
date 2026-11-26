package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysRegionDTO;
import com.jpmsoft.userProducer.pojo.entity.SysRegion;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 9:41
 */
public interface ISysRegionService extends IService<SysRegion> {


    BaseResponse createOrUpdateSysRegion(SysRegionDTO request);

    BaseResponse getSysRegionTree();

    BaseResponse getSysRegionByParentCode(String parentCode, Boolean exclude);

    BaseResponse getSysRegionById(String id);

    BaseResponse deleteRegionById(String id);

    BaseResponse importRegion(MultipartFile file);

    BaseResponse getSysRegionTreeNew();
}
