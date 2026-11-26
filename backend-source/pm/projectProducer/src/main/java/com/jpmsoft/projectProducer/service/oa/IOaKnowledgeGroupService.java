package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeGroupDTO;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaKnowledgeRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeGroup;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:50
 */
public interface IOaKnowledgeGroupService extends IService<OaKnowledgeGroup> {

    BaseResponse createOrUpdateOaKnowledgeGroupBath(List<OaKnowledgeGroupDTO> request,String teId);

    void copyParentKnowledgeBaseGroup(String parentKbId, String kbId);

    BaseResponse getOaKnowledgeGroupByKbId(String kbId);

    void deleteGroupByKbId(String kbId);

    /**
     * 删除指定目录下 指定的部门或用户权限
     *
     * @param kbIds    目录ID集合
     * @param groupIds 部门ID或用户ID集合
     */
    void deleteGroupByKbIdAndGroupId(List<String> kbIds, List<String> groupIds);

    List<OaKnowledgeRoleDTO> getUserOaKnowledgePower(LogUserInfoVo user);
}
