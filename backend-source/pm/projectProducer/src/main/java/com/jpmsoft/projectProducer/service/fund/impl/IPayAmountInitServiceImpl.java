package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.fund.PayAmountInitChangeMapper;
import com.jpmsoft.projectProducer.mapper.fund.PayAmountInitMapper;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.PayAmountInitChangeDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.PayAmountInitDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.PayAmountInit;
import com.jpmsoft.projectProducer.pojo.entity.fund.PayAmountInitChange;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanRequest;
import com.jpmsoft.projectProducer.pojo.request.fund.PayAmountInitRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.GroupFundPlanVo;
import com.jpmsoft.projectProducer.pojo.vo.fund.PayAmountInitVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.service.fund.IPayAmountInitService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IPayAmountInitServiceImpl extends BaseFileImpl<PayAmountInitMapper, PayAmountInit, PayAmountInitDto> implements IPayAmountInitService {
    @Autowired
    private PayAmountInitChangeMapper payloadAmountInitChangeMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public BaseResponse checkPayAmountInit(PayAmountInitRequest request,String uid) {
        if(request.getProjectId() == null){
            return BaseResponse.fail("pId不能为空");
        }
        PayAmountInit payAmountInit =baseMapper.getByPId(request.getProjectId());
        if (payAmountInit!=null){
            return BaseResponse.ok(1);
        }else {
            return BaseResponse.ok(0);
        }

    }

    @Override
    public BaseResponse updatePayAmountInit(PayAmountInitRequest request,String uid) {
        PayAmountInit payAmountInit =baseMapper.selectById(request.getId());
        if(request==null){
            return BaseResponse.fail("参数不能为空");
        }
        if (payAmountInit==null){
            return BaseResponse.fail("不存在初始化信息");
        }
        createPayAmountInitChange(request,uid,payAmountInit);
        if (request.getPiFundProjectAmount()!=null ){
             payAmountInit.setPiFundProjectAmount(request.getPiFundProjectAmount());
        }
        if (request.getPiGroupBookBalancesAmount()!=null ){
             payAmountInit.setPiGroupBookBalancesAmount(request.getPiGroupBookBalancesAmount());
        }
        if (request.getPiMemberFirmsBookBalancesAmount()!=null ){
             payAmountInit.setPiMemberFirmsBookBalancesAmount(request.getPiMemberFirmsBookBalancesAmount());
        }
        baseMapper.updateById(payAmountInit);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPayAmountInitList(PayAmountInitRequest request,String uid) {
        QueryMap<PayAmountInitRequest> params = new QueryMap<>(request);
        params.put("projectName");
        IPage<PayAmountInitVo> page = new Page<>(request.getPage(), request.getSize());
        List<PayAmountInitVo> payAmountInitVos
                = baseMapper.getPayAmountInitList(params.getQuery(),page);
        return BaseResponse.ok(payAmountInitVos,Long.valueOf(page.getTotal()).intValue());
    }

    private void createPayAmountInitChange(PayAmountInitRequest request,String uid,PayAmountInit payAmountInit) {
        PayAmountInitChange payAmountInitChange = new PayAmountInitChange();
        PayAmountInitChangeDto payAmountInitChangeDto = new PayAmountInitChangeDto();
        payAmountInitChangeDto.setId(GenerateUUID.getUUID());
        payAmountInitChangeDto.setPId(payAmountInit.getPId());
        payAmountInitChangeDto.setPiFundProjectAmount(request.getPiFundProjectAmount());
        payAmountInitChangeDto.setPiGroupBookBalancesAmount(request.getPiGroupBookBalancesAmount());
        payAmountInitChangeDto.setPiMemberFirmsBookBalancesAmount(request.getPiMemberFirmsBookBalancesAmount());
        payAmountInitChangeDto.setPiFundProjectAmountOld(payAmountInit.getPiFundProjectAmount());
        payAmountInitChangeDto.setPiGroupBookBalancesAmountOld(payAmountInit.getPiGroupBookBalancesAmount());
        payAmountInitChangeDto.setPiMemberFirmsBookBalancesAmountOld(payAmountInit.getPiMemberFirmsBookBalancesAmount());
        payAmountInitChangeDto.setCreateUid(uid);
        SysUser user = sysUserMapper.selectById(uid);
        if (user!=null){
            payAmountInitChangeDto.setCreateUname(user.getUName());
        }
        payAmountInitChangeDto.setCreateTime(DateTime.now());
        payAmountInitChangeDto.setStateTime(DateTime.now());
        payAmountInitChangeDto.setState(7);
        payAmountInitChangeDto.setFileTitle("payAmountInitChange");
        BeanUtil.copyProperties(payAmountInitChangeDto,payAmountInitChange);
        payloadAmountInitChangeMapper.insert(payAmountInitChange);
    }
}
