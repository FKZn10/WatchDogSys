package com.jpmsoft.projectProducer.service.project.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.project.PreProjectOrgStructRequest;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUserResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.project.PreProjectOrgStructUnitMapper;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.DepartmentPDto;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.PositionPDto;
import com.jpmsoft.projectProducer.pojo.dto.departmentP.PositionUserPDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.project.*;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 项目组织机构单位表(PreProjectOrgStructUnit)表服务实现类
 *
 * @author c
 * @since 2024-12-11 17:39:11
 */
@Service
public class PreProjectOrgStructUnitServiceImpl extends ServiceImpl<PreProjectOrgStructUnitMapper, PreProjectOrgStructUnit> implements IPreProjectOrgStructUnitService {

    @Resource
    private IPreProjectOrgStructUserService preProjectOrgStructUserService;

    @Resource
    private ISysUserService iSysUserService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;

    @Autowired
    private UserProducerFeign userProducerFeign;

    @Autowired
    private IPreProjectOrgStructChangeService preProjectOrgStructChangeService;

    @Autowired
    private IPreProjectOrgStructUnitChangeService preProjectOrgStructUnitChangeService;

    @Autowired private IPreProjectOrgStructUserChangeService iPreProjectOrgStructUserChangeService;




    @Override
    public List<PreProjectOrgStructUnitResponse> getByOrgStructId(String orgStructId) {
        List<PreProjectOrgStructUnitResponse> list = baseMapper.getByOrgStructId(orgStructId);
        if(CollectionUtil.isNotEmpty(list)){
            List<String> collect = list.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<PreProjectOrgStructUserResponse> userList = preProjectOrgStructUserService.getByOrgStructUnitIdList(collect);
            for (PreProjectOrgStructUnitResponse structUnit : list) {
                List<PreProjectOrgStructUserResponse> userConllectList = userList.stream().filter(item -> item.getOrgStructUnitId().equals(structUnit.getId())).collect(Collectors.toList());
                structUnit.setPreProjectOrgStructUserList(userConllectList);
            }
        }
        return list;
    }

    //保存项目组织机构
    @Override
    public BaseResponse saveSorganization(PreProjectOrgStructRequest request, String uId) {

         SysUser user = iSysUserService.getById(uId);
         if(ValidateUtil.isEmpty(user))return  BaseResponse.fail("用户不存在");

        //增加维护记录
        PreProjectOrgStructChange preChange = new PreProjectOrgStructChange();
        preChange.setId(GenerateUUID.getUUID());
        preChange.setPId(request.getpId());
        preChange.setRemark(request.getRemark());
        preChange.setCreateTime(new Date());
        preChange.setCreateUid(uId);
        preChange.setCreateUname(user.getUName());
        preChange.setOrgStructId(request.getId());
        preProjectOrgStructChangeService.save(preChange);


         //五方责任主体
         List<PreProjectOrgStructUnitResponse> collect = request.getPreProjectOrgStructUnitList().stream().filter(e -> e.getUnitType().equals("02")).collect(Collectors.toList());
//        List<PreProjectOrgStructUnit> data =  request.getPreProjectOrgStructUnitList().stream().map(e->{
//             PreProjectOrgStructUnit unit  = new PreProjectOrgStructUnit();
//             BeanUtils.copyProperties(e,unit);
////             unit.setId(GenerateUUID.getUUID());
//             unit.setCreateTime(new Date());
//             unit.setCreateUid(user.getUId());
//             unit.setCreateUname(user.getUName());
//             unit.setOrgStructId(request.getId());
//             return unit;
//         }).collect(Collectors.toList());
//        if(ValidateUtil.isNotEmpty(data)){
//            saveBatch(data);
//        }
        //用户数据
        List<PreProjectOrgStructUser> strUserList = request.getPreProjectOrgStructUnitList().stream().filter(e -> !CollectionUtils.isEmpty(e.getPreProjectOrgStructUserList()))
                .flatMap(e -> e.getPreProjectOrgStructUserList().stream()).map(e->{
                    PreProjectOrgStructUser strUser = new PreProjectOrgStructUser();
                    BeanUtils.copyProperties(e,strUser);
//                    strUser.setId(GenerateUUID.getUUID());
                    strUser.setUserId(user.getUId());
                    strUser.setCreateTime(new Date());
                    strUser.setCreateUname(user.getUName());
//                    strUser.setOrgStructUnitId(request.getId());
                    return strUser;
                }).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(strUserList)){
            strUserList = strUserList.stream().filter(e-> !e.getMaintenanceType().equals("移除")).collect(Collectors.toList());
            preProjectOrgStructUserService.saveBatch(strUserList);
        }


