package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.FlowKeyWordMapper;
import com.jpmsoft.userProducer.pojo.dto.FlowKeyWordDto;
import com.jpmsoft.userProducer.pojo.entity.FlowKeyWord;
import com.jpmsoft.userProducer.service.IFlowKeyWordService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FlowKeyWordImpl extends ServiceImpl<FlowKeyWordMapper, FlowKeyWord> implements IFlowKeyWordService {

    /**
     * 获取流程关键字信息列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getFlowKeyWordList(FlowKeyWordDto request) {
        QueryWrapper<FlowKeyWord> wrapper = new QueryWrapper<>();
        if (!CheckNullUtil.checkString(request.getkDescription()))
            wrapper.like("FlowName", request.getkDescription());
        if (!CheckNullUtil.checkString(request.getkCode()))
            wrapper.eq("T_Code", request.getkCode());
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        }
        List<FlowKeyWord> list = baseMapper.selectList(wrapper);
        List<FlowKeyWord> data = list.stream().skip((request.getPage() - 1) * request.getSize()).limit(request.getSize()).collect(Collectors.toList());
        return BaseResponse.ok(data, list.size());
    }

    /**
     * 新增或修改流程关键字信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse CreateOrUpdateFlowKeyWord(FlowKeyWordDto request) {
        FlowKeyWord pojo;
        if (CheckNullUtil.checkString(request.getkId()))
            pojo = new FlowKeyWord();
        else {
            pojo = baseMapper.selectById(request.getkId());
            if (CheckNullUtil.checkObject(pojo))
                return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        pojo.setkId(request.getkId());
        pojo.setkDescription(request.getkDescription());
        pojo.setkCode(request.getkCode());
        pojo.setTeId(request.getTeId());

        if (CheckNullUtil.checkString(request.getkId())) {
            pojo.setkId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
        } else
            baseMapper.updateById(pojo);
        return BaseResponse.ok();
    }

    /**
     * 删除流程关键字信息
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse DeleteFlowKeyWord(FlowKeyWordDto request) {
        if (CheckNullUtil.checkString(request.getkId()))
            return new BaseResponse("请求参数为空");
        FlowKeyWord pojo = baseMapper.selectById(request.getkId());
        if (CheckNullUtil.checkObject(pojo))
            return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(request.getkId());
        return BaseResponse.ok();

    }


    /**
     * 获取流程关键字信息Select
     *
     * @return
     */
    @Override
    public BaseResponse getFlowKeyWordSelect(String teId) {
        QueryWrapper<FlowKeyWord> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        List<FlowKeyWord> models = baseMapper.selectList(wrapper);
        List<Map<String, String>> vueSelect = new ArrayList<>();
        models.forEach(model -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", model.getkId());
            map.put("name", model.getkDescription());
            vueSelect.add(map);
        });
        return BaseResponse.ok(vueSelect);
    }
}
