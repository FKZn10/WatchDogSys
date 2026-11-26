package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegister;
import com.jpmsoft.projectProducer.pojo.vo.project.ProProjectRegisterVO;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectRegisterStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目登记主表持久化接口。
 */
public interface ProProjectRegisterMapper extends BaseMapper<ProProjectRegister> {

    /**
     * 锁定项目登记行，使同一项目的保存校验和写入在事务中串行执行。
     *
     * @param id 项目登记主键
     * @return 登记记录，不存在时为 null
     */
    ProProjectRegister selectByIdForUpdate(@Param("id") String id);

    /**
     * 按筛选条件分页查询登记主表。
     *
     * @param page   分页参数
     * @param params 筛选条件
     * @return 当前页记录
     */
    List<ProProjectRegister> getProProjectRegisterList(@Param("page") Page<ProProjectRegister> page,
                                                       @Param("params") Map<String, Object> params);

    /**
     * 查询登记主表及单据附件分组。
     *
     * @param id 登记ID
     * @return 登记详情
     */
    ProProjectRegisterVO getProProjectRegisterById(@Param("id") String id);

    /**
     * 统计授权范围内的项目登记状态。
     *
     * @param params 后端组装的单位权限及业务类型参数
     * @return 状态统计，空表返回零
     */
    ProjectRegisterStatisticsVO countRegistrationStatistics(@Param("params") Map<String, Object> params);
}
