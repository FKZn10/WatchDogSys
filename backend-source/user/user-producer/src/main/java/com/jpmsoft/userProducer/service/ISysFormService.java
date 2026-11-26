package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysFormDTO;
import com.jpmsoft.userProducer.pojo.entity.SysForm;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:07
 */
public interface ISysFormService extends IService<SysForm> {


    BaseResponse createOrUpdateSysForm(SysFormDTO request);

    BaseResponse getSysFormList(SysFormDTO request);

    BaseResponse getSysFormById(String id);

    BaseResponse getSysFormByCode(String code,String teId);

    BaseResponse copySysFormById(String id);
    BaseResponse importSysForm(MultipartFile file);
    BaseResponse importSysForm(List<SysFormDTO> request);
    BaseResponse createOrUpdateSysFormInText(MultipartFile file);
}
