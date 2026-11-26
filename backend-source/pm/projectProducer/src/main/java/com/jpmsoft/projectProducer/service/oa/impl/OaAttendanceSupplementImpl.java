package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceSupplementModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceGroupMapper;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceMemberMapper;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceSupplementMapper;
import com.jpmsoft.projectProducer.mapper.oa.OaAttendanceWeekdayMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.*;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceService;
import com.jpmsoft.projectProducer.service.oa.IOaAttendanceSupplementService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OaAttendanceSupplementImpl extends ServiceImpl<OaAttendanceSupplementMapper, OaAttendanceSupplement> implements IOaAttendanceSupplementService {

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IOaAttendanceService iOaAttendanceService;

    @Resource
    private OaAttendanceGroupMapper oaAttendanceGroupMapper;

    @Resource
    private OaAttendanceMemberMapper oaAttendanceMemberMapper;

    @Resource
    private OaAttendanceWeekdayMapper oaAttendanceWeekdayMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateAttendanceSupplement(OaAttendanceSupplementModel request) {

        if (StringUtils.isEmpty(request.getAsUid())) {
            return BaseResponse.fail("没有补卡人");
        }

        // 根据考勤人员id查询启用考勤组中的人员信息，排除已禁用考勤组的重复人员
        OaAttendanceMember oaAttendanceMember = oaAttendanceMemberMapper.getEnabledAttendanceMemberByUid(request.getAsUid());

        if (Objects.isNull(oaAttendanceMember)) {
            return BaseResponse.fail("不在考勤人员表");
        }

        //根据考勤组id查询考勤组详情
        OaAttendanceGroup oaAttendanceGroup = oaAttendanceGroupMapper.selectById(oaAttendanceMember.getAgId());

        if (Objects.isNull(oaAttendanceGroup)) {
            return BaseResponse.fail("没有考勤组");
        }

        //查询对应考勤组配置得工作日打卡规则
        LambdaQueryWrapper<OaAttendanceWeekday> weekdayWrapper = new LambdaQueryWrapper<>();
        weekdayWrapper.eq(OaAttendanceWeekday::getAgId, oaAttendanceGroup.getAgId());
        List<OaAttendanceWeekday> list = oaAttendanceWeekdayMapper.selectList(weekdayWrapper);
        if (list == null || list.size() == 0) {
            return BaseResponse.fail("考勤组配置工作日规则");
        }

        OaAttendanceSupplement entity = new OaAttendanceSupplement();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAsId())) {
            entity.setAsId(GenerateUUID.getUUID());
            entity.setAsCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getAsId(), request.gettId(), entity.getAsState() + "", entity.getAsUid(), entity.getAsUname()
                    , entity.getAsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getAsId());

        } else {
            OaAttendanceSupplement model = baseMapper.selectById(entity.getAsId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setAsCreateTime(model.getAsCreateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getAsId(), request.gettId(), entity.getAsState() + "", entity.getAsUid(), entity.getAsUname()
                    , entity.getAsCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getAsId());

        }
        return BaseResponse.ok(entity.getAsId());
    }

    @Override
    public BaseResponse getAttendanceSupplementList(OaAttendanceSupplementModel request) {
        QueryMap<OaAttendanceSupplementModel> params = new QueryMap<>(request);
        params.put("asUname").put("asAbnormalState").put("asState");
        List<OaAttendanceSupplementModel> datas = baseMapper.getAttendanceSupplementList(params.getQuery());
        Integer total = baseMapper.getAttendanceSupplementListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getAttendanceSupplementById(String asId) {
        OaAttendanceSupplementModel data = baseMapper.getAttendanceSupplementById(asId);
        return BaseResponse.ok(data);
    }

    /**
     * 补卡后，修改考勤记录为正常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateAttendance(OaAttendanceSupplementModel request) throws ParseException {
        if (StringUtils.isEmpty(request.getAsId())) {
            return BaseResponse.fail("缺少参数");
        }
        OaAttendanceSupplement oaAttendanceSupplement = this.baseMapper.selectById(request.getAsId());

        if (Objects.isNull(oaAttendanceSupplement)) {
            return BaseResponse.fail("流程不存在");
        }

        if (StringUtils.isEmpty(oaAttendanceSupplement.getAsUid())) {
            return BaseResponse.fail("没有补卡人");
        }
        //审批流程结束，进行补卡操作
        if ("7".equals(oaAttendanceSupplement.getAsState().toString())) {

            //处理时间，判断当天是周几
            Date time = oaAttendanceSupplement.getAsTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            // 根据考勤人员id查询启用考勤组中的人员信息，排除已禁用考勤组的重复人员
            OaAttendanceMember oaAttendanceMember = oaAttendanceMemberMapper.getEnabledAttendanceMemberByUid(oaAttendanceSupplement.getAsUid());

            if (Objects.isNull(oaAttendanceMember)) {
                return BaseResponse.fail("不在考勤人员表");
            }

            //根据考勤组id查询考勤组详情
            OaAttendanceGroup oaAttendanceGroup = oaAttendanceGroupMapper.selectById(oaAttendanceMember.getAgId());

            if (Objects.isNull(oaAttendanceGroup)) {
                return BaseResponse.fail("没有考勤组");
            }

            //查询对应考勤组配置得工作日打卡规则
            LambdaQueryWrapper<OaAttendanceWeekday> weekdayWrapper = new LambdaQueryWrapper<>();
            weekdayWrapper.eq(OaAttendanceWeekday::getAgId, oaAttendanceGroup.getAgId());
            weekdayWrapper.eq(OaAttendanceWeekday::getAwWeek, "0" + weekday);
            OaAttendanceWeekday oaAttendanceWeekday = oaAttendanceWeekdayMapper.selectOne(weekdayWrapper);

            if (Objects.isNull(oaAttendanceWeekday)) {
                return BaseResponse.fail("考勤组配置工作日规则");
            }

            //考勤组配置得上午上班时间
            String awStartAm = oaAttendanceWeekday.getAwStartAm();
            //考勤组配置得下午下班时间
            String awEndPm = oaAttendanceWeekday.getAwEndPm();

            //获取当天日期yyyy-MM-dd
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 普通的时间转换
            String date = new SimpleDateFormat("yyyy-MM-dd").format(time);
            Date amDate = dateFormat.parse(date + " " + awStartAm + ":00");
            Date pmDate = dateFormat.parse(date + " " + awEndPm + ":00");
            calendar.setTime(amDate);
            long timeInMillis1 = calendar.getTimeInMillis();
            calendar.setTime(pmDate);
            long timeInMillis2 = calendar.getTimeInMillis();
            //计算上班时长
            float hour = ((timeInMillis2 - timeInMillis1) / (1000 * 3600));

            //补上班打卡记录
            OaAttendance oaAttendanceAm = new OaAttendance();
            oaAttendanceAm.setAId(GenerateUUID.getUUID());
            oaAttendanceAm.setATime(amDate);
            oaAttendanceAm.setAgId(oaAttendanceGroup.getAgId());
            oaAttendanceAm.setAUid(oaAttendanceMember.getAmUid());
            oaAttendanceAm.setAUname(oaAttendanceMember.getAmUname());
            oaAttendanceAm.setAType("01");
            oaAttendanceAm.setAWeek("0" + weekday);
            oaAttendanceAm.setAHours(hour);
            oaAttendanceAm.setAResult("01");
            oaAttendanceAm.setARemark("补卡");
            iOaAttendanceService.getBaseMapper().insert(oaAttendanceAm);

            //补下班打卡记录
            OaAttendance oaAttendancePm = new OaAttendance();
            oaAttendancePm.setAId(GenerateUUID.getUUID());
            oaAttendancePm.setATime(pmDate);
            oaAttendancePm.setAgId(oaAttendanceGroup.getAgId());
            oaAttendancePm.setAUid(oaAttendanceMember.getAmUid());
            oaAttendancePm.setAUname(oaAttendanceMember.getAmUname());
            oaAttendancePm.setAType("04");
            oaAttendancePm.setAWeek("0" + weekday);
            oaAttendancePm.setAHours(hour);
            oaAttendancePm.setAResult("01");
            oaAttendancePm.setARemark("补卡");
            iOaAttendanceService.getBaseMapper().insert(oaAttendancePm);

        } else {
            return BaseResponse.fail("流程未结束");
        }

        return BaseResponse.ok();
    }
}
