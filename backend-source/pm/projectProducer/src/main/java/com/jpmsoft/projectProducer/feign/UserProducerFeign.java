package com.jpmsoft.projectProducer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.DepartmentPDto;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.PositionPDto;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.PositionUserPDto;
import com.jpmsoft.projectProducer.pojo.dto.other.ImportTDepartmentDto;
import com.jpmsoft.projectProducer.pojo.dto.other.UserDto;
import com.jpmsoft.projectProducer.pojo.dto.other.UserPositionPDTO_CJ;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-producer", contextId = "bpm")
public interface UserProducerFeign {

    /**
     * code获取配置信息
     *
     * @param code
     * @return
     */
    @GetMapping("/v1/SystemManage/GetConfigValueByCode")
    BaseResponse GetConfigValueByCode(@RequestParam String code);

    /**
     * 获取用户所在组织机构及其下级组织机构的单位部门的单位ID
     *
     * @param uId
     * @return
     */
    @GetMapping("/v1/OrganizationManage/getAllChildDepCompany")
    List<String> getAllChildDepCompany(@RequestParam String uId);

    @PostMapping("/v1/FlowManage/getFileTemplateByCode")
    FileTemplateTree getFileTemplateByCode(@RequestParam String tCode);

    @PostMapping("/v1/FlowManage/getFileTemplateByCodes")
    List<FileTemplateTree> getFileTemplateByCodes(@RequestBody List<String> tCodes);

    /**
     * 获取用户所在组织机构及其下级组织机构的单位部门的单位ID
     *
     * @param uIds
     * @return
     */
    @GetMapping("/getUserInfoByIds")
    BaseResponse getUserInfoByIds(@RequestBody List<String> uIds);

    @GetMapping("/v1/HolidayVacations/getBeforeWorkDay")
    BaseResponse getBeforeWorkDay(@RequestParam Integer day);

    @GetMapping("/v1/OrganizationManage/getPmUserInProjectDep")
    BaseResponse getPmUserInProjectDep(@RequestParam String pId, @RequestHeader String token);

    @GetMapping("/v1/OrganizationManage/getLogUserInfoById")
    LogUserInfoVo getLogUserInfoById(@RequestParam String uid);

    @GetMapping("/v1/OrganizationManage/getUserRole")
    BaseResponse getUserRole(@RequestParam String uId);

    /**
     * 导入项目组织机构
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/OrganizationManage/ImportT_Department")
    BaseResponse ImportT_Department(@RequestBody ImportTDepartmentDto request);

    /**
     * 同步项目组织机构模板 支持重复同步
     *
     * @param request
     * @return
     */
    @PostMapping("/v1/OrganizationManage/syncTDepartment")
    BaseResponse syncTDepartment(@RequestBody ImportTDepartmentDto request);

    @PostMapping("/v1/SystemManage/getDictionaryTreeByCodes")
    BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto selectDto);


    @PostMapping("/v1/OrganizationManage/CUDDepartment_P")
    BaseResponse CUDDepartment_P(@RequestBody @Validated DepartmentPDto request);

    @PostMapping("/v1/OrganizationManage/CreateOrUpdatePosition_P")
    BaseResponse CreateOrUpdatePosition_P(@RequestBody PositionPDto request);

    @PostMapping("/v1/OrganizationManage/CreateOrDeletePP_U")
    BaseResponse CreateOrDeletePP_U(@RequestBody @Validated PositionUserPDto request);

    @PostMapping("/v1/OrganizationManage/getDepartment_PTreeList")
    BaseResponse getDepartment_PTreeList(@RequestBody DepartmentPDto request);


    @GetMapping("/v1/OrganizationManage/GetRoleCheckBox")
    BaseResponse GetRoleCheckBox(@RequestParam(required = false) String powerId
            , @RequestParam(required = false) String cId, @RequestHeader(required = false) String token);

    @GetMapping("/v1/OrganizationManage/getUserByToken")
    BaseResponse getUserByToken(@RequestHeader String token);
    @PostMapping("/sms/v1/shortMessage/batchCreateShortMessage")
    BaseResponse batchCreateShortMessage(@RequestBody List<SysShortMessageModel> sysShortMessageModelList);
    @PostMapping("/v1/OrganizationManage/deleteOwnerDepartmentP")
    BaseResponse deleteOwnerDepartmentP(@RequestBody DepartmentPDto request);

    @GetMapping("/v1/OrganizationManage/syncPpOwnerUnit")
    BaseResponse syncPpOwnerUnit(@RequestParam("pId") String pId, @RequestParam("pdCode") String pdCode, @RequestParam("ownerUnit") String ownerUnit);

    @GetMapping("/v1/OrganizationManage/getProjectPositionUserGlManager")
    UserPositionPDTO_CJ getProjectPositionUserGlManager(@RequestParam("pId") String pId);

    @PostMapping("/v1/OrganizationManage/getUserCompanyDep")
    BaseResponse getUserCompanyDep(@RequestBody List<String> departmentIds, @RequestHeader String token);


    @PostMapping("/v1/OrganizationManage/GetUserListByDPId")
    BaseResponse GetUserListByDPId(@RequestBody UserDto request);
}
