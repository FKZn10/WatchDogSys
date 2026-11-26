package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsManageSystemMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsManageSystemDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsManageSystem;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsNodePerson;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsManageSystemService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QsManageSystemImpl  extends FileImpl<QsManageSystemMapper, QsManageSystem, QsManageSystemDto> implements IQsManageSystemService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdate(QsManageSystemDto request) {
        QsManageSystem entity = new QsManageSystem();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getQsManageSystemList(QsManageSystemDto request, String uid) {
        String authSql = sysUserService.getAuthSql(null, uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<QsManageSystemDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pCode").put("state").put("createUname").put("pCompetentDepartment");
        //城建外部用户只能看到各自发起/审批的数据
        Boolean isType = false;
        params.put("isType", isType);
        SysUser user = sysUserService.getById(uid);
        if(user.getUType().equals("2") || user.getUType().equals("02")){
            isType = true;
            params.put("isType", isType);
            params.put("uId",uid);
        }
        IPage<QsManageSystemDto> page = new Page<>(request.getPage(), request.getSize());
        List<QsManageSystemDto> datas = baseMapper.getQsManageSystemList(params.getQuery(),page);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getQsManageSystemById(String id) {
        QsManageSystemDto dto =  baseMapper.getQsManageSystemById(id);
        return BaseResponse.ok(dto);
    }

    @Override
    public BaseResponse getQsNodePersonByMark(String pId) {
        //根据配置项查找岗位备注信息
        String mark=baseMapper.selectConfigByCode("postDes");
        if(mark==null)
            mark="质量安全分支机构负责人";
        //根据pid查找部门id
        String dId=baseMapper.getDIdByPId(pId);

        if(dId!=null&& !dId.isEmpty()){

            //根据岗位备注和did查找岗位
            List<String> positions= baseMapper.getPositionsByMarkAndDId(mark,dId);

            if(positions==null||positions.isEmpty()){
                return BaseResponse.ok("项目无满足条件的岗位","");
            }

            //根据岗位查找用户
            List<QsNodePerson>  users=  baseMapper.getQsNodePersonByPosition(positions);
            return BaseResponse.ok(users);
        }else{
            return BaseResponse.ok("项目无分支机构","");
        }

    }


}
