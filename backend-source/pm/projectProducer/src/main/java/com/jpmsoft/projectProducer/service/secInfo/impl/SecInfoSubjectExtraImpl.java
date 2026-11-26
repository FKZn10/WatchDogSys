package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectExtraDTO;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoSubjectExtraMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectExtra;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectExtraChange;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectExtraChangeService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoSubjectExtraService;
import com.jpmsoft.projectProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 科目信息扩展服务实现
 */
@Service
public class SecInfoSubjectExtraImpl extends ServiceImpl<SecInfoSubjectExtraMapper, SecInfoSubjectExtra> implements ISecInfoSubjectExtraService {
    @Resource
    private ISecInfoSubjectExtraChangeService secInfoSubjectExtraChangeService;

    /**
     * 创建或编辑科目信息扩展
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse createOrUpdateSecInfoSubjectExtra(SecInfoSubjectExtraDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SecInfoSubjectExtra entity = new SecInfoSubjectExtra();
        BeanUtils.copyProperties(request, entity);
        long count = this.count(Wrappers.<SecInfoSubjectExtra>lambdaQuery()
                .eq(SecInfoSubjectExtra::getBpId, entity.getBpId())
                .eq(SecInfoSubjectExtra::getSecId, entity.getSecId())
                .eq(SecInfoSubjectExtra::getParentId, entity.getParentId())
                .ne(ValidateUtil.isNotEmpty(entity.getId()), SecInfoSubjectExtra::getId, entity.getId())
        );
        if (ValidateUtil.isNotEmpty(entity.getId())) {
            entity.setSort(Math.toIntExact(count + 1));
            this.updateById(entity);
        } else {
            LogUserInfoVo user = UserUtils.getUserInfo();
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            entity.setCreateTime(new Date());
            entity.setSort(Math.toIntExact(count + 1));
            this.save(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除科目信息扩展
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse deleteSecInfoSubjectExtraById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.removeById(id);
        return BaseResponse.ok();
    }

    /**
     * 根据secIds查询科目信息扩展
     *
     * @param secIds
     * @param bpPlanPhase
     * @return
     */
    @Override
    public List<SecInfoSubjectResponse> getSecInfoSubjectExtraBySecIds(List<String> secIds, String bpPlanPhase) {
        if (ValidateUtil.isNotEmpty(secIds)) {
            List<SecInfoSubjectExtra> extraList = this.list(Wrappers.<SecInfoSubjectExtra>lambdaQuery()
                    .in(SecInfoSubjectExtra::getSecId, secIds));
            return extraList.stream().map(extra -> {
                SecInfoSubjectResponse response = new SecInfoSubjectResponse();
                response.setId(extra.getParentId());
                response.setSecId(extra.getSecId());
                response.setSubMoney(extra.getSeMoney());
                if ("02".equals(bpPlanPhase) || "03".equals(bpPlanPhase)) {
                    response.setSubIebId(extra.getId());
                } else if ("04".equals(bpPlanPhase)) {
                    response.setSubIbbId(extra.getId());
                }
                response.setSubName(extra.getSeName());
                response.setLevelCode(extra.getLevelCode());
                return response;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 同步二类费扩展
     *
     * @param bpcId
     * @param bpId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approval2UpdateSecInfoSubjectExtra(String bpcId, String bpId) {
        // 先把自己的删除
        this.remove(Wrappers.<SecInfoSubjectExtra>lambdaQuery().eq(SecInfoSubjectExtra::getBpId, bpId));
        // 处理变更表的
        List<SecInfoSubjectExtraChange> changes = secInfoSubjectExtraChangeService.list(Wrappers.<SecInfoSubjectExtraChange>lambdaQuery().eq(SecInfoSubjectExtraChange::getBpcId, bpcId));
        if (ValidateUtil.isNotEmpty(changes)) {
            List<SecInfoSubjectExtra> extractedData = changes.stream().map(change -> {
                SecInfoSubjectExtra extra = new SecInfoSubjectExtra();
                BeanUtil.copyProperties(change, extra);
                extra.setId(GenerateUUID.getUUID());
                extra.setBpId(bpId);
                return extra;
            }).collect(Collectors.toList());
            this.saveBatch(extractedData);
        }
        return BaseResponse.ok();
    }
}
