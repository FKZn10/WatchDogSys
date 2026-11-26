package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaKnowledgeBaseFileMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaKnowledgeBaseFile;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseFileService;
import com.jpmsoft.projectProducer.service.oa.IOaKnowledgeBaseService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class OaKnowledgeBaseFileImpl extends ServiceImpl<OaKnowledgeBaseFileMapper, OaKnowledgeBaseFile> implements IOaKnowledgeBaseFileService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IOaKnowledgeBaseService oaKnowledgeBaseService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOaKnowledgeBaseFile(OaKnowledgeBaseFileModel request) {
        OaKnowledgeBaseFile entity = new OaKnowledgeBaseFile();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getKbfId())) {
            entity.setKbfId(GenerateUUID.getUUID());
            entity.setKbfCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getKbfCover(), request.getKbfFile()), entity.getKbfId());

        } else {
            OaKnowledgeBaseFile model = baseMapper.selectById(entity.getKbfId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setKbfCreateTime(model.getKbfCreateTime());
            entity.setKbfCreateUid(model.getKbfCreateUid());
            entity.setKbfCreateUname(model.getKbfCreateUname());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getKbfCover(), request.getKbfFile()),
                    Arrays.asList(request.getKbfCoverIds(), request.getKbfFileIds()), entity.getKbfId());

        }
        return BaseResponse.ok(entity.getKbfId());
    }

    @Override
    public BaseResponse getOaKnowledgeBaseFileList(OaKnowledgeBaseFileModel request) {
        QueryMap<OaKnowledgeBaseFileModel> params = new QueryMap<>(request);
        params.put("kbfType").put("kbfState").put("kbfName").put("kbfTitle").put("partyAsUnitName")
                .put("kbfVersionNumber").put("kbfCreateUname").put("kbfCode").put("kbfPublishDate0").put("kbfPublishDate1");
        List<String> kbIds = null;
        if (!ValidateUtil.isEmpty(request.getKbId())) {
            kbIds = oaKnowledgeBaseService.getAllChildsIds(request.getKbId());
        }
        List<OaKnowledgeBaseFileModel> datas = baseMapper.getOaKnowledgeBaseFileList(params.getQuery(), kbIds);
        Integer total = baseMapper.getOaKnowledgeBaseFileListCount(params.getQuery(), kbIds);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getOaKnowledgeBaseFileAllList(String teId) {
        return BaseResponse.ok(baseMapper.getOaKnowledgeBaseFileAllList(teId), 1);
    }


    @Override
    public BaseResponse deleteOaKnowledgeBaseFileById(String kbfId) {
        OaKnowledgeBaseFile model = baseMapper.selectById(kbfId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(kbfId);
        return BaseResponse.ok();
    }

    @Override
    public Integer getOaKnowledgeBaseFileCountByKbId(String kbId) {
        QueryWrapper<OaKnowledgeBaseFile> wrapper = new QueryWrapper<>();
        wrapper.eq("Kb_Id", kbId);
        return baseMapper.selectCount(wrapper).intValue();
    }
}
