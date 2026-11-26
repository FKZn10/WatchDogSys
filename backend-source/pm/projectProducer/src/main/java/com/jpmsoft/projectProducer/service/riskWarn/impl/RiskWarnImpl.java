package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.RiskWarn.RiskWarnRequest;
import com.jpmsoft.common.model.response.RiskWarn.RiskWarnResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskWarnMapper;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RiskWarnImpl extends ServiceImpl<RiskWarnMapper, RiskWarn> implements IRiskWarnService {

    @Resource
    private ISysUserService sysUserService;

    /**
     * 在当前用户权限和筛选范围内按 rwiId 统计，按数量倒序取前 size 条。
     * size 未传或为零时默认取十条，忽略 page 和自定义排序参数。
     * 排名内指标名称缺失或重复时返回错误，避免 Map 键冲突导致统计结果丢失。
     *
     * @param request 筛选条件和排名条数
     * @param user 当前登录用户
     * @return 以 rwiName 为键、预警数量为值的有序 Map
     */
    @Override
    public BaseResponse getRiskWarnRanking(RiskWarnRequest request, LogUserInfoVo user) {
        if (request.getSize() < 0) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String authSql = sysUserService.getAuthSql("Risk_Warn", user.getId(), "Rw_Pm_Id", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<RiskWarnRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("dId").put("rwiName").put("rwiLevel").put("cId")
                .put("rwContent").put("rwiDesc").put("rwiProblem").put("rwState").put("rwiId").put("pId")
                .put(request.getRwTimes(), "rwTimeStart", "rwTimeEnd").put("rwFrom").put("tCode").put("oplId");
        params.put("uType", "2".equals(user.getUType()) ? user.getUType() : null);
        IPage<Map<String, Object>> page = new Page<>(1, request.getSize() == 0 ? 10 : request.getSize(), false);
        List<Map<String, Object>> rows = baseMapper.getRiskWarnRanking(page, params.getQuery());
        Map<String, Long> ranking = new LinkedHashMap<>();
        for (Map<String, Object> row : rows) {
            String rwiName = (String) row.get("rwiName");
            if (ValidateUtil.isEmpty(rwiName)) {
                return BaseResponse.fail("风险预警指标名称缺失，无法生成排名");
            }
            if (ranking.containsKey(rwiName)) {
                return BaseResponse.fail("风险预警指标名称重复，无法按名称返回排名：" + rwiName);
            }
            ranking.put(rwiName, ((Number) row.get("warnCount")).longValue());
        }
        return BaseResponse.ok(ranking, ranking.size());
    }

    @Override
    public BaseResponse getRiskWarnList(RiskWarnRequest request, LogUserInfoVo user) {
        String authSql = sysUserService.getAuthSql("Risk_Warn", user.getId(), "Rw_Pm_Id", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<RiskWarnRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("dId").put("rwiName").put("rwiLevel").put("cId")
                .put("rwContent").put("rwiDesc").put("rwiProblem").put("rwState").put("rwiId").put("pId")
                .put(request.getRwTimes(), "rwTimeStart", "rwTimeEnd")
                .put(request.getRwCreateTimes(), "rwCreateTimeStart", "rwCreateTimeEnd")
                .put("rwFrom").put("tCode");
        params.put("uType", null);
        if (ValidateUtil.isNotEmpty(user.getUType()) && "2".equals(user.getUType()))
            params.put("uType", user.getUType());
        IPage<RiskWarnResponse> page = new Page<>(request.getPage(), request.getSize());
        List<RiskWarnResponse> datas = baseMapper.getRiskWarnList(page, params.getQuery());
        return BaseResponse.ok(datas, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse getRiskWarnById(String rwId) {
        RiskWarnResponse data = baseMapper.getRiskWarnById(rwId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getRiskWarnListOutline(RiskWarnRequest request, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(request.getOplId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<RiskWarnRequest> params = new QueryMap<>(request);
        params.put("pName").put("dId").put("rwiName").put("rwiLevel").put("cId")
                .put("rwContent").put("rwiDesc").put("rwiProblem").put("rwState").put("rwiId")
                .put("pId").put(request.getRwTimes(), "rwTimeStart", "rwTimeEnd")
                .put(request.getRwCreateTimes(), "rwCreateTimeStart", "rwCreateTimeEnd").put("oplId")
                .put("rwFrom").put("tCode");
        params.put("uType", null);
        if (ValidateUtil.isNotEmpty(user.getUType()) && "2".equals(user.getUType())) {
            params.put("uType", user.getUType());
        }
        IPage<RiskWarnResponse> page = new Page<>(request.getPage(), request.getSize());
        List<RiskWarnResponse> datas = baseMapper.getRiskWarnListOutline(page, params.getQuery());
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }
}
