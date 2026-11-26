package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.SysFileTemplatePrintDto;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplatePrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysFileTemplatePrintService extends IService<SysFileTemplatePrint> {
    BaseResponse createOrUpdateFileTemplatePrint(SysFileTemplatePrintDto request,String customerId);

    BaseResponse deleteFileTemplatePrintBytpId(String tpId,String customerId);

    BaseResponse getFileTemplatePrintList(SysFileTemplatePrintDto request,String customerId);
    List<SysFileTemplatePrint> getFileTemplatePrintList(String tId, String customerId);
}
