package com.jpmsoft.projectProducer.service.supervision.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Supervisor.ConSupPlanRequest;
import com.jpmsoft.common.model.request.Supervisor.PunishRequest;
import com.jpmsoft.common.model.response.Supervisor.ConSupPlanResponse;
import com.jpmsoft.common.model.response.Supervisor.PunishResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.supervision.ConSupPlanMapper;
import com.jpmsoft.projectProducer.mapper.supervision.PunishMapper;
import com.jpmsoft.projectProducer.pojo.entity.supervision.ConSupPlan;
import com.jpmsoft.projectProducer.pojo.entity.supervision.Punish;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.supervision.IConSupPlanService;
import com.jpmsoft.projectProducer.service.supervision.IPunishService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PunishImpl extends ServiceImpl<PunishMapper, Punish> implements IPunishService {
    @Resource
    private ISysUserService sysUserService;
    @Override
    public BaseResponse getPunishList(PunishRequest request, String uid) {
        String authSql = sysUserService.getAuthSql("punishes", uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<PunishRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("cName").put("punishType");
        IPage<PunishResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PunishResponse> datas = baseMapper.getPunishList(page, params.getQuery());

        return BaseResponse.ok(datas, (int) page.getTotal());
    }
}
