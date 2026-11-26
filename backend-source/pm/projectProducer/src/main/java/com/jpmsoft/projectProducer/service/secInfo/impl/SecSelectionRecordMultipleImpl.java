package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordMultipleModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSelectionRecordMultipleMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractExpertMultipleDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecordMultiple;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractMultipleVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SecSelectionRecordMultipleImpl extends ServiceImpl<SecSelectionRecordMultipleMapper, SecSelectionRecordMultiple> implements ISecSelectionRecordMultipleService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISecSelectionRecordService secSelectionRecordService;
    @Resource
    private ISecJGSelectionRecordService secJGSelectionRecordService;
    @Resource
    private ISecExpertShieldService secExpertShieldService;
    @Resource
    private ISecJGExpertShieldService secJGExpertShieldService;
    @Resource
    private ISecExpertService secExpertService;
    @Resource
    private ISecJGExpertService secJGExpertService;
    @Resource
    private ISysUserService sysUserService;

    private SecSelectionRecordMultiple createOrUpdateEntity(SecSelectionRecordMultipleModel request, LogUserInfoVo user){
        Integer expertDbType = request.getExpertDbType();
        if(expertDbType==null){
            throw new JPMException("专家库类型不能为空");
        }
        SecSelectionRecordMultiple entity = new SecSelectionRecordMultiple();
        BeanUtils.copyProperties(request, entity);
        String srId = "";
        BaseResponse baseResponse = null;
        if(expertDbType==1){
            baseResponse = secSelectionRecordService.createOrUpdateSecSelectionRecord(request.getSecSelectionRecordModel(), user);
        }else{
            baseResponse =  secJGSelectionRecordService.createOrUpdateSecJGSelectionRecord(request.getSecJGSelectionRecordModel(), user);
        }
        if(baseResponse.getSuccess()){
            Object data = baseResponse.getData();
            srId= data.toString();
        }
        entity.setSrId(srId);
        if(StrUtil.isBlank(request.getId())){
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setState(ValidateUtil.isNull(request.getState()) ? ApprovalStatusEnum.Not_Started.getIndex() : request.getState());
            entity.setStateTime(new Date());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());

        }else{
            SecSelectionRecordMultiple pojo = baseMapper.selectById(request.getId());
            if (pojo == null)
                throw new JPMException(ResMsg.infoNotExist);
            entity.setCreateTime(pojo.getCreateTime());
            entity.setCreateUid(pojo.getCreateUid());
            entity.setCreateUname(pojo.getCreateUname());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        return entity;
    }

    @Override
    public BaseResponse createOrUpdateSecSelectionRecordMultiple(SecSelectionRecordMultipleModel request, LogUserInfoVo user) {
        SecSelectionRecordMultiple secSelectionRecordMultiple = createOrUpdateEntity(request, user);
        return BaseResponse.ok(secSelectionRecordMultiple.getId());
    }

    @Override
    public BaseResponse getSecSelectionRecordMultipleList(SecSelectionRecordMultipleModel request, String uid) {
        String authSql = sysUserService.getAuthSql("Sec_Selection_Record_Multiple", uid, "create_uid", OutUserAuthEnum.Approver_Creator.getIndex(), "state", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<SecSelectionRecordMultipleModel> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("secName").put("state")
                .put("currentUid",uid).put("authSql", authSql);
        IPage<SecSelectionRecordMultipleModel> page = new Page<>(request.getPage(), request.getSize());
        List<SecSelectionRecordMultipleModel> datas = baseMapper.getSecSelectionRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecSelectionRecordMultipleById(String id) {
        SecSelectionRecordMultipleModel data = new SecSelectionRecordMultipleModel();
        SecSelectionRecordMultiple entity = baseMapper.getSecSelectionRecordById(id);
        BeanUtil.copyProperties(entity, data);
        if(data.getExpertDbType()==1){
            BaseResponse baseResponse = secSelectionRecordService.getSecSelectionRecordById(data.getSrId());
            if(baseResponse.getSuccess()){
                SecSelectionRecordModel secSelectionRecordModel= (SecSelectionRecordModel) baseResponse.getData();
                data.setSecSelectionRecordModel(secSelectionRecordModel);
            }
        }else {
            BaseResponse baseResponse = secJGSelectionRecordService.getSecJGSelectionRecordById(data.getSrId());
            if(baseResponse.getSuccess()){
                SecJGSelectionRecordModel secJGSelectionRecordModel= (SecJGSelectionRecordModel) baseResponse.getData();
                data.setSecJGSelectionRecordModel(secJGSelectionRecordModel);
            }
        }

        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSecSelectionRecordMultipleById(String id) {
        SecSelectionRecordMultiple pojo = baseMapper.selectById(id);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if(pojo.getExpertDbType()==1){
           secSelectionRecordService.deleteSecSelectionRecordById(pojo.getSrId());
        }else{
           secJGSelectionRecordService.deleteSecJGSelectionRecordById(pojo.getSrId());
        }
        baseMapper.deleteById(id);
        sysFileService.deleteFileById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse createOrUpdateExpertExtractMultiple(SecSelectionRecordMultipleModel request, LogUserInfoVo user) {
        SecSelectionRecordMultiple entity = createOrUpdateEntity(request, user);
        if(request.getExpertDbType()==1){
            secExpertShieldService.createOrUpdateSecExpertShield(request.getSecSelectionRecordModel().getSecExpertShields(), entity.getId());
        }else{
            secJGExpertShieldService.createOrUpdateSecExpertShield(request.getSecJGSelectionRecordModel().getSecJGExpertShields(), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecExpertExtractMultipleById(String id) {
        SecExpertExtractMultipleVo secExpertExtractMultipleVo = baseMapper.getSecExpertExtractById(id);
        if(secExpertExtractMultipleVo.getExpertDbType()==1){
            BaseResponse baseResponse = secSelectionRecordService.getSecExpertExtractById(secExpertExtractMultipleVo.getSrId());
            if(baseResponse.getSuccess()){
                SecExpertExtractVo secExpertExtractVo = (SecExpertExtractVo) baseResponse.getData();
                secExpertExtractMultipleVo.setSecExpertExtractVo(secExpertExtractVo);
            }
        }else{
            BaseResponse baseResponse = secJGSelectionRecordService.getSecJGExpertExtractById(secExpertExtractMultipleVo.getSrId());
            if(baseResponse.getSuccess()){
                SecJGExpertExtractVo secJGExpertExtractVo = (SecJGExpertExtractVo) baseResponse.getData();
                secExpertExtractMultipleVo.setSecJGExpertExtractVo(secJGExpertExtractVo);
            }
        }

        return BaseResponse.ok(secExpertExtractMultipleVo);
    }

    @Override
    public BaseResponse extractJGExpertMultiple(ExtractExpertMultipleDto request, String teId) {
        Integer expertDbType = request.getExpertDbType();
        if(expertDbType==null){
            throw new JPMException("专家库类型不能为空");
        }
        if(expertDbType==1){
           return secExpertService.extractExpert(request.getExtractExpertDto(), teId);
        }else{
           return secJGExpertService.extractJGExpert(request.getExtractJGExpertDto(), teId);
        }
    }

    @Override
    public BaseResponse approvalData(SecSelectionRecordMultipleModel request) {
        if (ValidateUtil.isEmpty(request.getId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        SecSelectionRecordMultiple entity = baseMapper.getSecSelectionRecordById(request.getId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        SysFile fileEntity;
        fileEntity = sysFileService.getById(request.getId());
        //流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
        String fileFlowStatus = fileEntity.getFileflowstatus();
        if(entity.getExpertDbType()==1){
            secSelectionRecordService.updateStateById(Integer.valueOf(fileFlowStatus),entity.getSrId());
        }else{
            secJGSelectionRecordService.updateStateById(Integer.valueOf(fileFlowStatus),entity.getSrId());
        }
        entity.setState(Integer.valueOf(fileFlowStatus));
        entity.setStateTime(new Date());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getOtherExpertSecInfo(String secId) {
        SecSelectionRecordMultiple secSelectionRecordMultiple = baseMapper.getLatestData(secId);
        Map<String, Object> map = new HashMap<>();
        Date secBidTime = null;
        String secLoaction = "";
        if(secSelectionRecordMultiple!=null && secSelectionRecordMultiple.getExpertDbType()!=null){
            if(secSelectionRecordMultiple.getExpertDbType()==1){
                BaseResponse baseResponse = secSelectionRecordService.getSecSelectionRecordById(secSelectionRecordMultiple.getSrId());
                if(baseResponse.getSuccess()){
                    SecSelectionRecordModel secSelectionRecordModel = (SecSelectionRecordModel) baseResponse.getData();
                    secBidTime = secSelectionRecordModel.getSrBidTime();
                    secLoaction = secSelectionRecordModel.getSrLocation();
                }
            }else{
                BaseResponse baseResponse = secJGSelectionRecordService.getSecJGSelectionRecordById(secSelectionRecordMultiple.getSrId());
                if(baseResponse.getSuccess()){
                    SecJGSelectionRecordModel secJGSelectionRecordModel = (SecJGSelectionRecordModel) baseResponse.getData();
                    secBidTime = secJGSelectionRecordModel.getSrBidTime();
                    secLoaction = secJGSelectionRecordModel.getSrLocation();
                }
            }
        }
        String secBidTimeStr = "";
        if(secBidTime!=null){
            secBidTimeStr = DateUtil.format(secBidTime, "yyyy-MM-dd HH:mm");
        }
        map.put("secBidTime",secBidTimeStr);
        map.put("secLoaction",secLoaction);

        return BaseResponse.ok(map);
    }


}
