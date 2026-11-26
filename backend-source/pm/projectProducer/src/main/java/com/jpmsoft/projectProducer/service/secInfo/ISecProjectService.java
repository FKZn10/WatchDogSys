package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.SecInfo.SecProjectRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProject;

import java.util.List;
import java.util.Map;

public interface ISecProjectService extends IService<SecProject> {

    void createOrUpdateSecProject(List<SecProjectRequest> datas, String secId, String secInfoCreateUid);

    void createOrUpdateSecProjectByBath(List<String> pIds,List<SecProjectRequest> secProjectList, List<String> secIds, String secInfoCreateUid);

    void deleteAllOldByBpId(String bpId);

    /**
     * 根据项目分组查询标段
     * 先获取项目分组后对应的Sec_Id
     *
     * @return
     */
    List<Map<String, String>> getSecInfoListGroupByProject(Map<String, Object> params);


    Integer getSecInfoListGroupByProjectCount(Map<String, Object> params);

    /**
     * 检查标段项目是否完成指定建设程序的办理
     *
     * @param secId
     * @param cpdCodes
     * @return
     */
    Boolean checkSecProjectCp(String secId, List<String> cpdCodes);

    List<SecProjectResponse> getSecInfoProject(String secId);
}
