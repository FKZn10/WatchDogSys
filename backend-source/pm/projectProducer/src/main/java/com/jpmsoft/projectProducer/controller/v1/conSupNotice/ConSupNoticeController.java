package com.jpmsoft.projectProducer.controller.v1.conSupNotice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeReplyService;
import com.jpmsoft.projectProducer.service.conSupNotice.IConSupNoticeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/v1/conSupNotice")
public class ConSupNoticeController {
    @Resource
    private IConSupNoticeService conSupNoticeService;
    @Resource
    private IConSupNoticeReplyService conSupNoticeReplyService;
    @Resource
    private RedisUtils redisUtils;


    /**
     * 首页
     */
    @PostMapping("/getConSupNoticeList")
    public BaseResponse getConSupNoticeList(@RequestBody Page page, @RequestHeader String token) {
        log.info("getConSupNoticeList");
        return conSupNoticeService.getConSupNoticeList(page,redisUtils.getTeId(token),redisUtils.getUid(token));
    }

    /**
     * 新增或者修改
     */
    @PostMapping("/createOrUpdateConSupNotice")
    public BaseResponse createOrUpdateConSupNotice(@RequestBody RequestBean requestBean, @RequestHeader String token) {
        log.info("createOrUpdateConSupNotice");
        return conSupNoticeService.createOrUpdateConSupNotice(requestBean,redisUtils.getTeId(token));
    }

    /**
     * 根据监理报表生成监理通知单并发送待办
     */
    @GetMapping("/createConSupNoticeByWorkReport")
    public BaseResponse createConSupNoticeByWorkReport(@RequestParam String wrId, @RequestHeader String token) {
        log.info("createConSupNoticeByWorkReport wrId:{}", wrId);
        return conSupNoticeService.createConSupNoticeByWorkReport(wrId, token);
    }

    /**
     * 查看
     */
    @PostMapping("/getConSupNoticeById")
    public BaseResponse getConSupNoticeById(@RequestBody RequestBean requestBean) {
        log.info("getConSupNoticeById");
        return conSupNoticeService.getConSupNoticeById(requestBean);
    }

    /**
     * 通知单审核
     */
    @PostMapping("/updateSnState")
    public BaseResponse updateSnState(@RequestBody RequestBean requestBean) {
        log.info("updateSnState");
        return conSupNoticeService.updateSnState(requestBean);
    }

    /**
     * 通知单回复
     */
    @PostMapping("/createOrUpdateConSupNoticeReply")
    public BaseResponse createOrUpdateConSupNoticeReply(@RequestBody RequestBean requestBean, @RequestHeader String token) {
        log.info("createOrUpdateConSupNoticeReply");
        return conSupNoticeReplyService.createOrUpdateConSupNoticeReply(requestBean,redisUtils.getTeId(token));
    }

    /**
     * 通知单回复审核
     */
    @PostMapping("/updateSnrState")
    public BaseResponse updateSnrState(@RequestBody RequestBean requestBean) {
        log.info("updateSnrState");
        return conSupNoticeReplyService.updateSnrState(requestBean);
    }

    /**
     * 通知单回复审核
     */
    @PostMapping("/getConSupNoticeReplyPageList")
    public BaseResponse getConSupNoticeReplyPageList(@RequestBody Page page, @RequestHeader String token) {
        log.info("getConSupNoticeReplyPageList");
        return conSupNoticeReplyService.getConSupNoticeReplyPageList(page,redisUtils.getTeId(token),redisUtils.getUid(token));
    }

    /**
     * 通知单回复详情
     */
    @PostMapping("/getConSupNoticeReplyById")
    public BaseResponse getConSupNoticeReplyById(@RequestBody RequestBean requestBean) {
        log.info("getConSupNoticeReplyById");
        return conSupNoticeReplyService.getConSupNoticeReplyById(requestBean);
    }
}
