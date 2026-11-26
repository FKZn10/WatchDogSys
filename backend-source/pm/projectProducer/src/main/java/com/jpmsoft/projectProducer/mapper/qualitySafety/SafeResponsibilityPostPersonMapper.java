package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostPersonDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPostPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 安全责任书岗位人员
 */
@Mapper
public interface SafeResponsibilityPostPersonMapper extends BaseMapper<SafeResponsibilityPostPerson> {
    /**
     * 分页筛选安全责任岗位人员，并返回项目、合同名称。
     *
     * @param page    分页参数
     * @param request 筛选条件
     * @return 岗位人员列表
     */
    List<SafeResponsibilityPostPersonDTO> getPostPersonList(@Param("page") IPage<SafeResponsibilityPostPersonDTO> page, @Param("request") SafeResponsibilityPostPersonDTO request);

    /**
     * 根据ID查询安全责任岗位人员详情。
     *
     * @param id 人员记录ID
     * @return 岗位人员详情
     */
    SafeResponsibilityPostPersonDTO getPostPersonById(@Param("id") String id);
}
