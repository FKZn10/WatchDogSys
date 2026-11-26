package com.jpmsoft.projectProducer.service.zdyxy.impl;

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
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.maintenance.MaintenanceRecordMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.AnnualPlanMapper;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.AnnualPlan;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.zdyxy.AnnualPlanService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class AnnualPlanServiceImpl
        extends ServiceImpl<AnnualPlanMapper, AnnualPlan>
        implements AnnualPlanService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private MaintenanceRecordMapper maintenanceRecordMapper;

    @Override
    public BaseResponse saveOrUpdateData(AnnualPlanModel request) {
        AnnualPlan entity;
        String primaryId = request.getId();
        log.info("primaryId:{}", primaryId);
        if (StrUtil.isEmpty(primaryId)) {
            // QueryWrapper<AnnualPlan> queryWrapper = new QueryWrapper<>();
            // Long old = baseMapper.selectCount(queryWrapper.eq("year", request.getYear()).ne("state", ApprovalStatusEnum.Invalid.getIndex()));
            // if (old != null && old > 0) {
            //     return BaseResponse.fail(request.getYear() + "该年度计划已存在");
            // }
            primaryId = IdWorker.getGenerateID();
            entity = new AnnualPlan();
            BeanUtils.copyProperties(request, entity);
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Already_Over.getIndex();
            entity.setStateTime(DateTime.now());
            entity.setState(flowStatus);
            if (ValidateUtil.isNotNull(entity.getStartTime())) {
                entity.setYear(DateUtil.getTimeYear(entity.getStartTime()));
            }
            //saveFile(entity, documentDtos);
            //创建流程
            baseMapper.insert(entity);
            if (!sysFileService.FileHelper(entity, request, "create", request.getFileRelationId())) {
                return BaseResponse.fail("新增失败");
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(request, entity);
            if (ValidateUtil.isNotNull(entity.getStartTime())) {
                entity.setYear(DateUtil.getTimeYear(entity.getStartTime()));
            }
            //saveFile(entity, documentDtos);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse approvalData(AnnualPlanModel request) {
        String id = request.getId();
        AnnualPlan entity;
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
    public BaseResponse getList(AnnualPlanModel request, String token) {
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<AnnualPlanModel> queryMap = new QueryMap<>(request);
        queryMap.put("year").put("status").put("conName").put("startTime").put("endTime").put("effectiveDate");

        IPage<AnnualPlanModel> page = new Page<>(request.getPage(), request.getSize());
        List<AnnualPlanModel> list = baseMapper.getList(queryMap.getQuery(), page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String id) {
        AnnualPlanModel model = baseMapper.getDetail(id);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }

    @Override
    public BaseResponse getByYear(AnnualPlanModel request) {
        // 1. 查询年度计划预算总额
        QueryWrapper<AnnualPlan> planWrapper = new QueryWrapper<>();
        planWrapper.eq("state", ApprovalStatusEnum.Already_Over.getIndex());

        // 如果传了时间，则过滤时间；不传则查全部
        if (ValidateUtil.isNotNull(request.getStartTime())) {
            // 传入的时间在startTime和endTime之间的记录
            planWrapper.le("start_time", request.getStartTime());
            planWrapper.ge("end_time", request.getStartTime());
            planWrapper.orderByDesc("create_time");
            planWrapper.last(" LIMIT 1 ");
        }

        List<AnnualPlan> planList = baseMapper.selectList(planWrapper);
        if (planList.isEmpty()) {
            return new BaseResponse("相关计划信息不存在！");
        }

        // 计算总预算 (万元转元)
        BigDecimal preAmount = planList.stream()
                .map(plan -> plan.getPreAmount().multiply(BigDecimal.valueOf(10000)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. 查询维修记录支出总额
        QueryWrapper<MaintenanceRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.eq("mr_state", ApprovalStatusEnum.Already_Over.getIndex());
        // 如果传了年份，才需要计算时间区间过滤条件
        if (request.getYear() != null) {
            recordWrapper.ge("mr_create_time", planList.get(0).getStartTime());
            recordWrapper.le("mr_create_time", planList.get(0).getStartTime());
        }
        List<MaintenanceRecord> maintenanceRecords = maintenanceRecordMapper.selectList(recordWrapper);

        // 计算已支出总额
        BigDecimal hasPayAmount = maintenanceRecords.stream()
                .map(MaintenanceRecord::getMrReportAmountPre)
                .filter(Objects::nonNull) // 防止空指针异常
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. 返回结果
        Map<String, Object> map = new HashMap<>();
        map.put("preAmount", preAmount);
        map.put("hasPayAmount", hasPayAmount);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getInfo(AnnualPlanModel request) {
        AnnualPlanModel model = baseMapper.getInfo(request.getStartTime(), request.getStartTime());
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }

    @Override
    public String getAnnualPlanByTime(Date time) {
        QueryWrapper<AnnualPlan> wrapper = new QueryWrapper<>();
        wrapper.le("start_time", time);
        wrapper.ge("end_time", time);
        List<AnnualPlan> annualPlan = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(annualPlan)) return annualPlan.stream().findFirst().get().getId();
        return null;
    }
}