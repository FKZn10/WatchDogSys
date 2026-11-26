package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.project.SingleProjectSolutionInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.SingleProjectSolutionInfo;
import com.jpmsoft.projectProducer.pojo.request.project.SingleProjectSolutionInfoRequest;
import com.jpmsoft.projectProducer.pojo.response.SingleProjectSolutionInfoResponse;
import com.jpmsoft.projectProducer.pojo.vo.project.SingleProjectSolutionInfoVo;
import com.jpmsoft.projectProducer.service.project.SingleProjectSolutionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SingleProjectSolutionInfoServiceImpl extends ServiceImpl<SingleProjectSolutionInfoMapper, SingleProjectSolutionInfo> implements SingleProjectSolutionInfoService {
    @Override
    public BaseResponse getSingleProjectSolutionInfo(SingleProjectSolutionInfoRequest request) {
        List<SingleProjectSolutionInfoResponse> list =new ArrayList<>();
        List<SingleProjectSolutionInfoVo> singleProjectSolutionInfos = baseMapper.selectSingleProjectSolutionInfoVo();
        Map<String, List<SingleProjectSolutionInfoVo>> map = singleProjectSolutionInfos.stream().collect(Collectors.groupingBy(SingleProjectSolutionInfoVo::getProjectName));
        for (Map.Entry<String, List<SingleProjectSolutionInfoVo>> entry : map.entrySet()) {
            SingleProjectSolutionInfoResponse response = new SingleProjectSolutionInfoResponse();
            response.setProjectName(entry.getKey());
            response.setList(entry.getValue());
            list.add(response);
        }
        return BaseResponse.ok(list);
    }
}
