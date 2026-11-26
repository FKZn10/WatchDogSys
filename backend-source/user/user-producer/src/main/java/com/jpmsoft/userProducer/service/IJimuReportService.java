package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.JimuReportDto;
import com.jpmsoft.userProducer.pojo.entity.JimuReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IJimuReportService  extends IService<JimuReport>
{

    BaseResponse getJimuReportList(JimuReport request);

    BaseResponse getJimuReportListByIds(List<String> ids, String tenantId);

    BaseResponse importJimuReportList(JimuReportDto data, String tenantId);

    BaseResponse deleteJimuReportById(String  id);

    BaseResponse importJimuReport(MultipartFile file, String tenantId);

    BaseResponse refreshJimuDictItem(String customerId);
}
