package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonth;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonthDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonthDtlQD;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.investment.TrackAuditMonthMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.TrackAuditMonthDtl;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.investment.ITrackAuditMonthService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: shanzhi
 * @date: 2024/06/28 17:10
 */
@Service
    public class TrackAuditMonthImpl extends ServiceImpl<TrackAuditMonthMapper, TrackAuditMonthDtl> implements ITrackAuditMonthService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ConContractMapper conContractMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ISysFileService sysFileService;

    @Override
    public BaseResponse getTrackAuditMonthByConId(String conId, String month) {
        Map<String, BigDecimal> map = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        BigDecimal data=new BigDecimal(0);
        List<TrackAuditMonthDtl> datas = baseMapper.getTrackAuditMonthByConId(conId);
        if(ValidateUtil.isNotEmpty(datas)) {
            LinkedHashSet<String> monthlist = datas.stream().map(e -> formatter.format(e.getMonthDate())).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            if(ValidateUtil.isNotNull(month))
            {
                String Month=month+"-01";
                LocalDate localDate = LocalDate.parse(Month);
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                monthlist=datas.stream().filter(k->k.getMonthDate().before(date)).map(e -> formatter.format(e.getMonthDate())).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            }
            for (String item :monthlist) {
                TrackAuditMonthDtl child = datas.stream().filter(k->item.equals(formatter.format(k.getMonthDate())))
                        .sorted(Comparator.comparing(TrackAuditMonthDtl::getStateTime).reversed())
                        .findFirst().orElse(null);
                if(ValidateUtil.isNotNull(child))
                {
                    data=data.add(child.getCompleteValue());
                }
            }
      }
        map.put("totalMoney",data);
        return BaseResponse.ok(map);
    }

    public BaseResponse getTrackAuditMonthListByPId(String pId) {
        //根据项目ID获取已结束流程数据
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        List<TrackAuditMonthDtlModel> newlist=new ArrayList<>();
        List<TrackAuditMonth> datas = baseMapper.getTrackAuditMonthListByPId(pId);
        List<Map<String,Object>> contract= this.conContractMapper.getConContractByPid(pId);

        List<TrackAuditMonth> newdatas=datas;
        if(ValidateUtil.isNotEmpty(datas)) {
            LinkedHashSet<String> monthlist = datas.stream().map(e -> formatter.format(e.getMonthDate())).sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            List<TrackAuditMonthDtlModel> list = datas.stream().map(n -> {
                TrackAuditMonthDtlModel model = new TrackAuditMonthDtlModel();
                model.setPId(pId);
                model.setTrackCId(n.getTrackCId());
                model.setTrackCName(n.getTrackCName());
                model.setConId(n.getConId());
                model.setConName(n.getConName());
                model.setSgName(n.getSgName());
                model.setPlanStartTime(n.getPlanStartTime());
                model.setPlanEndTime(n.getPlanEndTime());
                model.setConTotalAmount(n.getConTotalAmount());
                List<TrackAuditMonthDtlQD> trackAuditMonthDtlQD = new ArrayList<>();
                monthlist.forEach(m -> {
                    TrackAuditMonthDtlQD qd = new TrackAuditMonthDtlQD();
                    // 将Calendar对象格式化为指定格式的字符串
                   TrackAuditMonth child = newdatas.stream().filter(k -> k.getConId().equals(n.getConId()) && m.equals(formatter.format(k.getMonthDate()))).sorted(Comparator.comparing(TrackAuditMonth::getStateTime).reversed()).findFirst().orElse(null);
                    qd.setMonthDate(m);
                    if (ValidateUtil.isNotNull(child)) {
                        qd.setCompleteValue(child.getCompleteValue());
                        //实时查询累计完成产值
                        BigDecimal TotleValue = newdatas.stream().filter(k -> n.getConId().equals(k.getConId())
                                        && k.getMonthDate().toInstant().isBefore(child.getMonthDate().toInstant())
                                        &&k.getDataStatus()==1).collect(Collectors.toList()).stream().
                                map(TrackAuditMonth::getCompleteValue).
                                reduce(BigDecimal.ZERO, NumberUtil::add).add(child.getCompleteValue());
                        qd.setTotleValue(TotleValue);
                        BigDecimal TotleRate=new BigDecimal(0);
                        //查询合同金额
                        Map<String,Object> con=contract.stream().filter(k->n.getConId().equals(k.get("conId"))).findFirst().orElse(null);
                        if(ValidateUtil.isNotNull(con))
                        {
                            BigDecimal htje= con.get("conMoneyTotal")==null?BigDecimal.ZERO:(BigDecimal)con.get("conMoneyTotal");
                            if(htje!=BigDecimal.ZERO)
                            {
                                TotleRate=NumberUtil.div(TotleValue,htje, 4).multiply(new BigDecimal(100));
                            }
                        }
                        qd.setTotleRate(TotleRate);
                    }
                    trackAuditMonthDtlQD.add(qd);
                });
                model.setTrackAuditMonthDtlQD(trackAuditMonthDtlQD);
                return model;
            }).collect(Collectors.toList());
            for (TrackAuditMonthDtlModel item : list) {
                TrackAuditMonthDtlModel newobj = newlist.stream().filter(n -> item.getConId().contains(n.getConId())).findFirst().orElse(null);
                if (ValidateUtil.isNull(newobj)) {
                    newlist.add(item);
                }
            }
        }
       return BaseResponse.ok(newlist);
    }

    public BaseResponse approvalTrackAuditMonth(@RequestParam String tamId) {
        SysFile sysFile = sysFileService.getById(tamId);
        String fileflowstatus = sysFile.getFileflowstatus();
        if("7".equals(fileflowstatus)){
            //获取当前
            LambdaQueryWrapper<TrackAuditMonthDtl> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TrackAuditMonthDtl::getTamId,tamId);
            TrackAuditMonthDtl obj=baseMapper.selectList(wrapper).stream().findFirst().get();
            obj.setDataStatus(1);
            baseMapper.update(obj,wrapper);

            //查询该合同下其他数据的信息
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            List<TrackAuditMonthDtl> list=baseMapper.getTrackAuditMonthByConId(obj.getConId())
                    .stream().filter(k->formatter.format(obj.getMonthDate()).equals(formatter.format(k.getMonthDate()))
                            &&!tamId.equals(k.getTamId())).collect(Collectors.toList());
            List<TrackAuditMonthDtl> entitys = new ArrayList<>();
            for (TrackAuditMonthDtl item : list)
            {
                TrackAuditMonthDtl entity=new TrackAuditMonthDtl();
                BeanUtils.copyProperties(item, entity);
                item.setDataStatus(0);
                LambdaQueryWrapper<TrackAuditMonthDtl> wrapper2 = new LambdaQueryWrapper<>();
                wrapper2.eq(TrackAuditMonthDtl::getTamId,item.getTamId());
                baseMapper.update(item,wrapper2);
            }
        }
        return BaseResponse.ok(null);
    }

}
