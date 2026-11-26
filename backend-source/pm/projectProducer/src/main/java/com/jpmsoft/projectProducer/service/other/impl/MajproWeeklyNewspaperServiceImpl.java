package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.NoMajproWeeklyNewspaperRequest;
import com.jpmsoft.common.model.response.NoMajproWeeklyNewspaperResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.other.MajproWeeklyNewspaperMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.MajproWeeklyNewspaper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.other.IMajproWeeklyNewspaperService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MajproWeeklyNewspaperServiceImpl extends ServiceImpl<MajproWeeklyNewspaperMapper, MajproWeeklyNewspaper> implements IMajproWeeklyNewspaperService {
    @Resource
    private IPreProjectService preProjectService;

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取没在指定时间内填写周报的重点项目
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getNoMajproWeeklyNewspaperList(NoMajproWeeklyNewspaperRequest request,String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String authSql = sysUserService.getAuthSql(null, redisUtils.getUid(token), "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "State");

        List<PreProject> projects = preProjectService.list(Wrappers.<PreProject>lambdaQuery()
                .eq(PreProject::getpMajorType, "1")
                .and(wrapper -> wrapper
                        .ne(PreProject::getpImageProgress, "竣工验收")
                        .ne(PreProject::getpImageProgress, "移交")
                        .or().isNull(PreProject::getpImageProgress)
                )
                .eq(PreProject::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(PreProject::getDeleted, false)
                .select(PreProject::getpId)
        );
        if (ValidateUtil.isEmpty(projects)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        Set<String> pIds = projects.stream().map(PreProject::getpId).collect(Collectors.toSet());
        Page<NoMajproWeeklyNewspaperResponse> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<NoMajproWeeklyNewspaperRequest> params = new QueryMap<>(request);
        params.put("proName").put("pUseUnitName");
        Date startTime = request.getStartTime();
        Date endTime = request.getEndTime();
        // 处理成start是当天的开始，end是当天的结束
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startTime);
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endTime);
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);

        params.put("startTime", startCal.getTime());
        params.put("endTime", endCal.getTime());

        List<String> pIds2 = baseMapper.selectProids(params.getQuery());

        // pIds减去pIds2
        pIds2.forEach(pIds::remove);

        params.put("pIds", pIds);
        List<NoMajproWeeklyNewspaperResponse> list = baseMapper.getNoMajproWeeklyNewspaperList(page, params.getQuery(),authSql);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }
}
