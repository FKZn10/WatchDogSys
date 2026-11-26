package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysInvitationCodeMapper;
import com.jpmsoft.userProducer.pojo.dto.InvitationCodeConfigDTO;
import com.jpmsoft.userProducer.pojo.dto.SysInvitationCodeDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInvitationCode;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysInvitationCodeService;
import com.jpmsoft.userProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 邀请码
 */
@Service
public class SysInvitationCodeImpl extends ServiceImpl<SysInvitationCodeMapper, SysInvitationCode> implements ISysInvitationCodeService {
    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    /**
     * 为当前用户在指定项目下生成邀请码；事务内锁定用户后检查该用户所有项目的未过期总数量并保存。
     * 配置 invitationCodeConfig 必须包含正整数 maxUnexpiredCount 和 expireDays。
     *
     * @param pId 关联项目 ID，必填
     * @return 新生成的邀请码 DTO 或项目参数、用户、配置、数量限制的失败提示
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public BaseResponse generateInvitationCode(String pId) {
        if (pId == null || pId.trim().isEmpty()) {
            return BaseResponse.fail("项目 ID 不能为空");
        }
        LogUserInfoVo user = UserUtils.getUserInfo();
        if (user == null || user.getId() == null || user.getId().trim().isEmpty()) {
            return BaseResponse.fail("未获取到当前登录用户信息");
        }
        String configJson = sysConfigEntityService.getSysConfigByCode("invitationCodeConfig");
        if (configJson == null || configJson.trim().isEmpty()) {
            return BaseResponse.fail("请配置 invitationCodeConfig 邀请码生成规则");
        }
        InvitationCodeConfigDTO config = JacksonUtils.parseObject(configJson, InvitationCodeConfigDTO.class);
        if (config == null || config.getMaxUnexpiredCount() == null || config.getMaxUnexpiredCount() < 1
                || config.getExpireDays() == null || config.getExpireDays() < 1) {
            return BaseResponse.fail("invitationCodeConfig 配置无效，maxUnexpiredCount 和 expireDays 必须为正整数");
        }
        if (baseMapper.lockInvitationCodeCreator(user.getId()) == null) {
            return BaseResponse.fail("当前登录用户不存在");
        }
        if (baseMapper.countUnexpiredInvitationCodes(user.getId()) >= config.getMaxUnexpiredCount()) {
            return BaseResponse.fail("未过期邀请码数量已达到上限：" + config.getMaxUnexpiredCount());
        }
        Date now = baseMapper.getInvitationCodeCurrentTime();
        SysInvitationCode entity = new SysInvitationCode();
        entity.setId(GenerateUUID.getUUID());
        entity.setCode(GenerateUUID.getUUID());
        entity.setPId(pId.trim());
        entity.setCreateUid(user.getId());
        entity.setCreateUname(user.getName());
        entity.setCreateTime(now);
        entity.setExpiredTime(new Date(Math.addExact(now.getTime(), TimeUnit.DAYS.toMillis(config.getExpireDays()))));
        if (baseMapper.insert(entity) != 1) {
            throw new IllegalStateException("邀请码保存失败");
        }
        SysInvitationCodeDTO result = new SysInvitationCodeDTO();
        BeanUtils.copyProperties(entity, result);
        result.setExpired(false);
        return BaseResponse.ok(result);
    }

    /**
     * 按可选项目 ID、项目名称及其他条件分页查询当前用户的邀请码，返回项目 ID、名称和过期标识。
     *
     * @param request 筛选条件及分页参数，pId 精确匹配、pName 模糊匹配，均可不传；创建人固定为当前用户
     * @return 包含当前页邀请码 DTO 列表及筛选结果总条数的响应
     */
    @Override
    public BaseResponse getInvitationCodeList(SysInvitationCodeDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getPId() != null) {
            request.setPId(request.getPId().trim());
        }
        if (request.getPName() != null) {
            request.setPName(request.getPName().trim());
        }
        if (request.getPage() == null || request.getPage() < 1
                || request.getSize() == null || request.getSize() < 1) {
            return BaseResponse.fail("page 和 size 必须为正整数");
        }
        if (request.getCreateTimeStart() != null && request.getCreateTimeEnd() != null
                && request.getCreateTimeStart().after(request.getCreateTimeEnd())) {
            return BaseResponse.fail("创建时间范围起点不能晚于终点");
        }
        if (request.getExpiredTimeStart() != null && request.getExpiredTimeEnd() != null
                && request.getExpiredTimeStart().after(request.getExpiredTimeEnd())) {
            return BaseResponse.fail("过期时间范围起点不能晚于终点");
        }
        LogUserInfoVo user = UserUtils.getUserInfo();
        if (user == null || user.getId() == null || user.getId().trim().isEmpty()) {
            return BaseResponse.fail("未获取到当前登录用户信息");
        }
        request.setCreateUid(user.getId());
        IPage<SysInvitationCodeDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SysInvitationCodeDTO> list = baseMapper.getInvitationCodeList(page, request);
        return BaseResponse.ok(list, page.getTotal());
    }
}
