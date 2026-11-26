package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SignSafetyResponDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SignSafetyRespon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 安全责任书签订
 */
@Mapper
public interface SignSafetyResponMapper extends BaseMapper<SignSafetyRespon> {

    /**
     * 分页筛选安全责任书签订记录，并返回项目、合同、单位名称
     *
     * @param page   分页参数
     * @param params 查询条件
     * @return 安全责任书签订列表
     */
    List<SignSafetyResponDTO> getSignSafetyResponList(@Param("page") IPage<SignSafetyResponDTO> page, @Param("params") Map<String, Object> params);

    /**
     * 根据ID查询安全责任书签订详情
     *
     * @param id 主键
     * @return 安全责任书签订详情
     */
    SignSafetyResponDTO getSignSafetyResponById(@Param("id") String id);
}
