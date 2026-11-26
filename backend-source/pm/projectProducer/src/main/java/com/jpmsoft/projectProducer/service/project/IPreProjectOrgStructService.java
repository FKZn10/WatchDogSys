package com.jpmsoft.projectProducer.service.project;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectOrgStructModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectOrgStruct;

/**
 * 项目组织机构(PreProjectOrgStruct)表服务接口
 *
 * @author c
 * @since 2024-12-11 17:39:10
 */
public interface IPreProjectOrgStructService extends IService<PreProjectOrgStruct> {


    BaseResponse syncPreProjectOrgStruct(PreProjectOrgStructModel request,String uId);


    BaseResponse getPreProjectOrgStructList(PreProjectOrgStructRequest request,String uId);


    BaseResponse getPreProjectOrgStructById(String id);


    BaseResponse saveAndsynchronouSorganization(PreProjectOrgStructRequest request, String uId);

    BaseResponse changesynchronouSorganization(PreProjectOrgStructRequest request, String uid);

    BaseResponse getchangeRecords(PreProjectOrgStructRequest request);

    BaseResponse getPreProjectOrgStructChangeById(String id);

    BaseResponse saveProjectOrganization(String id);

    BaseResponse queryInspectionContract(String pId);

    BaseResponse getProjectOrganizationById(String pId);
}
