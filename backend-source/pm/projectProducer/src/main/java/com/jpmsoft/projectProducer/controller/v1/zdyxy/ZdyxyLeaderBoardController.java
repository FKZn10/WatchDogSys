package com.jpmsoft.projectProducer.controller.v1.zdyxy;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.loadBoard.ProjectLoadRequest;
import com.jpmsoft.projectProducer.service.leaderBoard.LeaderBoardService;
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
    private LeaderBoardService leaderBoardService;
    //项目管理
    @PostMapping("/getAnnualMaintenanceBudgetInfo")
    public BaseResponse getAnnualMaintenanceBudgetInfo(@RequestBody ProjectLoadRequest request) {
        log.info("getAnnualMaintenanceBudgetInfo");
        return leaderBoardService.getAnnualMaintenanceBudgetInfo(request);
    }
    @PostMapping("/getAnnualMaintenanceBudgetInfoList")
    public BaseResponse getAnnualMaintenanceBudgetInfoList(@RequestBody ProjectLoadRequest request) {
        log.info("getAnnualMaintenanceBudgetInfoList");
        return leaderBoardService.getAnnualMaintenanceBudgetInfoList(request);
    }

}