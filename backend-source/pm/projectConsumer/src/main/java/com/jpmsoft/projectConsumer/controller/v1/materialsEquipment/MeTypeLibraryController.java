package com.jpmsoft.projectConsumer.controller.v1.materialsEquipment;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeTypeLibraryRequest;
import com.jpmsoft.projectConsumer.feign.MaterialFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@Api(tags = "材料设备品种库", value = "材料设备品种库")
@RequestMapping("/material/v1/meTypeLibrary")
public class MeTypeLibraryController {
    @Autowired
    private MaterialFeign materialFeign;

    @ApiOperation(value = "新增或修改材料设备品种库")
    @PostMapping("/createOrUpdateMeTypeLibrary")
    public BaseResponse createOrUpdateMeTypeLibrary(@RequestBody MeTypeLibraryRequest request) {
        log.info("createOrUpdateMeTypeLibrary");
        return materialFeign.createOrUpdateMeTypeLibrary(request);
    }

    @ApiOperation(value = "分页查询材料设备品种库（可根据父ID筛选）")
    @PostMapping("/getMeTypeLibraryList")
    public BaseResponse getMeTypeLibraryList(@RequestBody MeTypeLibraryRequest request) {
        log.info("getMeTypeLibraryList");
        return materialFeign.getMeTypeLibraryList(request);
    }

    @ApiOperation(value = "上移或下移材料设备品种库")
    @GetMapping("/upOrDownMeTypeLibrarySort")
    public BaseResponse upOrDownMeTypeLibrarySort(@RequestParam String metId, @RequestParam String action,@RequestHeader String token) {
        log.info("upOrDownMeTypeLibrarySort");
        return materialFeign.upOrDownMeTypeLibrarySort(metId, action,token);
    }

    
    @ApiOperation(value = "删除材料设备品种库")
    @GetMapping("/delMeTypeLibraryById")
    public BaseResponse delMeTypeLibraryById(@RequestParam String id) {
        log.info("delMeTypeLibraryById");
        return materialFeign.delMeTypeLibraryById(id);
    }

    /**
     * 以树的形式返回品种库
     * [{label:"metName",value:"metId"}]
     * @return
     */
    @GetMapping("/getDownPullListWithTree")
    public BaseResponse getDownPullListWithTree(){
        return materialFeign.getMeTypeLibraryDownPullListWithTree();
    }

}
