package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvBudgetDeclareAddMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvBudgetDeclareAddDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvBudgetDeclareAdd;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareAddService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 15:14
 */
@Service
public class InvBudgetDeclareAddImpl extends FileImpl<InvBudgetDeclareAddMapper, InvBudgetDeclareAdd, InvBudgetDeclareAddDTO> implements IInvBudgetDeclareAddService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public BaseResponse createOrUpdate(InvBudgetDeclareAddDTO request) {
        InvBudgetDeclareAdd entity = new InvBudgetDeclareAdd();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getInvBudgetDeclareAddList(InvBudgetDeclareAddDTO request, String uid) {
        String authSql = sysUserService.getAuthSql(null, uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<InvBudgetDeclareAddDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode").put("state").put("createUname");
        IPage<InvBudgetDeclareAddDTO> page = new Page<>(request.getPage(), request.getSize());
        List<InvBudgetDeclareAddDTO> datas = baseMapper.getInvBudgetDeclareAddList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvBudgetDeclareAddById(String id) {
        return BaseResponse.ok(baseMapper.getInvBudgetDeclareAddById(id));
    }

    @Override
    public BaseResponse approve2AddProBudget(String id) {
        InvBudgetDeclareAdd entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProject project = preProjectService.getById(entity.getPId());
        project.setpApproveBudget(MathUtil.decAdd(project.getpApproveBudget(), entity.getBdaMoney()));
        //项目数据完整性验证
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        project.setSign(apiUtilsService.sign(ied, ied.getProjectSign(), project));
        preProjectService.updateById(project);
        return BaseResponse.ok();
    }
}