        //生成项目组织机构
        DepartmentPDto dto = new DepartmentPDto();
        dto.setPdName("五方责任主体");
        dto.setpId(request.getpId());
        dto.setPdParentid("0"); //层级
        dto.setPdStatus("1"); //状态
        dto.setType("create");
        dto.setPdCreateuName(request.getCreateUname());
        dto.setPdCreateuID(request.getCreateUid());
        dto.setPdCode("WFZRZT");//五方责任主体
        dto.setPdId(GenerateUUID.getUUID());
        userProducerFeign.CUDDepartment_P(dto);
        //单位id
        Map<String,Object> map  = new HashMap<>();
        //生成单位
        List<String> list  = new ArrayList<>(Arrays.asList("项目管理单位","勘察单位","设计单位","施工单位","监理单位"));
        for (String s : list) {
            DepartmentPDto dtoCj = new DepartmentPDto();
            dtoCj.setPdName(s);
            dtoCj.setpId(request.getpId());
            dtoCj.setPdParentid(dto.getPdId()); //层级
            dtoCj.setPdStatus("1"); //状态
            dtoCj.setType("create");
            dtoCj.setPdCreateuName(request.getCreateUname());
            dtoCj.setPdCreateuID(request.getCreateUid());
            dtoCj.setPdCode(s);
            dtoCj.setPdId(GenerateUUID.getUUID());
            map.put(s,dtoCj.getPdId());
            userProducerFeign.CUDDepartment_P(dtoCj);
        }


        // 用于存储用户和他们对应的职位ID
        Map<String, String> userPositionIds = new HashMap<>();

        // 用于存储所有单位的职位名称和职位ID映射
        Map<String, String> allCreatedPositions = new HashMap<>();

