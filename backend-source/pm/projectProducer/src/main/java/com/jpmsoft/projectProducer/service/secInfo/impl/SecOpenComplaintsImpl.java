package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecOpenComplaintsMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecOpenComplaintsDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenComplaints;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecOpenComplaintsVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenComplaintsService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/11/28 13:52
 */
@Service
public class SecOpenComplaintsImpl extends BaseFileImpl<SecOpenComplaintsMapper, SecOpenComplaints, SecOpenComplaintsDto> implements ISecOpenComplaintsService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateSecOpenComplaints(SecOpenComplaintsDto request) {
        SecOpenComplaints entity = new SecOpenComplaints();
        if (ValidateUtil.isEmpty(request.getPId()) && ValidateUtil.isNotEmpty(request.getSecProjects()) && request.getSecProjects().size() == 1) {
            request.setPId(request.getSecProjects().get(0).getpId());
        }
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecOpenComplaintsList(SecOpenComplaintsDto request) {
        QueryMap<SecOpenComplaintsDto> query = new QueryMap<>(request);
        query.put("socQuestion").put("secId").put("socQuestionerTelephone").put("createUname").put("socQuestionQuestion").put("socRespondent").put("socReplyContent").put("socRemarks").put("pName").put("secBiddingType").put("secName").put(request.getCreateTimes(), "createTimeStart", "createTimeEnd");
        IPage<SecOpenComplaintsVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecOpenComplaintsVo> datas = baseMapper.getSecOpenComplaintsList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecOpenComplaintsById(String id) {
        return BaseResponse.ok(baseMapper.getSecOpenComplaintsById(id));
    }

}
