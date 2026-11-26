package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Contract.ConContractMultiProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 多项目合同项目信息
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Mapper
public interface ConContractMultiProjectMapper extends BaseMapper<ConContractMultiProject> {
    /**
     * 根据多项目合同ID获取多项目合同项目信息
     *
     * @param id
     * @return
     */
    @Select("select " +
            "mn.*, " +
            "pp.P_Name " +
            "from Con_Contract_Multi_Project mn " +
            "left join Pre_Project pp on mn.p_Id = pp.P_Id " +
            "where mn.ccm_Id = #{id}")
    List<ConContractMultiProjectResponse> getConContractMultiProjectByCcmId(@Param("id") String id);
}