        // 生成岗位
        for (PreProjectOrgStructUnitResponse preUser : collect) {
            // 根据单位类型获取ID
            String id = "";
            switch (preUser.getOrgStructType()) {
                case "01":
                    id = String.valueOf(map.get("项目管理单位"));
                    break;
                case "02":
                    id = String.valueOf(map.get("勘察单位"));
                    break;
                case "03":
                    id = String.valueOf(map.get("设计单位"));
                    break;
                case "04":
                    id = String.valueOf(map.get("施工单位"));
                    break;
                case "05":
                    id = String.valueOf(map.get("监理单位"));
                    break;
            }

            // 为每个单位创建一个新的Map来跟踪已创建的职位
            Map<String, String> createdPositionsForUnit = new HashMap<>();
            // 生成岗位
            for (PreProjectOrgStructUserResponse re : preUser.getPreProjectOrgStructUserList()) {
                // 检查是否已经存在相同职位名称（针对当前单位）
                if (!createdPositionsForUnit.containsKey(re.getOrgPost())) {
                    PositionPDto postDto = new PositionPDto();
                    postDto.setPpCreatetime(new Date());
                    postDto.setPpCreateuId(user.getUId());
                    postDto.setPpCreateuName(user.getUName());
                    postDto.setPpName(re.getOrgPost());
                    postDto.setPpStatus("1");
                    postDto.setPpCode(re.getOrgPost());
                    postDto.setRoleIds("");
                    postDto.setPdId(String.valueOf(id));

                    BaseResponse baseResponse = userProducerFeign.CreateOrUpdatePosition_P(postDto);
                    String positionId = (String) baseResponse.getData();
                    createdPositionsForUnit.put(re.getOrgPost(), positionId);
                    userPositionIds.put(re.getUserId(), positionId);
                    allCreatedPositions.put(re.getOrgPost(), positionId);
                } else {
                    String existingPositionId = createdPositionsForUnit.get(re.getOrgPost());
                    userPositionIds.put(re.getUserId(), existingPositionId);
                }
            }
            for (PreProjectOrgStructUserResponse pre : preUser.getPreProjectOrgStructUserList()) {
                PositionUserPDto ppdto  = new PositionUserPDto();
                ppdto.setType("create");
                ppdto.setuId(pre.getUserId());
                 String ppId = allCreatedPositions.get(pre.getOrgPost());
                ppdto.setPpId(ppId);
                ppdto.setPupId(GenerateUUID.getUUID());
                userProducerFeign.CreateOrDeletePP_U(ppdto);
            }
        }
        //走流程
        sysFileService.saveFile(request.getId(), request.gettId(), request.getState() + "", request.getCreateUid(), request.getCreateUname()
                , request.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                request.getFileRelationId(), null, request.gettCode(), null);
        sysFileAnnexService.refreshRelationFile(
                Arrays.asList(request.getProofFile()),
                Arrays.asList(request.getProofFileIds()
                ),request.getId());
        return BaseResponse.ok();
    }


    @Override
    public BaseResponse changeSorganization(PreProjectOrgStructRequest request, String uId) {
        LambdaQueryWrapper<PreProjectOrgStructUnit> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreProjectOrgStructUnit::getOrgStructId,request.getId());
         List<PreProjectOrgStructUnit> itsList = baseMapper.selectList(queryWrapper);
         List<String> ids = itsList.stream().map(e -> e.getId()).collect(Collectors.toList());
        preProjectOrgStructUserService.deletepId(ids);
        SysUser user = iSysUserService.getById(uId);
        if(ValidateUtil.isEmpty(user))return  BaseResponse.fail("用户不存在");

        //增加维护记录
        PreProjectOrgStructChange preChange = new PreProjectOrgStructChange();
        preChange.setId(GenerateUUID.getUUID());
        preChange.setPId(request.getpId());
        preChange.setRemark(request.getRemark());
        preChange.setCreateTime(new Date());
        preChange.setCreateUid(uId);
        preChange.setCreateUname(user.getUName());
        preChange.setOrgStructId(request.getId());
        preChange.setState(0);
        preProjectOrgStructChangeService.save(preChange);
        //五方责任主体
