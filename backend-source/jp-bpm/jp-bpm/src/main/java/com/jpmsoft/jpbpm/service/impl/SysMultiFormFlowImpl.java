package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysMultiFormFlowMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysMultiFormFlow;
import com.jpmsoft.jpbpm.service.SysMultiFormFlowService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMultiFormFlowImpl extends ServiceImpl<SysMultiFormFlowMapper, SysMultiFormFlow> implements SysMultiFormFlowService {
    @Override
    public BaseResponse create(List<SysMultiFormFlow> request,String customerId) {
        List<String> idlist = request.stream().map(n -> n.getMffId()).collect(Collectors.toList());
        List<SysMultiFormFlow> proList = baseMapper.selectBatchIds(idlist);
        if (proList.size() > 0) {
            List<SysMultiFormFlow> adds = new ArrayList<>();
            List<SysMultiFormFlow> ups = new ArrayList<>();
            request.forEach(n -> {
                n.setTeId(customerId);
                List<SysMultiFormFlow> nlist = proList.stream().filter(item -> item.getMffId().equals(n.getMffId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(nlist)) {
                    ups.add(n);
                } else {
                    adds.add(n);
                }

            });
            if (!CollectionUtils.isEmpty(adds)) this.saveBatch(adds);
            if (!CollectionUtils.isEmpty(ups)) this.updateBatchById(ups);
        } else {
            this.saveBatch(request);
        }
        return BaseResponse.ok();
    }
    @Override
    public List<SysMultiFormFlow> GetSysMultiFormFlow(SysMultiFormFlow request,String customerId) {
        if (!ValidateUtil.isEmpty(request.getMffMainFileId()) || !ValidateUtil.isEmpty(request.getMffFileId()) || !ValidateUtil.isEmpty(request.gettCode())) {
            QueryWrapper<SysMultiFormFlow> wrapper = new QueryWrapper<>();
            if (!ValidateUtil.isEmpty(request.getMffMainFileId())) {
                wrapper.eq("Mff_Main_File_Id", request.getMffMainFileId());
            }
            if (!ValidateUtil.isEmpty(request.getMffFileId())) {
                wrapper.eq("Mff_File_Id", request.getMffFileId());
            }
            if (!ValidateUtil.isEmpty(request.gettCode())) {
                wrapper.eq("T_Code", request.gettCode());
            }
            if(!"All".equals(customerId) ){
                wrapper.eq("Te_Id", customerId);
            }
            List<SysMultiFormFlow> Lisst = baseMapper.selectList(wrapper);
            return Lisst;
        } else {
            return new ArrayList<>();
        }
    }
}
