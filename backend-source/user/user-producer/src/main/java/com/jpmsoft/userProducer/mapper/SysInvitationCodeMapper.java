package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.SysInvitationCodeDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInvitationCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 邀请码
 */
@Mapper
public interface SysInvitationCodeMapper extends BaseMapper<SysInvitationCode> {
    /**
     * 锁定创建人的用户记录，串行处理同一用户的生成请求。
     *
     * @param userId 当前登录用户 ID
     * @return 存在的用户 ID，用户不存在时返回 null
     */
    String lockInvitationCodeCreator(@Param("userId") String userId);

    /**
     * 统计用户未过期的邀请码，过期时间为空也计入数量。
     *
     * @param userId 当前登录用户 ID
     * @return 未过期数量
     */
    long countUnexpiredInvitationCodes(@Param("userId") String userId);

    /**
     * 获取数据库当前时间，统一生成和过期判断的时间来源。
     *
     * @return 数据库当前时间
     */
    java.util.Date getInvitationCodeCurrentTime();

    /**
     * 按创建人及可选项目 ID、项目名称等条件分页查询邀请码，返回项目 ID、名称并计算是否已过期。
     *
     * @param page    分页参数，同时接收筛选结果总条数
     * @param request 筛选条件，pId 精确匹配、pName 模糊匹配，均可不传；createUid 由服务端设置
     * @return 按创建时间倒序排列的邀请码 DTO 列表
     */
    List<SysInvitationCodeDTO> getInvitationCodeList(@Param("page") IPage<SysInvitationCodeDTO> page,
                                                     @Param("request") SysInvitationCodeDTO request);
}