//        List<PreProjectOrgStructUnitResponse> collect = request.getPreProjectOrgStructUnitList().stream().filter(e -> e.getUnitType().equals("02")).collect(Collectors.toList());

        List<String> unitIsidS = new ArrayList<>();
         List<PreProjectOrgStructUnitResponse> unitIds = request.getPreProjectOrgStructUnitList().stream().filter(e -> e.getUnitType().equals("03")).collect(Collectors.toList());
         if(!CollectionUtils.isEmpty(unitIds)){
             List<String> unids = unitIds.stream().map(e -> e.getId()).collect(Collectors.toList());
             LambdaQueryWrapper<PreProjectOrgStructUnit> unitQuery = new LambdaQueryWrapper<>();
             unitQuery.in(PreProjectOrgStructUnit::getId,unids);
             List<PreProjectOrgStructUnit> preProjectOrgStructUnits = baseMapper.selectList(unitQuery);
             unitIsidS = preProjectOrgStructUnits.stream().map(e-> e.getId()).collect(Collectors.toList());
         }
                //保存其他单位
        List<String> finalUnitIsidS = unitIsidS;
        List<PreProjectOrgStructUnit> data =  request.getPreProjectOrgStructUnitList().stream().filter(e->e.getUnitType().equals("03")).map(e->{
            PreProjectOrgStructUnit unit  = new PreProjectOrgStructUnit();
                 if(!finalUnitIsidS.contains(e.getId())){
                     BeanUtils.copyProperties(e,unit);
                     unit.setCreateTime(new Date());
                     unit.setCreateUid(user.getUId());
                     unit.setCreateUname(user.getUName());
                     unit.setOrgStructId(request.getId());
                     return unit;
                 }
                 return null;
         }).filter(Objects::nonNull).collect(Collectors.toList());
        if(ValidateUtil.isNotEmpty(data)){
            saveBatch(data);
        }


         List<String> strUnitIds = request.getPreProjectOrgStructUnitList().stream().map(e -> e.getId()).collect(Collectors.toList());
         List<PreProjectOrgStructUnitChange> preProjectOrgStructUnitChanges = preProjectOrgStructUnitChangeService.listByIds(strUnitIds);
         List<String> collect1 = preProjectOrgStructUnitChanges.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<PreProjectOrgStructUnitChange> changeData = new ArrayList<>();
        request.getPreProjectOrgStructUnitList().forEach(e->{
            if(!collect1.contains(e.getId())){
                PreProjectOrgStructUnitChange unit  = new PreProjectOrgStructUnitChange();
                unit.setId(e.getId());
                unit.setOrgStructChangeId(preChange.getId());
                unit.setUnitType(e.getUnitType());
                unit.setOrgStructType(e.getOrgStructType());
                unit.setHaveUnit(e.getHaveUnit());
                unit.setUnitId(e.getUnitId());
                unit.setCreateTime(new Date());
                unit.setCreateUid(user.getUId());
                unit.setCreateUname(user.getUName());
                changeData.add(unit);
            }
        });
        if(ValidateUtil.isNotEmpty(changeData)){
            preProjectOrgStructUnitChangeService.saveBatch(changeData);
        }
        //人员列表维护
        List<PreProjectOrgStructUserChange> userChangeList =  new ArrayList<>();

         List<PreProjectOrgStructUserChange> userChangeListdata = iPreProjectOrgStructUserChangeService.list();
        //人员列表
        List<PreProjectOrgStructUser> strUserList = request.getPreProjectOrgStructUnitList().stream().filter(e -> !CollectionUtils.isEmpty(e.getPreProjectOrgStructUserList()))
                .flatMap(e -> e.getPreProjectOrgStructUserList().stream()).map(e->{
                    PreProjectOrgStructUser strUser = new PreProjectOrgStructUser();
                    BeanUtils.copyProperties(e,strUser);
                    strUser.setUserId(e.getUserId());
                    strUser.setCreateTime(new Date());
                    strUser.setUserName(e.getUserName());
                    strUser.setCreateUname(user.getUName());
                    strUser.setCreateUid(user.getUId());
                    //添加维护记录
                     long count = userChangeListdata.stream().filter(g -> g.getOrgStructUnitChangeId().equals(e.getOrgStructUnitId()) && g.getUserId().equals(e.getUserId())).count();
                     if(count == 0){
                         PreProjectOrgStructUserChange userChange = new PreProjectOrgStructUserChange();
                         userChange.setId(GenerateUUID.getUUID());
                         userChange.setOrgStructUnitChangeId(e.getOrgStructUnitId());
                         userChange.setUserId(e.getUserId());
                         userChange.setUserName(e.getUserName());
                         userChange.setCreateUname(user.getUName());
                         userChange.setOrgPost(e.getOrgPost());
                         userChange.setOrgContact(e.getOrgContact());
                         userChange.setOrgSort(e.getOrgSort());
                         userChange.setCreateUid(user.getUId());
                         userChange.setCreateTime(new Date());
                         userChange.setOrgStructType(e.getOrgStructType());
                         userChange.setMaintenanceType(e.getMaintenanceType());
                         userChange.setChangeType(preChange.getId()); //用作标识维护记录
                         userChangeList.add(userChange);
                     }
                    return strUser;
                }).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(strUserList)){
            strUserList = strUserList.stream().filter(e-> !e.getMaintenanceType().equals("移除")).collect(Collectors.toList());
            preProjectOrgStructUserService.saveBatch(strUserList);
        }

        //保存维护表
        if(!CollectionUtils.isEmpty(userChangeList)){
            iPreProjectOrgStructUserChangeService.saveBatch(userChangeList);
        }

