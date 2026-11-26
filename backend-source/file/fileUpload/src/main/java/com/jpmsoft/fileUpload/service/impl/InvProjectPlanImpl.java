package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.InvProjectPlanMapper;
import com.jpmsoft.fileUpload.pojo.dto.GmInvProPlanDto;
import com.jpmsoft.fileUpload.pojo.dto.ProProjectCodeDto;
import com.jpmsoft.fileUpload.pojo.dto.ProYearInvPlanDto;
import com.jpmsoft.fileUpload.pojo.entity.InvProjectPlan;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.pojo.vo.GmInvProPlanVo;
import com.jpmsoft.fileUpload.service.IInvProjectPlanService;
import com.jpmsoft.fileUpload.service.IPreProjectService;
import com.jpmsoft.fileUpload.service.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvProjectPlanImpl extends ServiceImpl<InvProjectPlanMapper, InvProjectPlan> implements IInvProjectPlanService {


    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IPreProjectService preProjectService;

    @Override
    public BaseResponse importInvProjectPlanByExcel(List<InvProjectPlan> datas, String uId) {
        SysUser user = sysUserService.getById(uId);
        Set<String> codes = new HashSet<>();
        for (InvProjectPlan data : datas) {
            codes.add(data.getPId());
        }
        List<String> pCodes = new ArrayList<>(codes);
        if (pCodes.size() > 0) {
            List<PreProject> pros = preProjectService.getPreProjectByCodes(pCodes);
            if (pros != null && pros.size() > 0) {
                //项目 年度  资金总额
                List<ProYearInvPlanDto> proYearInvs = getProYearInvListByCodes(pCodes);
                //项目  资金总额
                List<ProYearInvPlanDto> proInvs = getProInvListByCodes(pCodes);
                List<InvProjectPlan> entitys = new ArrayList<>();
                for (InvProjectPlan data : datas) {
                    List<PreProject> pro = pros.stream().filter(n -> n.getpCode().equals(data.getPId())).collect(Collectors.toList());
                    if (pro != null && pro.size() > 0) {
                        PreProject p = pro.stream().findFirst().get();
                        data.setPId(p.getpId());
                        data.setIppAnnualAccumFund(data.getIppReleaseFunds());
                        data.setIppAnnualReleaseFunds(data.getIppReleaseFunds());
                        //年度累计
                        List<ProYearInvPlanDto> proYearInv = proYearInvs.stream().filter(n -> n.getIppYear().equals(data.getIppYear()) && n.getPCode().equals(p.getpCode())).collect(Collectors.toList());
                        if (proYearInv != null && proYearInv.size() > 0) {
                            ProYearInvPlanDto pyi = proYearInv.stream().findFirst().get();
                            BigDecimal funds = data.getIppReleaseFunds().add(pyi.getFunds());
                            data.setIppAnnualReleaseFunds(funds);
                            proYearInvs.remove(proYearInvs.indexOf(pyi));
                            pyi.setFunds(funds);
                            proYearInvs.add(pyi);
                        } else {
                            ProYearInvPlanDto pyi = new ProYearInvPlanDto();
                            pyi.setFunds(data.getIppReleaseFunds());
                            pyi.setIppYear(data.getIppYear());
                            pyi.setPCode(p.getpCode());
                            proYearInvs.add(pyi);
                        }
                        //总累计
                        List<ProYearInvPlanDto> proInv = proInvs.stream().filter(n -> n.getPCode().equals(p.getpCode())).collect(Collectors.toList());
                        if (proInv != null && proInv.size() > 0) {
                            ProYearInvPlanDto pi = proInv.stream().findFirst().get();
                            BigDecimal funds = data.getIppReleaseFunds().add(pi.getFunds());
                            data.setIppAnnualAccumFund(funds);
                            proInvs.remove(proInvs.indexOf(pi));
                            pi.setFunds(funds);
                            proInvs.add(pi);
                        } else {
                            ProYearInvPlanDto pi = new ProYearInvPlanDto();
                            pi.setPCode(p.getpCode());
                            pi.setFunds(data.getIppReleaseFunds());
                            proInvs.add(pi);
                        }
                        data.setIppCreateUid(user.getuId());
                        data.setIppCreateUname(user.getuName());
                        data.setIppFile(GenerateUUID.getUUID());
                        data.setIppCreateTime(new Date());
                        data.setIppPType(p.getpType());
                        entitys.add(data);
                    }
                }
                if (entitys.size() > 0)
                    saveBatch(entitys);
            }
        }
        return new BaseResponse(true, "导入完成！");
    }

    @Override
    public BaseResponse importGmInvProPlan(List<GmInvProPlanDto> datas) {
        if (ValidateUtil.isEmpty(datas)) return BaseResponse.ok();
        List<String> ippNdrcCodes = datas.stream().map(GmInvProPlanDto::getIppNdrcCode).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
        List<String> pCodes = datas.stream().map(GmInvProPlanDto::getPCode).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
        List<ProProjectCodeDto> codes = preProjectService.getPidByProjectCodes(ippNdrcCodes, pCodes);
        List<GmInvProPlanVo> models = new ArrayList<>();
        for (GmInvProPlanDto data : datas) {
            List<ProProjectCodeDto> code = codes.stream().filter(n ->ValidateUtil.isNotEmpty(n.getPProjectCode())&&n.getPProjectCode().equals(data.getIppNdrcCode())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(code))
                code = codes.stream().filter(n ->ValidateUtil.isNotEmpty(n.getPCode())&& n.getPCode().equals(data.getPCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(code)) {
                GmInvProPlanVo model = new GmInvProPlanVo();
                BeanUtils.copyProperties(data, model);
                model.setPId(code.stream().findFirst().get().getPId());
                models.add(model);
            }
        }
        return BaseResponse.ok(models);
    }

    private List<ProYearInvPlanDto> getProYearInvListByCodes(List<String> pCodes) {
        return baseMapper.getProYearInvListByCodes(pCodes);
    }

    private List<ProYearInvPlanDto> getProInvListByCodes(List<String> pCodes) {
        return baseMapper.getProInvListByCodes(pCodes);
    }
}
