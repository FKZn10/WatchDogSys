package com.jpmsoft.projectProducer.controller.v1.oa;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeRequest;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNotice;
import com.jpmsoft.projectProducer.service.oa.IOaNoticeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/OA/Notice")
public class OaNoticeController {

    @Resource
    private IOaNoticeService oaNoticeService;
    @Resource
    private RedisUtils redisUtils;
    @TenantId
    @PostMapping("/createOrUpdateOaNotice")
    public BaseResponse createOrUpdateOaNotice(@RequestBody OaNoticeRequest request) {
        log.info("createOrUpdateOaNotice");
        return oaNoticeService.createOrUpdateOaNotice(request);
    }

    /**
     * 批量插入
     * @param oaNoticeRequestList
     * @return
     */
    @PostMapping("/createOaNotice")
    public BaseResponse createOaNotice(@RequestBody List<OaNoticeRequest> oaNoticeRequestList, @RequestHeader String token) {
        log.info("createOaNotice");
        String teId=redisUtils.getTeId(token);
        List<OaNotice> oaNoticeList=new ArrayList<>();
        OaNotice oaNotice=new OaNotice();
        if(CollectionUtil.isNotEmpty(oaNoticeRequestList)){
            for (OaNoticeRequest oaNoticeRequest : oaNoticeRequestList) {
                oaNotice=new OaNotice();
                BeanUtil.copyProperties(oaNoticeRequest,oaNotice);
                oaNotice.setNCreateTime(new Date());
                oaNotice.setTeId(teId);
                oaNoticeList.add(oaNotice);
            }
        }
        return BaseResponse.ok(oaNoticeService.saveBatch(oaNoticeList));
    }

    @GetMapping("/getOaNoticeByNid")
    public BaseResponse getOaNoticeByNid(@RequestParam String nId) {
        log.info("getOaNoticeByNid");
        return oaNoticeService.getOaNoticeByNid(nId);
    }

    @TenantId
    @PostMapping("/getOaNoticeList")
    public BaseResponse getOaNoticeList(@RequestBody OaNoticeRequest request) {
        log.info("getOaNoticeList");
        return oaNoticeService.getOaNoticeList(request);
    }

    @TenantId
    @PostMapping("/getUserOaNoticeList")
    public BaseResponse getUserOaNoticeList(@RequestBody OaNoticeRequest request) {
        log.info("getUserOaNoticeList");
        return oaNoticeService.getUserOaNoticeList(request);
    }


    @GetMapping("/deleteOaNoticeByNid")
    public BaseResponse deleteOaNoticeByNid(@RequestParam String nId) {
        log.info("deleteOaNoticeByNid");
        return oaNoticeService.deleteOaNoticeByNid(nId);
    }

    /**
     * 添加查看记录
     *
     * @param requestBean
     * @author:zhangl
     */

    @PostMapping("/createOrUpdateViewRecords")
    public BaseResponse createOrUpdateViewRecords(@RequestBody RequestBean requestBean) {
        log.info("createOrUpdateViewRecords");
        return oaNoticeService.createOrUpdateViewRecords(requestBean);
    }

    /**
     * 获取分页列表 查看记录
     */
    @PostMapping("/getViewRecordsById")
    public BaseResponse getViewRecordsById(@RequestBody RequestBean requestBean) {
        log.info("getViewRecordsById");
        return oaNoticeService.getViewRecordsById(requestBean);
    }

}
