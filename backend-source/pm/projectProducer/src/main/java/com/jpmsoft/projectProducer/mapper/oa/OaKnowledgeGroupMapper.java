package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.DirPowerRoleDTO;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeGroup;
import com.jpmsoft.projectProducer.pojo.vo.oa.OaKnowledgeGroupVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 13:50
 */
public interface OaKnowledgeGroupMapper extends BaseMapper<OaKnowledgeGroup> {


    @Select("select * from OA_Knowledge_Group where Kb_Id=#{kbId}")
    List<OaKnowledgeGroup> getIdListByKbId(@Param("kbId") String kbId);


    List<OaKnowledgeGroupVO> getOaKnowledgeGroupByKbId(@Param("kbId") String kbId);


    List<OaKnowledgeGroupVO> getUserOaKnowledgePower(@Param("userInfo") LogUserInfoVo userInfo);

    List<DirPowerRoleDTO> getDirPowerRole(@Param("roleIds") List<String> roleIds);
}
