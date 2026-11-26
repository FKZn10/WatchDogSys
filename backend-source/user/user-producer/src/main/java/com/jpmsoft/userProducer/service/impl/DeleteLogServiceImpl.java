package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.constants.ResMsg;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.DeleteLogDto;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaKnowledgeBaseFileModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.DeleteLogMapper;
import com.jpmsoft.userProducer.pojo.entity.DeleteLog;
import com.jpmsoft.userProducer.pojo.entity.SysFile;
import com.jpmsoft.userProducer.service.DeleteLogService;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import com.jpmsoft.userProducer.service.ISysFileService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:26
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class DeleteLogServiceImpl extends ServiceImpl<DeleteLogMapper, DeleteLog> implements DeleteLogService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource(name = "restTemplate")
    private RestTemplate restTemplate;
    @Resource
    private RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse insertDeleteLog(DeleteLogDto model) {
        String primaryId = model.getId();
        DeleteLog entity;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new DeleteLog();
            BeanUtil.copyProperties(model, entity);
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setCreateTime(DateTime.now());
            entity.setDeleteTime(DateTime.now());
            primaryId = GenerateUUID.getUUID();
            entity.setId(primaryId);
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
            //创建流程
            if (sysFileService.FileHelper(entity, model, "create", model.getFileRelationId())) {

            } else {
                return BaseResponse.fail(BaseMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(model, entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getDeleteLogList(DeleteLogDto request) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<DeleteLogDto> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<DeleteLogDto> params = new QueryMap<>(request);
        params.put("deleteSourceID").put("deleteFunctionCode");
        List<DeleteLogDto> deleteLogList = baseMapper.getDeleteLogList(params.getQuery(), page);
        log.info("deleteLogList:{}", JacksonUtils.toJSONString(deleteLogList));
        return BaseResponse.ok(deleteLogList, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDeleteLogById(String id) {
        DeleteLogDto deleteLogDto = baseMapper.getDeleteLogById(id);
        if (deleteLogDto == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}", deleteLogDto);
        return BaseResponse.ok(deleteLogDto);
    }

    @Override
    public BaseResponse approveData(DeleteLogDto request) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        SysFile fileEntity;
        DeleteLog deleteLog = baseMapper.selectById(request.getId());
        if (deleteLog == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        fileEntity = sysFileService.getById(request.getId());
        /**
         * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
         * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
         */
        String fileFlowStatus = fileEntity.getFileflowstatus();


        deleteLog.setState(Integer.valueOf(fileFlowStatus));
        deleteLog.setStateTime(new Date());
        baseMapper.updateById(deleteLog);
        BaseResponse baseResponse = null;
        //流程结束更新对应功能数据
        if (Objects.equals(deleteLog.getState(), ApprovalStatusEnum.Already_Over.getIndex()) && StrUtil.isNotEmpty(deleteLog.getDeleteSourceId())) {
            switch (deleteLog.getDeleteFunctionCode()) {
                case "OaKnowledgeBaseFile":
                    baseResponse = setOaKnowledgeBaseFile(deleteLog);
                    break;
                default:
                    baseResponse = BaseResponse.ok();
                    break;
            }
        } else {
            baseResponse = BaseResponse.ok();
        }
        return baseResponse;
    }

    private BaseResponse setOaKnowledgeBaseFile(DeleteLog deleteLog) {
        OaKnowledgeBaseFileModel request = new OaKnowledgeBaseFileModel();
        request.setKbfId(deleteLog.getDeleteSourceId());
        HttpEntity<String> formEntity = assemblyRequestParam(request);
        String url = "http://zhbg-server/v1/OA/KnowledgeFile/removeOaKnowledgeBaseFileById";
        BaseResponse baseResponse = restTemplate.postForObject(url, formEntity, BaseResponse.class);
        return baseResponse;
    }

    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        String token = redisUtils.getReqToken();
        if (ValidateUtil.isNotEmpty(token))
            headers.add("token", token);
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:" + JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}
