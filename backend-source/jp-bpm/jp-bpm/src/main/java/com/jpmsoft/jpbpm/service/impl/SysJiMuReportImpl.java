package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysJiMuReportMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplatePrint;
import com.jpmsoft.jpbpm.pojo.entity.SysJiMuReport;
import com.jpmsoft.jpbpm.service.SysFileTemplatePrintService;
import com.jpmsoft.jpbpm.service.SysJiMuReportService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class SysJiMuReportImpl extends ServiceImpl<SysJiMuReportMapper, SysJiMuReport> implements SysJiMuReportService {
    @Autowired
    SysFileTemplatePrintService sysFileTemplatePrintService;

    @Transactional
    @Override
    public void createSysJiMuReport(String fileId,String tCode,String tId,String customerId) {
        try{
        List<SysFileTemplatePrint> pList=sysFileTemplatePrintService.getFileTemplatePrintList(tId,customerId);
        if(CollectionUtils.isNotEmpty(pList)){
            for(SysFileTemplatePrint p:pList){
                SysJiMuReport obj=new SysJiMuReport();
                obj.setId(GenerateUUID.getUUID());
                obj.setCreateTime(new Date());
                obj.setFileId(fileId);
                obj.setTCode(tCode);
                obj.setJmId(p.getTpId());
                baseMapper.insert(obj);
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void createSysJiMuReportBatch(List<String> fileIds,String tCode,String tId,String customerId) {
        try{
            List<SysFileTemplatePrint> pList=sysFileTemplatePrintService.getFileTemplatePrintList(tId,customerId);
            if(CollectionUtils.isNotEmpty(pList)){
                for(SysFileTemplatePrint p:pList){
                    if(CollectionUtils.isNotEmpty(fileIds)){
                        for(String fileId:fileIds){
                            SysJiMuReport obj=new SysJiMuReport();
                            obj.setId(GenerateUUID.getUUID());
                            obj.setCreateTime(new Date());
                            obj.setFileId(fileId);
                            obj.setTCode(tCode);
                            obj.setJmId(p.getTpId());
                            baseMapper.insert(obj);
                        }
                    }


                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
