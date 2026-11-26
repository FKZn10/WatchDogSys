package com.jpmsoft.projectConsumer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.loadBoard.*;
import com.jpmsoft.projectConsumer.feign.LeaderBoardFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/zdyxy/leaderBoard")
@Slf4j
public class ZdyxyLeaderBoardController {

    @Autowired
    private LeaderBoardFeign leadBoardFeign;
    //项目管理
    @PostMapping("/getAnnualMaintenanceBudgetInfo")
    public BaseResponse getAnnualMaintenanceBudgetInfo(@RequestBody ProjectLoadRequest request) {
        log.info("getAnnualMaintenanceBudgetInfo");
        return leadBoardFeign.getAnnualMaintenanceBudgetInfo(request);
    }
    @PostMapping("/getAnnualMaintenanceBudgetInfoList")
    public BaseResponse getAnnualMaintenanceBudgetInfoList(@RequestBody ProjectLoadRequest request) {
        log.info("getAnnualMaintenanceBudgetInfoList");
        return leadBoardFeign.getAnnualMaintenanceBudgetInfoList(request);
    }

}