package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConTemplateMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTemplateDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTemplate;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConTemplateVo;
import com.jpmsoft.projectProducer.service.contract.IConTemplateService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 10:06
 */
@Service
public class ConTemplateServiceImpl extends ServiceImpl<ConTemplateMapper, ConTemplate> implements IConTemplateService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createConTemplate(ConTemplateDto request) {
        ConTemplate entity = new ConTemplate();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        entity.setCreateTime(new Date());
        baseMapper.insert(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
        sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getContFile()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateConTemplate(ConTemplateDto request) {
        ConTemplate entity = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), null, request.gettCode(), entity.getConId());
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getContFile()), Arrays.asList(request.getFileIds(), request.getContFileIds()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConTemplateList(ConTemplateDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Template", uId, "Create_Uid", OutUserAuthEnum.Only_Inner.getIndex(), "state");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("conName", ValidateUtil.isEmpty(request.getConName()) ? null : request.getConName());
        params.put("pName", ValidateUtil.isEmpty(request.getPName()) ? null : request.getPName());
        params.put("pId", ValidateUtil.isEmpty(request.getpId()) ? null : request.getpId());
        params.put("conType", ValidateUtil.isEmpty(request.getConType()) ? null : request.getConType());
        params.put("contName", ValidateUtil.isEmpty(request.getContName()) ? null : request.getContName());
        params.put("contVersion", ValidateUtil.isEmpty(request.getContVersion()) ? null : request.getContVersion());
        params.put("state", ValidateUtil.isEmpty(request.getState()) ? null : request.getState());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("contCode", ValidateUtil.isEmpty(request.getContCode()) ? null : request.getContCode());
        params.put("contEnable", ValidateUtil.isEmpty(request.getContEnable()) ? null : request.getContEnable());
        params.put("createUname", ValidateUtil.isEmpty(request.getCreateUname()) ? null : request.getCreateUname());
        IPage<ConTemplateVo> page=new Page<>(request.getPage(),request.getSize());
        List<ConTemplateVo> datas = baseMapper.getConTemplateList(page,params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConTemplateById(String id) {
        ConTemplateVo data = baseMapper.getConTemplateById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateConTemplateHistory(ConTemplateDto request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        boolean update = this.update(Wrappers.<ConTemplate>lambdaUpdate()
                .eq(ConTemplate::getContMarKey, request.getContMarKey())
                .ne(ConTemplate::getId, request.getId())
                .set(ConTemplate::getContNew, request.getContNew())
        );
        if (!update) {
            return BaseResponse.fail(ResMsg.actError);
        }
        return BaseResponse.ok();
    }
}




