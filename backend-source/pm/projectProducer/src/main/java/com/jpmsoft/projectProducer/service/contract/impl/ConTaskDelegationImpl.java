package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConTaskDelegationMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTaskDelegationDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTaskDelegation;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTaskDelegationVo;
import com.jpmsoft.projectProducer.service.contract.IConTaskDelegationService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 13:58
 */
@Service
public class ConTaskDelegationImpl extends BaseFileImpl<ConTaskDelegationMapper, ConTaskDelegation, ConTaskDelegationDto>
        implements IConTaskDelegationService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public BaseResponse createOrUpdateConTaskDelegation(ConTaskDelegationDto request) {
        String codeRule = setCode(request);
        ConTaskDelegation entity = new ConTaskDelegation();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        if (ValidateUtil.isNotEmpty(codeRule)) apiUtilsService.refershAutoNo(codeRule, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConTaskDelegationList(ConTaskDelegationDto request, String uId) {
        String authSql;
        if (ValidateUtil.isEmpty(request.getNoAuth())) {
            authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        } else {
            authSql = " where 1=1 ";
        }
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConTaskDelegationDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("pName").put("conName").put("ctdCode").put("ctdType")
                .put("ctdProType").put("state").put("createUname").put("ctdProNo").put("ctdSuperviseNo")
                .put("ctdContacts").put("ctdSoutContacts").put("ctdSampler").put("ctdWitness").put("conAnnualName")
                .put("conId");
        IPage<ConTaskDelegationVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConTaskDelegationVo> datas = baseMapper.getConTaskDelegationList(page, query.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getConTaskDelegationById(String id) {
        ConTaskDelegationVo data = baseMapper.getConTaskDelegationById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getConTaskDelegationByConId(String conId) {
        return BaseResponse.ok(baseMapper.getConTaskDelegationByConId(conId));
    }


    /**
     * 编码生成
     * 不同委托类型规则不同
     *
     * @param request
     */
    private String setCode(ConTaskDelegationDto request) {
        //region 编码生成 不同委托类型配置不同的编码规则
        String seqCode = "ConTaskDelegation" + request.getCtdType();
        if (ValidateUtil.isEmpty(request.getId()) && apiUtilsService.sequenceRestEnable(seqCode)) {
            //非历史合同才生成编码
            request.setCtdCode(apiUtilsService.generateCodeByTCode(seqCode, request));
            if (ValidateUtil.isEmpty(request.getCtdCode())) throw new JPMException(ResMsg.codeGenerError);
            return seqCode;
        }
        if (ValidateUtil.isEmpty(request.getCtdCode())) throw new JPMException(ResMsg.codeIsEmpty);
        //endregion
        return null;
    }
}
