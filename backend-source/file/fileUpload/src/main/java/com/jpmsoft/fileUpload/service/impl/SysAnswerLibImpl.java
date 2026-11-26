package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.mapper.SysAnswerLibMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysAnswerLibDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysAnswerLib;
import com.jpmsoft.fileUpload.service.ISysAnswerLibService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.ISysQuestionSumService;
import com.jpmsoft.fileUpload.utils.lucene.Searcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/25 9:56
 */
@Service
public class SysAnswerLibImpl extends ServiceImpl<SysAnswerLibMapper, SysAnswerLib> implements ISysAnswerLibService {

    @Resource
    private ISysQuestionSumService sysQuestionSumService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private UserFeign userFeign;

    @Override
    public BaseResponse createOrUpdateSysAnswerLib(SysAnswerLibDTO request, String token) {
        SysAnswerLib entity = new SysAnswerLib();
        BeanUtils.copyProperties(request, entity);
        String codeRule = null;
        if (userFeign.sequenceRestEnable("SysAnswerLib", token) && ValidateUtil.isEmpty(request.getId())) {
            entity.setAlCode(userFeign.generateCodeByTCode("SysAnswerLib", request, token));
            if (ValidateUtil.isEmpty(entity.getAlCode()))
                return new BaseResponse("生成编码失败");
            codeRule = "SysAnswerLib";
        }
        if (ValidateUtil.isNotEmpty(request.getId())) {
            SysAnswerLib old = baseMapper.selectById(request.getId());
            if (old == null) return BaseResponse.fail(FileMsg.infoNotExist);
            entity.setCreateTime(old.getCreateTime());
            entity.setCreateUname(old.getCreateUname());
            entity.setCreateUid(old.getCreateUid());
            entity.setAlCount(old.getAlCount());
            baseMapper.updateById(entity);
        } else {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
            if (ValidateUtil.isNotEmpty(codeRule))
                userFeign.refershAutoNo(codeRule, entity, token);
        }
        //富文本文件新增时无法确认用户是否删除文件，所以最终也要根据IDS去比对
        List<Map<String, String>> params = new ArrayList<>();
        Map<String, String> param = new HashMap<>();
        param.put("fileRelationId", request.getRteFileRelationId());
        param.put("fileIds", request.getRteFileIds());
        params.add(param);
        sysFileAnnexService.refreshRelationFile(params, entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSysAnswerLibList(SysAnswerLibDTO request) {
        QueryMap<SysAnswerLibDTO> query = new QueryMap<>(request);
        query.put("problem").put("alKeys").put("alType").put("alTitle")
                .put("createUname").put("alAsked").put("alCode")
                .put("words", Searcher.wordExtraction(request.getProblem()));
        IPage<SysAnswerLibDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SysAnswerLibDTO> data = baseMapper.getSysAnswerLibList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysAnswerLibById(String id) {
        SysAnswerLib entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(FileMsg.infoNotExist);
        SysAnswerLibDTO res = new SysAnswerLibDTO();
        BeanUtils.copyProperties(entity, res);
        entity.setAlCount(entity.getAlCount() == null ? 1 : entity.getAlCount() + 1);
        return BaseResponse.ok(res);
    }

    @Override
    public BaseResponse searchProblem(SysAnswerLibDTO requset) {
        //region 数据库全文检索
        if (!ValidateUtil.isAllEmpty(requset.getProblem(), requset.getAlType(), requset.getAlKeys())) {
            List<String> words = Searcher.wordExtraction(requset.getProblem());
            if (CollectionUtils.isEmpty(words)) return BaseResponse.fail(FileMsg.ikSegmenterException);
            sysQuestionSumService.createQuestionSum(requset, Boolean.TRUE);
            return BaseResponse.ok(baseMapper.searchProblem(words, requset.getProblem(), requset.getAlType(), requset.getAlKeys()));
        }
        //endregion
        //region  lucene全文检索
        //endregion
        return BaseResponse.ok();
    }
}
