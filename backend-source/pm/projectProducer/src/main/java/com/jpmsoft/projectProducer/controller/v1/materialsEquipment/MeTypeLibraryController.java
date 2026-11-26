package com.jpmsoft.projectProducer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeTypeLibraryRequest;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeTypeLibraryService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/v1/meTypeLibrary")
public class MeTypeLibraryController {

    @Resource
    private IMeTypeLibraryService meTypeLibraryService;
    @Resource
    private RedisUtils redisUtils;


    @TenantId
    @PostMapping("/createOrUpdateMeTypeLibrary")
    public BaseResponse createOrUpdateMeTypeLibrary(@RequestBody MeTypeLibraryRequest request) {
        log.info("createOrUpdateMeTypeLibrary");
        return meTypeLibraryService.createOrUpdateMeTypeLibrary(request);
    }

    @TenantId
    @PostMapping("/getMeTypeLibraryList")
    public BaseResponse getMeTypeLibraryList(@RequestBody MeTypeLibraryRequest request) {
        log.info("getMeTypeLibraryList");
        return meTypeLibraryService.getMeTypeLibraryList(request);
    }

    @GetMapping("/upOrDownMeTypeLibrarySort")
    public BaseResponse upOrDownMeTypeLibrarySort(@RequestParam String metId, @RequestParam String action,@RequestHeader String token) {
        log.info("upOrDownMeTypeLibrarySort");
        return meTypeLibraryService.upOrDownMeTypeLibrarySort(metId, action,redisUtils.getTeId(token));
    }

    @GetMapping("/delMeTypeLibraryById")
    public BaseResponse delMeTypeLibraryById(@RequestParam String id) {
        log.info("delMeTypeLibraryById");
        return meTypeLibraryService.delMeTypeLibraryById(id);
    }

    /**
     * 以树的形式返回品种库
     * [{label:"metName",value:"metId"}]
     * @return
     */
    @GetMapping("/getDownPullListWithTree")
    public BaseResponse getDownPullListWithTree(){
        return meTypeLibraryService.getDownPullListWithTree();
    }
}
