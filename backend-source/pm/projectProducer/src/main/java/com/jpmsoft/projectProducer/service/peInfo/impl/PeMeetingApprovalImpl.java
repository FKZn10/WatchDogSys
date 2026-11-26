package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.PeInfo.PeMeetingApprovalRequest;
import com.jpmsoft.common.model.response.peInfo.PeMeetingApprovalRes;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeMeetingApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingApproval;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingApprovalListService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMeetingApprovalService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:14
 */
@Service
public class PeMeetingApprovalImpl extends ServiceImpl<PeMeetingApprovalMapper, PeMeetingApproval> implements IPeMeetingApprovalService {


    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IPeMeetingApprovalListService peMeetingApprovalListService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeMeetingApproval(PeMeetingApprovalRequest request) {
        PeMeetingApproval pojo = new PeMeetingApproval();
        BeanUtils.copyProperties(request, pojo);
        if (ValidateUtil.isEmpty(request.getMaId())) {
            pojo.setMaCreateTime(new Date());
            pojo.setMaId(GenerateUUID.getUUID());
            pojo.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(pojo);
            sysFileService.saveFile(pojo.getMaId(), request.gettId(), pojo.getState() + "", pojo.getMaCreateUid(), pojo.getMaCreateUname()
                    , pojo.getMaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMaMinutesMeeting()), pojo.getMaId());


        } else {
            PeMeetingApproval entity = baseMapper.selectById(request.getMaId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(entity.getState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            pojo.setMaCreateTime(entity.getMaCreateTime());
            pojo.setMaCreateUid(entity.getMaCreateUid());
            pojo.setMaCreateUname(entity.getMaCreateUname());
            baseMapper.updateById(pojo);
            sysFileService.saveFile(pojo.getMaId(), request.gettId(), pojo.getState() + "", pojo.getMaCreateUid(), pojo.getMaCreateUname()
                    , pojo.getMaCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMaMinutesMeeting()),
                    Arrays.asList(request.getFileIds(), request.getMaMinutesMeetingIds()), pojo.getMaId());

        }
        peMeetingApprovalListService.createOrUpdatePMAList(request.getPeIds(), pojo.getMaId());
        return BaseResponse.ok(pojo.getMaId());
    }

    @Override
    public List<PeMeetingApprovalRes> getPeMeetingApprovalList(PeMeetingApprovalRequest request, String uId) {
        Map<String, Object> params = getFilter(request, uId);
        return baseMapper.getPeMeetingApprovalList(params);
    }

    @Override
    public Integer getPeMeetingApprovalCount(PeMeetingApprovalRequest request, String uId) {
        Map<String, Object> params = getFilter(request, uId);
        return baseMapper.getPeMeetingApprovalCount(params);
    }

    @Override
    public PeMeetingApprovalRes getPeMeetingApprovalById(String maId) {
        return baseMapper.getPeMeetingApprovalById(maId);
    }

    @Override
    public BaseResponse updatePeStateByMaId(String peState, String maId) {
        SysFile fileObj = sysFileService.getById(maId);
        if (fileObj != null) {
            if (fileObj.getFileflowstatus().equals("7")) {
                Map<String, Object> params = new HashMap<>();
                params.put("peState", peState);
                params.put("maId", maId);
                Integer count = baseMapper.updatePeStateByMaId(params);
            }
        }
        return BaseResponse.ok(maId);
    }

    private Map<String, Object> getFilter(PeMeetingApprovalRequest request, String uId) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("sortFiled", ValidateUtil.isNotEmpty(request.getSortFiled()) ? request.getSortFiled() : "Ma_Create_Time");
        params.put("sortType", ValidateUtil.isNotEmpty(request.getSortType()) ? request.getSortType() : "DESC");
        params.put("teId", ValidateUtil.isNotEmpty(request.getTeId()) ? request.getTeId() : null);
        params.put("maTitle", ValidateUtil.isNotEmpty(request.getMaTitle()) ? request.getMaTitle() : null);
        params.put("maAssessmentType", ValidateUtil.isNotEmpty(request.getMaAssessmentType()) ? request.getMaAssessmentType() : null);
        params.put("maCreateUname", ValidateUtil.isNotEmpty(request.getMaCreateUname()) ? request.getMaCreateUname() : null);
        params.put("state", ValidateUtil.isNotNull(request.getState()) ? request.getState() : null);
        return params;
    }

}
