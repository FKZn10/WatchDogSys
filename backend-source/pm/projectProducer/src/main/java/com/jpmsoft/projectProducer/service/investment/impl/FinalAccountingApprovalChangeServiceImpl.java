package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.FinalAccountingApprovalchangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApproval;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApprovalChange;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalChangeVo;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalChangeService;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class FinalAccountingApprovalChangeServiceImpl extends ServiceImpl<FinalAccountingApprovalchangeMapper, FinalAccountingApprovalChange> implements FinalAccountingApprovalChangeService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private FinalAccountingApprovalService finalAccountingApprovalService;

    @Override
    public BaseResponse createOrUpdatefinalApprovalChange(FinalAccountingApprovalChangeVo request, String uId) {
        String id = request.getId();
        //保存
        if(ValidateUtil.isEmpty(id)){
            id = GenerateUUID.getUUID();
            FinalAccountingApprovalChange approval = new FinalAccountingApprovalChange();
            BeanUtils.copyProperties(request,approval);
            approval.setId(id);
            approval.setStatus(1);
            approval.setCreateUid(uId);
            approval.setCreateTime(DateTime.now());
            approval.setState(ApprovalStatusEnum.Not_Started.getIndex());
            SysUser user = iSysUserService.getById(uId);
            if(ValidateUtil.isNotNull(user)){
                approval.setCreateUname(user.getUName());
            }
            baseMapper.insert(approval);
            sysFileService.saveFile(approval.getId(), request.gettId(), approval.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , approval.getCreateTime(), approval.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
//            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), audit.getId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getFaaApprovalAccountsId(), request.getFaaApprovalAuditId(), request.getFaaApprovalFileId()), approval.getId());

        }else{
            FinalAccountingApprovalChange approval = baseMapper.selectById(id);
            if(ValidateUtil.isNull(approval)){
                return BaseResponse.fail("未查询到数据");
            }
            FinalAccountingApprovalChange entity = new FinalAccountingApprovalChange();
            BeanUtils.copyProperties(request,entity);
            SysFile fileEntity;
            fileEntity = sysFileService.getById(id);
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getStatus() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(),request.getFaaApprovalAccountsId(),
                            request.getFaaApprovalAuditId(),request.getFaaApprovalFileId()),
                    Arrays.asList(request.getFileIds(),request.getFaaApprovalAccountsIds(),
                            request.getFaaApprovalAuditIds(),request.getFaaApprovalFileIds()), approval.getId());
        }
        return BaseResponse.ok(id);
    }

    @Override
    public BaseResponse getfinalApprovalChangeById(String id) {
        FinalAccountingApprovalChangeVo vo =  baseMapper.getfinalApprovalChangeById(id);
        return BaseResponse.ok(vo);
    }

    @Override
    public BaseResponse changeFinalApproval(String id) {
         FinalAccountingApprovalChange change = baseMapper.selectById(id);
         if(ValidateUtil.isNull(change)) return BaseResponse.fail("未查询到数据");
         if(!change.getState().equals(7)) return BaseResponse.fail("流程未审批通过,不可变更数据");
        FinalAccountingApproval approval = new FinalAccountingApproval();
        BeanUtils.copyProperties(change,approval);
        approval.setId(change.getFaaId());
         FinalAccountingApproval finala = finalAccountingApprovalService.getById(change.getFaaId());
        approval.setCreateTime(finala.getCreateTime());
        approval.setCreateUid(finala.getCreateUid());
        approval.setCreateUname(finala.getCreateUname());
        finalAccountingApprovalService.updateById(approval);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getfinalApprovalChangeList(FinalAccountingApprovalChangeVo request, String uid) {
        Page<FinalAccountingApprovalChangeVo> page = new Page<>(request.getPage(),request.getSize());
        Page<FinalAccountingApprovalChangeVo> list =  baseMapper.getfinalApprovalChangeList(page,request);
        return BaseResponse.ok(list.getRecords(),(int)list.getTotal());
    }
}
