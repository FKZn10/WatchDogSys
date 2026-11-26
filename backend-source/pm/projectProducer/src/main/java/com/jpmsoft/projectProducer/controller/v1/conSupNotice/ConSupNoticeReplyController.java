package com.jpmsoft.projectProducer.controller.v1.conSupNotice;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 监理通知回复单
 */
@Slf4j
@RestController
@RequestMapping("/v1/conSupNoticeReply")
public class ConSupNoticeReplyController {
    @Resource
    private IConSupNoticeReplyService conSupNoticeReplyService;

    /**
     * 获取回复次序
     *
     * @param pId
     * @param snId
     * @return
     */
    @GetMapping("/getReplyOrder")
    public BaseResponse getReplyOrder(@RequestParam String pId, @RequestParam String snId) {
        log.info("getReplyOrder");
        return conSupNoticeReplyService.getReplyOrder(pId, snId);
    }
}
