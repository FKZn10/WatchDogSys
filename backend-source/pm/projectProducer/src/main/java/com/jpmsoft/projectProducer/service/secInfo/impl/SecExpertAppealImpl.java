package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecExpertAppealMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecExpertAppealDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertAppeal;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertAppealVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAppealDtlService;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertAppealService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/2 13:47
 */
@Service
public class SecExpertAppealImpl extends FileImpl<SecExpertAppealMapper, SecExpertAppeal, SecExpertAppealDTO> implements ISecExpertAppealService {

    @Resource
    private ISecExpertAppealDtlService secExpertAppealDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public BaseResponse createOrUpdateSecExpertAppeal(SecExpertAppealDTO request) {
        SecExpertAppeal entity = new SecExpertAppeal();
        createOrUpdateFileEntity(entity, request);
        secExpertAppealDtlService.createOrUpdateSecExpertAppealDtl(request.getSecExpertAppealDtls(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertAppealList(SecExpertAppealDTO request, LogUserInfoVo user) {
        String authSql = sysUserService.getAuthSql("Sec_Expert_Appeal", user.getId(), "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<SecExpertAppealDTO> queryMap = new QueryMap<>(request);
        queryMap.put("authSql", authSql).put("state").put("createUname");
        IPage<SecExpertAppealVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecExpertAppealVO> data = baseMapper.getSecExpertAppealList(page, queryMap.getQuery());
        data.forEach(d -> {
            d.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(d.getExIdNumber())));
        });
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    @Override
    public BaseResponse getSecExpertAppealById(String id) {
        SecExpertAppealVO data = baseMapper.getSecExpertAppealById(id);
        data.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(data.getExIdNumber())));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approveSecExpertAppealById(String id) {
        SecExpertAppeal data = baseMapper.selectById(id);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(data.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        secExpertAppealDtlService.approveSecExpertAppealById(id);
        return BaseResponse.ok();
    }
}
