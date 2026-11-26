package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.FinalAccountingApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingApproval;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.investment.FinalAccountingApprovalRequest;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingApprovalVo;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalChangeService;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingApprovalService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinalAccountingApprovalServiceImpl extends ServiceImpl<FinalAccountingApprovalMapper, FinalAccountingApproval> implements FinalAccountingApprovalService {


    @Autowired
    private FinalAccountingApprovalChangeService finalAccountingApprovalChangeService;
    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse getfinalApprovalById(String id) {
        FinalAccountingApprovalVo  vo =  baseMapper.getfinalApprovalById(id);
        return BaseResponse.ok(vo);
    }

    @Override
    public BaseResponse getfinalApprovalList(FinalAccountingApprovalVo request) {
        Page<FinalAccountingApproval> page = new Page<>(request.getPage(),request.getSize());
        Page<FinalAccountingApprovalVo> list = baseMapper.getfinalApprovalList(page,request);
        return BaseResponse.ok(list.getRecords(),(int)list.getTotal());
    }

    @Override
    public BaseResponse delFinalApprovalById(String id) {
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse createOrUpdatefinalApproval(FinalAccountingApprovalVo request, String uId) {
        String id = request.getId();
        //保存
        if(ValidateUtil.isEmpty(id)){
            id = GenerateUUID.getUUID();
            FinalAccountingApproval approval = new FinalAccountingApproval();
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
            FinalAccountingApproval approval = baseMapper.selectById(id);
            if(ValidateUtil.isNull(approval)){
                return BaseResponse.fail("未查询到数据");
            }

            FinalAccountingApproval entity = new FinalAccountingApproval();
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
    public BaseResponse queryApprovalListClassInfo(FinalAccountingApprovalVo request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(FinalAccountingApprovalRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        if (classInfoAnnotation.getValue().equals("pNameList")){
                            List<Object> dictionaryValues =baseMapper.getpNameValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(1);
                        }else if (classInfoAnnotation.getValue().equals("faaApprovalTime")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(2);
                        }else if (classInfoAnnotation.getValue().equals("stateList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(3);
                        }else if (classInfoAnnotation.getValue().equals("faaApprovalInvestmentList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(4);
                        }else if (classInfoAnnotation.getValue().equals("createUnameList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(5);
                        }else if (classInfoAnnotation.getValue().equals("createTime")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(6);
                        }else{
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(7);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("queryApprovalListClassInfo error:{}",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList=classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }
}
