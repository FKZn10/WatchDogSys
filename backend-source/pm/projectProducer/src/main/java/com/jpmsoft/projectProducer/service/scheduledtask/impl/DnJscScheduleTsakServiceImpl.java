package com.jpmsoft.projectProducer.service.scheduledtask.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.feign.MultiProjectFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpDtlMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConEcMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsRecordMapper;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.*;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.service.scheduledtask.IDnJscScheduleTsakService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DnJscScheduleTsakServiceImpl implements IDnJscScheduleTsakService {

    private RequestBean<Object> requestBean = new RequestBean<>();

    @Resource
    private PreProjectMapper preProjectMapper;

    @Resource
    private InvPlanYearMapper invPlanYearMapper;

    @Resource
    private QsRecordMapper qsRecordMapper;

    @Resource
    private ConContractMapper conContractMapper;

    @Resource
    private ConEcMapper conEcMapper;

    @Resource
    private SecInfoMapper secInfoMapper;

    @Resource
    private ProjectCpDtlMapper projectCpDtlMapper;

    @Resource
    private MultiProjectFeign multiProjectFeign;

    @Resource
    private UserProducerFeign userProducerFeign;

    public void startTask() {

//        //统计最新在建项目总数
//        this.projectCount();
//
//        //项目投资进度统计数据
//        this.mpcProjectInvestmentProgress();
//
//        //质量安全情况统计数据
//        this.mpcQualityAndSafety();
//
//        //资金情况一览统计数据
//        this.mpcFunds();
//
//        //变更签证情况
//        this.mpcChangeVisaSituation();
//
//        //招投标情况一览
//        this.mpcBiddingSituation();
//
//        //项目进度
//        this.mpcProjectSchedule();

    }

    /**
     * 定时统计在建项目，入库mpc_projectNumber
     */
    @Transactional(rollbackFor = Exception.class)
    public void projectCount() {

        //统计最新在建项目总数
        LambdaQueryWrapper<PreProject> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PreProject::getpType, "1");
        Long count = preProjectMapper.selectCount(wrapper);

        //删除原来统计总数
        this.multiProjectFeign.deleteByType();

        MpcProjectnumber mpcProjectnumber = new MpcProjectnumber();
        mpcProjectnumber.setBuildingNum(count.toString());
        mpcProjectnumber.setType("1");
        requestBean.setInfo(mpcProjectnumber);
        //mpc_projectNumber添加最新在建项目总数
        this.multiProjectFeign.createMpcProjectnumber(requestBean);
    }

    /**
     * 项目投资进度统计数据,入库mpc_project_investment_progress
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcProjectInvestmentProgress() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteProjectInvestmentProgressByType();

        //抓取规则，需按年份抓取
        // 总支付金额：抓取合同支付得本期申请金额累加，时间:创建时间
        // 当年投资计划：抓取年度投资计划中得概算总投资累加，时间:申请年份
        List<MpcProjectInvestmentProgress> mpcProjectInvestmentProgresses = invPlanYearMapper.mpcProjectInvestmentProgressCount();
        if (mpcProjectInvestmentProgresses != null && mpcProjectInvestmentProgresses.size() > 0) {
            mpcProjectInvestmentProgresses.forEach(mpcProjectInvestmentProgress -> {
                requestBean.setInfo(mpcProjectInvestmentProgress);
                this.multiProjectFeign.createMpcProjectInvestmentProgress(requestBean);
            });
        }
    }

    /**
     * 质量安全情况数据统计，入库mpc_quality_and_safety
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcQualityAndSafety() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteQualityAndSafetyByType();

        //统计质量和安全已整改数和总数
        MpcQualityAndSafety mpcQualityAndSafety = qsRecordMapper.countMpcQualityAndSafety();
        requestBean.setInfo(mpcQualityAndSafety);
        this.multiProjectFeign.createMpcQualityAndSafety(requestBean);
    }

    /**
     * 资金情况一览,入库mpc_funds
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcFunds() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteMpcFundsByType();

        //资金情况一览统计逻辑
        List<MpcFunds> mpcFunds = conContractMapper.countContract();
        if (mpcFunds != null && mpcFunds.size() > 0) {
            mpcFunds.forEach(mpcFund -> {
                requestBean.setInfo(mpcFund);
                this.multiProjectFeign.createMpcFunds(requestBean);
            });
        }
    }

    /**
     * 变更签证情况,入库mpc_change_visa_situation
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcChangeVisaSituation() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteChangeVisaSituationByType();

        //变更签证情况统计逻辑
        List<MpcChangeVisaSituation> mpcFunds = conEcMapper.countMpcChangeVisaSituation();
        if (mpcFunds != null && mpcFunds.size() > 0) {
            mpcFunds.forEach(mpcChangeVisaSituation -> {
                requestBean.setInfo(mpcChangeVisaSituation);
                this.multiProjectFeign.createMpcChangeVisaSituation(requestBean);
            });
        }
    }

    /**
     * 招投标情况一览,mpc_bidding_situation
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcBiddingSituation() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteMpcBiddingSituationBySourceType();

        //招投标情况一览统计逻辑
        List<MpcBiddingSituation> list = secInfoMapper.countMpcBiddingSituation();
        if (list != null && list.size() > 0) {
            list.forEach(mpcBiddingSituation -> {
                requestBean.setInfo(mpcBiddingSituation);
                this.multiProjectFeign.createMpcBiddingSituation(requestBean);
            });
        }
    }

    /**
     * 项目进度,mpc_project_schedule
     */
    @Transactional(rollbackFor = Exception.class)
    public void mpcProjectSchedule() {
        //先删除原来统计记录
        this.multiProjectFeign.deleteMpcProjectScheduleByType();

        //milestoneNode 根据type查询项目里程碑节点配置
        BaseResponse baseResponse = this.userProducerFeign.GetConfigValueByCode("milestoneNode");

        //获取配置得百分比
        BaseResponse response = this.userProducerFeign.GetConfigValueByCode("milestoneNodeAndValue");

        Object data = baseResponse.getData();
        if (Objects.nonNull(data) && Objects.nonNull(response.getData())) {
            JSONObject jsonObject = JSON.parseObject(response.getData().toString());
            List<String> codeValue = Arrays.asList(data.toString().split(","));

            //项目进度统计逻辑
            List<ProjectCpDtlVo> projectCpDtlVos = this.projectCpDtlMapper.qryProjectCpDtlByCpIds(codeValue);
            if (projectCpDtlVos != null && projectCpDtlVos.size() > 0) {
                //按项目id分组成多个集合
                Map<String, List<ProjectCpDtlVo>> groupBy = projectCpDtlVos.stream().collect(Collectors.groupingBy(ProjectCpDtlVo::getPId));

                Collection<List<ProjectCpDtlVo>> values = groupBy.values();

                //每个value都是同一个项目得不同进度
                values.forEach(value -> {
                    //这个是进度，单位6.25
                    Double num = 0.00;
                    int index = -1;
                    for (int n = 0; n < codeValue.size(); n++) {
                        String code = codeValue.get(n);
                        for (int i = 0; i < value.size(); i++) {
                            ProjectCpDtlVo projectCpDtlVo = value.get(i);
                            if (code.equals(projectCpDtlVo.getCpdCode())) {
                                double parseDouble = Double.parseDouble(jsonObject.get(code).toString());
                                if (parseDouble > num) {
                                    num = parseDouble;
                                    index = i;
                                }
                            }
                        }
                    }
                    //如果大于1，就存一条项目进度最快得
                    if (index > -1) {
                        ProjectCpDtlVo projectCpDtlVo = value.get(index);
                        MpcProjectSchedule vo = new MpcProjectSchedule();
                        vo.setProjectName(projectCpDtlVo.getPName());
                        vo.setProgressProportion(num.toString());
                        vo.setType("1");
                        requestBean.setInfo(vo);
                        this.multiProjectFeign.createMpcProjectSchedule(requestBean);
                    }
                });
            }
        }

    }
}
