package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConPowerAttorneyMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPowerAttorneyDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPowerAttorney;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConPowerAttorneyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 9:49
 */
@Service
public class ConPowerAttorneyImpl extends ServiceImpl<ConPowerAttorneyMapper, ConPowerAttorney> implements IConPowerAttorneyService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConContractService conContractService;
    @Resource
    private UserProducerFeign userProducerFeign;

    @Override
    public BaseResponse createOrUpdateConPowerAttorney(ConPowerAttorneyDTO request) {
        ConPowerAttorney entity = new ConPowerAttorney();
        BeanUtils.copyProperties(request, entity);
        if (existsCon(request.getConId(), request.getId()))
            return BaseResponse.fail(ResMsg.conPowerAttorneyCF);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setCreateTime(new Date());
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), entity.getPId());
        } else {
            ConPowerAttorney model = baseMapper.selectById(request.getId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(model.getCreateTime());
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId()),
                    Arrays.asList(request.getFileIds()), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConPowerAttorneyList(ConPowerAttorneyDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Con_Power_Attorney", uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConPowerAttorneyDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conId").put("pId").put("pName").put("conName")
                .put("pCode").put("paConCode").put("paAuthPerson");
        IPage<ConPowerAttorneyDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConPowerAttorneyDTO> datas = baseMapper.getConPowerAttorneyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConPowerAttorneyById(String id) {
        return BaseResponse.ok(baseMapper.getConPowerAttorneyById(id));
    }


    @Override
    public synchronized BaseResponse contractApprove2Create(String conId) {
        ConContract con = conContractService.getById(conId);
        if (con == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (Objects.equals(con.getConApprovalState(), ApprovalStatusEnum.Not_Started.getIndex())
                || Objects.equals(con.getConApprovalState(), ApprovalStatusEnum.Invalid.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("ConPowerAttorney");
        if (temp == null) return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "ConPowerAttorney"));
        if (!existsCon(conId, null)) {
            ConPowerAttorney entity = new ConPowerAttorney();
            entity.setId(GenerateUUID.getUUID());
            entity.setPId(con.getConProjectId());
            entity.setConId(conId);
            entity.setPaConCode(con.getConCode());
            entity.setCreateTime(new Date());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setStateTime(entity.getCreateTime());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), temp.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), entity.getPId(), temp.gettName(), temp.gettCode(), "create",
                    GenerateUUID.getUUID(), null, temp.gettCode(), entity.getConId());
        }
        return BaseResponse.ok();
    }


    private boolean existsCon(String conId, String id) {
        QueryWrapper<ConPowerAttorney> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        if (ValidateUtil.isNotEmpty(id))
            wrapper.ne("id", id);
        wrapper.ne("state", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

}
