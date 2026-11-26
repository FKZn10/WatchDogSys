package com.jpmsoft.projectProducer.service.zdyxy.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionDrawingModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.zdyxy.ConstructionDrawingMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionDrawing;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.zdyxy.ConstructionDrawingService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConstructionDrawingServiceImpl
    extends ServiceImpl<ConstructionDrawingMapper, ConstructionDrawing>
        implements ConstructionDrawingService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    @CodeGeneration(key = "id", codeParam = "constructionDrawingCode", method = "setConstructionDrawingCode", codeRule = "ConstructionDrawing", beanClass = ConstructionDrawingModel.class)
    public BaseResponse saveOrUpdateData(ConstructionDrawingModel model) {
        if (model == null){
            return BaseResponse.fail("缺少请求参数");
        }
        if (StrUtil.isEmpty(model.getDesignContractAttachmentFile())){
            return BaseResponse.fail("缺少设计合同附件");
        }
        if (StrUtil.isEmpty(model.getConstructionDrawingAttachmentFile())){
            return BaseResponse.fail("缺少施工图纸附件");
        }
        if (StrUtil.isEmpty(model.getInitiatingUnit())){
            return BaseResponse.fail("缺少发起单位");
        }
        if (StrUtil.isEmpty(model.getInitiatingPerson())){
            return BaseResponse.fail("缺少发起人");
        }
        if (model.getInitiatingTime() == null){
            return BaseResponse.fail("缺少发起时间");
        }
        if (StrUtil.isEmpty(model.getInitiatingPersonPhone())){
            return BaseResponse.fail("缺少发起人联系方式");
        }
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(model));
        ConstructionDrawing entity;
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto;
        if(StrUtil.isNotEmpty(model.getConstructionDrawingAttachmentFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(model.getConstructionDrawingAttachmentFile());
            supportDocumentDto.setSupportDocumentFileIDs(model.getConstructionDrawingAttachmentFileId());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(model.getDesignContractAttachmentFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(model.getDesignContractAttachmentFile());
            supportDocumentDto.setSupportDocumentFileIDs(model.getDesignContractAttachmentFileId());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(model.getConstructionDrawingFinalVersionFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(model.getConstructionDrawingFinalVersionFile());
            supportDocumentDto.setSupportDocumentFileIDs(model.getConstructionDrawingFinalVersionFileId());
            documentDtos.add(supportDocumentDto);
        }
        String primaryId = model.getId();
        log.info("primaryId:{}", primaryId);
        if(StrUtil.isEmpty(primaryId)){
            List<ConstructionDrawing> list = baseMapper
                    .selectList(new QueryWrapper<ConstructionDrawing>()
                            .eq("construction_drawing_code", model.getConstructionDrawingCode())
                            .ne("state", ApprovalStatusEnum.Invalid.getIndex()));
            if(CollectionUtil.isNotEmpty(list)){
                return BaseResponse.fail("该施工图编号已存在，请更换其他编号");
            }
            primaryId = IdWorker.getGenerateID();
            entity = new ConstructionDrawing();
            BeanUtils.copyProperties(model,entity);
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setStateTime(DateTime.now());
            entity.setState(flowStatus);
            saveFile(entity, documentDtos);
            //创建流程
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(model.getFileRelationId(), entity.getId());
            if(!sysFileService.FileHelper(entity,model,"create",model.getFileRelationId())){
                return BaseResponse.fail("新增失败");
            }
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(model,entity);
            saveFile(entity, documentDtos);
            sysFileAnnexService.refreshRelationFile(model.getFileRelationId(), model.getFileIds(), entity.getId());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(ConstructionDrawing entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(
                    Collections.singletonList(supportDocumentDto.getSupportDocument()),
                    Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }
    @Override
    public BaseResponse approvalData(ConstructionDrawingModel request) {
        String id = request.getId();
        ConstructionDrawing entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null) {
                return new BaseResponse("信息不存在，请刷新重试！");
            }

            fileEntity = sysFileService.getById(id);
            /*
              流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
              5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(ConstructionDrawingModel request, String token) {
        String authSql = sysUserService.getAuthSql("construction_drawing", redisUtils.getUid(token), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<ConstructionDrawingModel> queryMap = new QueryMap<>(request);
        queryMap.put("initiatingUnitName").put("initiatingPersonName").put("constructionDrawingName").put("authSql", authSql)
                .put("constructionDrawingCode").put("initiatingPersonPhone").put("state").put("isSelect").put("createTimeRange");
        IPage<ConstructionDrawingModel> page = new Page<>(request.getPage(), request.getSize());
        List<ConstructionDrawingModel> list = baseMapper.getList(queryMap.getQuery(),page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String id) {
        ConstructionDrawingModel model = baseMapper.getDetail(id);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }
}