//        //删除组织机构
//        DepartmentPDto pdto = new DepartmentPDto();
//        pdto.setpId(request.getpId());
//         BaseResponse departmentPTreeList = userProducerFeign.getDepartment_PTreeList(pdto);
//         if(ValidateUtil.isNotNull(departmentPTreeList.getData())){
//             List<String> keys = extractAllKeys(departmentPTreeList);
//             //清空， 重新赋值,删除机构
//             for (String key : keys) {
//                 pdto = new DepartmentPDto();
//                 pdto.setPdId(key);
//                 pdto.setType("delete");
//                 userProducerFeign.CUDDepartment_P(pdto);
//             }
//         }
//        //生成项目组织机构
//        DepartmentPDto dto = new DepartmentPDto();
//        dto.setPdName("五方责任主体");
//        dto.setpId(request.getpId());
//        dto.setPdParentid("0"); //层级
//        dto.setPdStatus("1"); //状态
//        dto.setType("create");
//        dto.setPdCreateuName(request.getCreateUname());
//        dto.setPdCreateuID(request.getCreateUid());
//        dto.setPdCode("WFZRZT");//五方责任主体
//        dto.setPdId(GenerateUUID.getUUID());
//        userProducerFeign.CUDDepartment_P(dto);
//        //单位id
//        Map<String,Object> map  = new HashMap<>();
//        //生成单位
//        List<String> list  = new ArrayList<>(Arrays.asList("项目管理单位","勘察单位","设计单位","施工单位","监理单位"));
//        for (String s : list) {
//            DepartmentPDto dtoCj = new DepartmentPDto();
//            dtoCj.setPdName(s);
//            dtoCj.setpId(request.getpId());
//            dtoCj.setPdParentid(dto.getPdId()); //层级
//            dtoCj.setPdStatus("1"); //状态
//            dtoCj.setType("create");
//            dtoCj.setPdCreateuName(request.getCreateUname());
//            dtoCj.setPdCreateuID(request.getCreateUid());
//            dtoCj.setPdCode(s);
//            dtoCj.setPdId(GenerateUUID.getUUID());
//            map.put(s,dtoCj.getPdId());
//            userProducerFeign.CUDDepartment_P(dtoCj);
//        }
//
//
//        // 用于存储用户和他们对应的职位ID
//        Map<String, String> userPositionIds = new HashMap<>();
//
//        // 用于存储所有单位的职位名称和职位ID映射
//        Map<String, String> allCreatedPositions = new HashMap<>();
//
//        // 生成岗位
//        for (PreProjectOrgStructUnitResponse preUser : collect) {
//            // 根据单位类型获取ID
//            String id = "";
//            switch (preUser.getOrgStructType()) {
//                case "01":
//                    id = String.valueOf(map.get("项目管理单位"));
//                    break;
//                case "02":
//                    id = String.valueOf(map.get("勘察单位"));
//                    break;
//                case "03":
//                    id = String.valueOf(map.get("设计单位"));
//                    break;
//                case "04":
//                    id = String.valueOf(map.get("施工单位"));
//                    break;
//                case "05":
//                    id = String.valueOf(map.get("监理单位"));
//                    break;
//            }
//
//            // 为每个单位创建一个新的Map来跟踪已创建的职位
//            Map<String, String> createdPositionsForUnit = new HashMap<>();
//            // 生成岗位
//            for (PreProjectOrgStructUserResponse re : preUser.getPreProjectOrgStructUserList()) {
//                // 检查是否已经存在相同职位名称（针对当前单位）
//                if (!createdPositionsForUnit.containsKey(re.getOrgPost())) {
//                    PositionPDto postDto = new PositionPDto();
//                    postDto.setPpCreatetime(new Date());
//                    postDto.setPpCreateuId(user.getUId());
//                    postDto.setPpCreateuName(user.getUName());
//                    postDto.setPpName(re.getOrgPost());
//                    postDto.setPpStatus("1");
//                    postDto.setPpCode(re.getOrgPost());
//                    postDto.setRoleIds(""); //权限
//                    postDto.setPdId(String.valueOf(id));
//
//                    BaseResponse baseResponse = userProducerFeign.CreateOrUpdatePosition_P(postDto);
//                    String positionId = (String) baseResponse.getData();
//                    createdPositionsForUnit.put(re.getOrgPost(), positionId);
//                    userPositionIds.put(re.getUserId(), positionId);
//                    allCreatedPositions.put(re.getOrgPost(), positionId);
//                } else {
//                    String existingPositionId = createdPositionsForUnit.get(re.getOrgPost());
//                    userPositionIds.put(re.getUserId(), existingPositionId);
//                }
//            }
//            for (PreProjectOrgStructUserResponse pre : preUser.getPreProjectOrgStructUserList()) {
//                PositionUserPDto ppdto  = new PositionUserPDto();
//                ppdto.setType("create");
//                ppdto.setuId(pre.getUserId());
//                String ppId = allCreatedPositions.get(pre.getOrgPost());
//                ppdto.setPpId(ppId);
//                ppdto.setPupId(GenerateUUID.getUUID());
//                userProducerFeign.CreateOrDeletePP_U(ppdto);
//            }
//        }
        //走流程
         SysFile sysFile = sysFileService.getById(request.getId());
        if(ValidateUtil.isEmpty(sysFile)){
            sysFileService.saveFile(request.getId(), request.gettId(), request.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , request.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(
                    Arrays.asList(request.getProofFile())
                   ,request.getId());
        }else{
            sysFileService.saveFile(request.getId(), request.gettId(), request.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , request.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(
                    Arrays.asList(request.getProofFile()),
                    Arrays.asList(request.getProofFileIds()
                    ),request.getId());
        }
        return BaseResponse.ok(request.getId());
    }

    @Override
    public void saveProjectOrganization(PreProjectOrgStructResponse pre) {
        //删除组织机构
        DepartmentPDto pdto = new DepartmentPDto();
        pdto.setpId(pre.getPId());
        BaseResponse departmentPTreeList = userProducerFeign.getDepartment_PTreeList(pdto);
        if(ValidateUtil.isNotNull(departmentPTreeList.getData())){
            List<String> keys = extractAllKeys(departmentPTreeList);
            //清空， 重新赋值,删除机构
            for (String key : keys) {
                pdto = new DepartmentPDto();
                pdto.setPdId(key);
                pdto.setType("delete");
                userProducerFeign.CUDDepartment_P(pdto);
            }
        }
        //权限
        BaseResponse quanxian = userProducerFeign.GetRoleCheckBox(null, null, null);
        List<Map<String,Object>> dataList = (List<Map<String,Object>>)quanxian.getData();
        //生成项目组织机构
        DepartmentPDto dto = new DepartmentPDto();
        dto.setPdName("五方责任主体");
        dto.setpId(pre.getPId());
        dto.setPdParentid("0"); //层级
        dto.setPdStatus("1"); //状态
        dto.setType("create");
        dto.setPdCreateuName(pre.getCreateUname());
        dto.setPdCreateuID(pre.getCreateUid());
        dto.setPdCode("WFZRZT");//五方责任主体
        dto.setPdId(GenerateUUID.getUUID());
        userProducerFeign.CUDDepartment_P(dto);
        //单位id
        Map<String,Object> map  = new HashMap<>();
        //生成单位
        List<String> list  = new ArrayList<>(Arrays.asList("项目管理单位","勘察单位","设计单位","施工单位","监理单位"));
        for (String s : list) {
            DepartmentPDto dtoCj = new DepartmentPDto();
            dtoCj.setPdName(s);
            dtoCj.setpId(pre.getPId());
            dtoCj.setPdParentid(dto.getPdId()); //层级
            dtoCj.setPdStatus("1"); //状态
            dtoCj.setType("create");
            dtoCj.setPdCreateuName(pre.getCreateUname());
            dtoCj.setPdCreateuID(pre.getCreateUid());
            dtoCj.setPdCode(s);
            dtoCj.setPdId(GenerateUUID.getUUID());
            map.put(s,dtoCj.getPdId());
            userProducerFeign.CUDDepartment_P(dtoCj);
        }
        // 用于存储用户和他们对应的职位ID
        Map<String, String> userPositionIds = new HashMap<>();

        // 用于存储所有单位的职位名称和职位ID映射
        Map<String, String> allCreatedPositions = new HashMap<>();

        List<PreProjectOrgStructUnitResponse> collect = pre.getPreProjectOrgStructUnitList().stream().filter(e -> e.getUnitType().equals("02")).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(collect)){
            // 生成岗位
            for (PreProjectOrgStructUnitResponse preUser : collect) {
                // 根据单位类型获取ID
                String id = "";
                switch (preUser.getOrgStructType()) {
                    case "01":
                        id = String.valueOf(map.get("项目管理单位"));
                        break;
                    case "02":
                        id = String.valueOf(map.get("勘察单位"));
                        break;
                    case "03":
                        id = String.valueOf(map.get("设计单位"));
                        break;
                    case "04":
                        id = String.valueOf(map.get("施工单位"));
                        break;
                    case "05":
                        id = String.valueOf(map.get("监理单位"));
                        break;
                }

                // 为每个单位创建一个新的Map来跟踪已创建的职位
                Map<String, String> createdPositionsForUnit = new HashMap<>();
                // 生成岗位
                if(!CollectionUtils.isEmpty(preUser.getPreProjectOrgStructUserList())){
                    for (PreProjectOrgStructUserResponse re : preUser.getPreProjectOrgStructUserList()) {
                        // 检查是否已经存在相同职位名称（针对当前单位）
                        if (!createdPositionsForUnit.containsKey(re.getOrgPost())) {
                            PositionPDto postDto = new PositionPDto();
                            postDto.setPpCreatetime(new Date());
                            postDto.setPpCreateuId(pre.getCreateUid());
                            postDto.setPpCreateuName(pre.getCreateUname());
                            postDto.setPpName(re.getOrgPost());
                            postDto.setPpStatus("1");
                            //赋值权限
                            Optional<String> foundValue = dataList.stream()
                                    .filter(entry -> re.getOrgPost().equals(entry.get("label")))
                                    .map(entry -> (String) entry.get("value"))
                                    .findFirst();
                            postDto.setPpCode(re.getOrgPost());
                            postDto.setRoleIds(String.valueOf(foundValue.orElse("")));
                            postDto.setPdId(String.valueOf(id));
                            BaseResponse baseResponse = userProducerFeign.CreateOrUpdatePosition_P(postDto);
                            String positionId = (String) baseResponse.getData();
                            createdPositionsForUnit.put(re.getOrgPost(), positionId);
                            userPositionIds.put(re.getUserId(), positionId);
                            allCreatedPositions.put(re.getOrgPost(), positionId);
                        } else {
                            String existingPositionId = createdPositionsForUnit.get(re.getOrgPost());
                            userPositionIds.put(re.getUserId(), existingPositionId);
                        }
                    }
                    for (PreProjectOrgStructUserResponse res : preUser.getPreProjectOrgStructUserList()) {
                        PositionUserPDto ppdto  = new PositionUserPDto();
                        ppdto.setType("create");
                        ppdto.setuId(res.getUserId());
                        String ppId = allCreatedPositions.get(res.getOrgPost());
                        ppdto.setPpId(ppId);
                        ppdto.setPupId(GenerateUUID.getUUID());
                        userProducerFeign.CreateOrDeletePP_U(ppdto);
                    }
                }

            }
        }
    }


    public static List<String> extractAllKeys(BaseResponse response) {
        List<String> allKeys = new ArrayList<>();
        JSONArray rootArray = JSONUtil.parseArray(response.getData());
        List<String> outerKeys = new ArrayList<>();
        if (CollUtil.isNotEmpty(rootArray)) {
            for (Object itemObj : rootArray) {
                JSONObject item = (JSONObject) itemObj;

                extractKeys(item.getJSONArray("children"), allKeys);

                // 最外层的 key
                String outerKey = item.getStr("key");
                if (outerKey != null && !outerKey.isEmpty()) {
                    outerKeys.add(outerKey);
                }
            }
        }

        // 最外层的 key 添加到末尾
        allKeys.addAll(outerKeys);

        return allKeys;
    }

    private static void extractKeys(JSONArray children, List<String> allKeys) {
        if (CollUtil.isNotEmpty(children)) {
            for (Object childObj : children) {
                JSONObject child = (JSONObject) childObj;
                String key = child.getStr("key");
                if (key != null && !key.isEmpty()) {
                    allKeys.add(key);
                }
                extractKeys(child.getJSONArray("children"), allKeys);
            }
        }
    }
}
