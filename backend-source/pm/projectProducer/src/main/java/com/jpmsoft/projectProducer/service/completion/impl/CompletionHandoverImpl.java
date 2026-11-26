package com.jpmsoft.projectProducer.service.completion.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Completion.CompletionHandoverRequest;
import com.jpmsoft.common.model.response.Completion.CompletionHandoverResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.completion.CompletionHandoverInfoSubMapper;
import com.jpmsoft.projectProducer.mapper.completion.CompletionHandoverMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.BuildingInfoSub;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionHandover;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionHandoverInfoSub;
import com.jpmsoft.projectProducer.service.completion.IBuildingInfoSubService;
import com.jpmsoft.projectProducer.service.completion.ICompletionHandoverService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CompletionHandoverImpl extends FileImpl<CompletionHandoverMapper, CompletionHandover, CompletionHandoverRequest> implements ICompletionHandoverService {

    @Resource
    private ISysUserService sysUserService;


    @Resource
    private CompletionHandoverInfoSubMapper completionHandoverInfoSubMapper;

    @Resource
    private IBuildingInfoSubService buildingInfoSubService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateCompletionHandover(CompletionHandoverRequest request) {
        CompletionHandover entity = new CompletionHandover();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getChId());
    }

    @Override
    public BaseResponse getCompletionHandoverList(CompletionHandoverRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Ch_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Ch_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<CompletionHandoverRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("chSupervisorId")
                .put("chUserUnitId").put("chContractorId").put("chContactTel");
        IPage<CompletionHandoverResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionHandoverResponse> datas = baseMapper.getCompletionHandoverList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionHandoverById(String cfId) {
        CompletionHandoverResponse data = baseMapper.getCompletionHandoverById(cfId);
        return BaseResponse.ok(data);
    }

    // 查询审核信息并数据同步到建筑信息子表
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateBuildingInfoSub(String chId) {
        // 1. 查询项目移交主表
        CompletionHandover completionHandover = baseMapper.selectById(chId);
        if (ValidateUtil.isEmpty(completionHandover)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        Integer chState = completionHandover.getChState();
        if (chState != 7) return BaseResponse.fail("状态错误");

        LambdaQueryWrapper<CompletionHandoverInfoSub> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(CompletionHandoverInfoSub::getBirId, chId);
        List<CompletionHandoverInfoSub> data = completionHandoverInfoSubMapper.selectList(wrapper);

        if (!CollectionUtils.isEmpty(data)) {
            List<BuildingInfoSub> updates = new ArrayList<>();
            for (CompletionHandoverInfoSub c : data) {
                BuildingInfoSub b = new BuildingInfoSub();
                b.setId(c.getBisId());
                b.setBisWarrantyStart(c.getBisWarrantyStart());
                b.setBisWarrantyEnd(c.getBisWarrantyEnd());
                b.setBisWarranty(c.getBisWarranty());
                b.setBisCompany(completionHandover.getChFirmUnit());
                b.setBisFirmContact(completionHandover.getChFirmContact());
                b.setBisFirmPhone(completionHandover.getChFirmPhone());
                b.setBisNum(c.getBisNum());
                b.setBisUnit(c.getBisUnit());
                updates.add(b);
            }
            boolean updateBatchById = buildingInfoSubService.updateBatchById(updates);

            //同步失败，抛出异常
            if (!updateBatchById) {
                throw new RuntimeException("批量更新失败");
            }

            completionHandover.setBirState(1);
            baseMapper.updateById(completionHandover);
            return BaseResponse.ok("同步成功，共更新 " + updates.size() + " 条");
        }else {
            completionHandover.setBirState(1);
            baseMapper.updateById(completionHandover);
            return BaseResponse.ok("无同步数据，标记状态成功");
        }
    }


    /**
     * 定时任务：重新同步失败的数据
     * 查询 birState != 1 且 chState = 7 的记录，重新尝试同步
     */
    @Override
    public BaseResponse retrySyncFailedBuildingInfo() {
        LambdaQueryWrapper<CompletionHandover> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompletionHandover::getChState, 7);
        wrapper.and(
                w -> w.eq(CompletionHandover::getBirState, 0)
                .or()
                .isNull(CompletionHandover::getBirState));

        List<CompletionHandover> failedList = baseMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(failedList)) {
            return BaseResponse.ok("暂无需要同步的数据");
        }

        int successCount = 0;
        int failCount = 0;

        for (CompletionHandover handover : failedList) {
            String chId = null;
            try {
                chId = handover.getChId();
                LambdaQueryWrapper<CompletionHandoverInfoSub> subWrapper = new LambdaQueryWrapper<>();
                subWrapper.eq(CompletionHandoverInfoSub::getBirId, chId);
                List<CompletionHandoverInfoSub> data = completionHandoverInfoSubMapper.selectList(subWrapper);

                boolean updateSuccess;

                // 没有子表数据 → 也算同步成功
                if (CollectionUtils.isEmpty(data)) {
                    updateSuccess = true;
                } else {
                    List<BuildingInfoSub> updates = new ArrayList<>();
                    for (CompletionHandoverInfoSub c : data) {
                        BuildingInfoSub b = new BuildingInfoSub();
                        b.setId(c.getBisId());
                        b.setBisWarrantyStart(c.getBisWarrantyStart());
                        b.setBisWarrantyEnd(c.getBisWarrantyEnd());
                        b.setBisWarranty(c.getBisWarranty());
                        b.setBisCompany(handover.getChFirmUnit());
                        b.setBisFirmContact(handover.getChFirmContact());
                        b.setBisFirmPhone(handover.getChFirmPhone());
                        b.setBisNum(c.getBisNum());
                        b.setBisUnit(c.getBisUnit());
                        updates.add(b);
                    }
                    updateSuccess = buildingInfoSubService.updateBatchById(updates);
                }

                if (updateSuccess) {
                    handover.setBirState(1);
                    baseMapper.updateById(handover);
                    successCount++;
                } else {
                    failCount++;
                }

            } catch (Exception e) {
                log.error("同步异常+chId:"+ chId+"异常为"+ e.getMessage());
                failCount++;
            }
        }
        return BaseResponse.ok("重试同步完成，成功：" + successCount + " 条，失败：" + failCount + " 条");
    }
    /**
     * 检查保险状态 过期标记已过期
     * 定时检查building_info_sub表 查看质保时间 过期吧质保状态标记已过期
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse checkInsuranceStatus() {
        LambdaQueryWrapper<BuildingInfoSub> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNotNull(BuildingInfoSub::getBisWarrantyStart);
        wrapper.isNotNull(BuildingInfoSub::getBisWarrantyEnd);

        List<BuildingInfoSub> list = buildingInfoSubService.list(wrapper);
        if (CollUtil.isEmpty(list)) {
            return BaseResponse.ok("暂无需要更新的质保数据");
        }

        int count = 0;
        Date now = new Date();
        // 2. 循环判断：未生效 / 在保 / 已过期
        for (BuildingInfoSub sub : list) {
            try {
                Date startDate = sub.getBisWarrantyStart();
                Date endDate = sub.getBisWarrantyEnd();
                String oldStatus = sub.getBisWarranty();

                String newStatus;
                if (now.before(startDate)) {
                    // 未到开始时间 → 未生效
                    newStatus = "未生效";
                } else if (now.after(endDate)) {
                    // 已过结束时间 → 已过期
                    newStatus = "已过期";
                } else {
                    // 在时间范围内 → 在保
                    newStatus = "在保";
                }
                // 只有状态变化了才更新（避免无意义更新）
                if (!newStatus.equals(oldStatus)) {
                    sub.setBisWarranty(newStatus);
                    buildingInfoSubService.updateById(sub);
                    count++;
                }
            } catch (Exception e) {
                log.error("更新质保状态异常 id:"+ sub.getId()+"异常信息"+ e.getMessage());
            }
        }

        return BaseResponse.ok("质保检查完成，更新状态数据：" + count + " 条");
    }
}