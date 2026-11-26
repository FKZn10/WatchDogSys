package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkRecordRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;


public interface IProjectCpWorkRecordService extends IService<ProjectCpWorkRecord> {

    BaseResponse createOrUpdateProCpWorkRecord(ProjectCpWorkRecordRequest request);

    BaseResponse deleteProCpWorkRecordById(String cpwrId);

    BaseResponse getProCpWorkRecordList(ProjectCpWorkRecordRequest request, LogUserInfoVo user);


    Integer getCpWorkRecordCountByCpdId(String cpdId);

    Integer getCpWorkRecordCountByCpId(String cpId);
}
