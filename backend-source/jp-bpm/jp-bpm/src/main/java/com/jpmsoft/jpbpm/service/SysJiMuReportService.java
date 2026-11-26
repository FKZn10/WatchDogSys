package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysJiMuReport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysJiMuReportService  extends IService<SysJiMuReport> {

    void createSysJiMuReport(String fileId,String tCode,String tId,String customerId);

    void createSysJiMuReportBatch(List<String> fileIds, String tCode, String tId, String customerId);

}
