package com.jpmsoft.projectProducer.controller.v1.projectOutline;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineMonitorMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/v1/outlineMonitor")
public class OutlineMonitorController {


    @Resource
    private OutlineMonitorMapper outlineMonitorMapper;

    @PostMapping("/insertMonitor")
    public BaseResponse insertMonitor(@RequestBody OutlineMonitor data) {
        data.setId(GenerateUUID.getUUID());
        outlineMonitorMapper.insert(data);
        return BaseResponse.ok();
    }

    @GetMapping("/getMonitor")
    public BaseResponse getMonitor(@RequestHeader String token) {
        QueryWrapper<OutlineMonitor> wrapper = new QueryWrapper<>();
        wrapper.eq("token", token);
        List<OutlineMonitor> list = outlineMonitorMapper.selectList(wrapper);
        if (list.size() > 0) {
            OutlineMonitor data = list.get(0);
            outlineMonitorMapper.delete(wrapper);
            return BaseResponse.ok(data);
        }
        return BaseResponse.ok();
    }

}
