package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionPMapper;
import com.jpmsoft.userProducer.pojo.dto.DepartmentPCjConfDTO;
import com.jpmsoft.userProducer.pojo.dto.PositionPDto;
import com.jpmsoft.userProducer.pojo.entity.SysPositionP;
import com.jpmsoft.userProducer.pojo.vo.SysPositionPVo;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysPositionPRoleService;
import com.jpmsoft.userProducer.service.ISysPositionPService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysPositionPImpl extends ServiceImpl<SysPositionPMapper, SysPositionP> implements ISysPositionPService {

    @Resource
    private ISysPositionPRoleService sysPositionPRoleService;
    @Resource
    private ISysConfigEntityService configEntityService;

    @Override
    public BaseResponse getProjectPositionP(String pId, String jtOrGl) {
        DepartmentPCjConfDTO conf = configEntityService.getDepartmentPCjConf();
        if ("jt".equalsIgnoreCase(jtOrGl)) {
            return BaseResponse.ok(baseMapper.getProjectPositionP(pId, Collections.singletonList(conf.getJtCode())));
        } else if ("yzdw".equalsIgnoreCase(jtOrGl)) {
            return BaseResponse.ok(baseMapper.getProjectPositionPOwner(pId, conf.getYzdwoCode(), conf.getYzdwtCode()));
        } else if ("cjdw".equalsIgnoreCase(jtOrGl)) {
            List<String> codes = Arrays.asList(
                    conf.getCjdwCode(),
                    conf.getJsdwCode(),
                    conf.getJldwCode(),
                    conf.getSgdwCode(),
                    conf.getKcdwCode(),
                    conf.getSjdwCode(),
                    conf.getGzsjdwCode()
            );
            return BaseResponse.ok(baseMapper.getProjectPositionP(pId, codes));
        } else {
            return BaseResponse.ok(baseMapper.getProjectPositionP(pId, Collections.singletonList(conf.getGldwCode())));
        }
    }

    @Override
    public List<SysPositionP> selectPositionPByPdId(String pdId) {
        LambdaQueryWrapper<SysPositionP> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPositionP::getPdId, pdId);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 分页查询项目岗位，支持按项目、部门ID和部门编码等条件筛选。
     *
     * @param request 分页及筛选条件，pdCode非空时精确匹配部门编码
     * @return 岗位列表及相同筛选条件下的总数
     */
    @Override
    public BaseResponse GetPosition_PList(PositionPDto request) {
        if (ValidateUtil.isEmpty(request.getPpStatus()))
            request.setPpSort(null);
        if (ValidateUtil.isEmpty(request.getPpName()))
            request.setPpName(null);
        if (ValidateUtil.isEmpty(request.getPdId()))
            request.setPdId(null);
        //获取过滤岗位编码
        String filterSpecifiedPosition = configEntityService.getSysConfigByCode("FilterSpecifiedPosition");

        request.setFilterSpecifiedPosition(filterSpecifiedPosition);
        
        List<SysPositionPVo> models = baseMapper.GetPositionPList((request.getPage() - 1) * request.getSize(), request.getSize()
                , request.getPpStatus(), request.getPdId(), request.getPpName(), request.getSortType(), request.getSortFiled(), filterSpecifiedPosition, request.getpId(), request.getPdCode());
        Integer total = baseMapper.GetPositionPListCount(request.getPpStatus(), request.getPdId(), request.getPpName(), filterSpecifiedPosition, request.getpId(), request.getPdCode());
        return BaseResponse.ok(models, total);
    }

    @Override
    public BaseResponse getPositionPByUid(String uId, String pId) {
        return BaseResponse.ok(baseMapper.getPositionPByUid(uId, pId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdatePosition_P(PositionPDto request) {
        SysPositionP entity;
        if (CheckNullUtil.checkString(request.getPpId()))
            entity = new SysPositionP();
        else {
            entity = baseMapper.selectById(request.getPpId());
            if (CheckNullUtil.checkObject(entity))
                return BaseResponse.fail(BaseMsg.infoNotExist);
        }
        entity.setPdId(request.getPdId());
        entity.setPpCode(request.getPpCode());
        entity.setPpStatus(request.getPpStatus());
        if (!ValidateUtil.isEmpty(request.getPpSort()))
            entity.setPpSort(Integer.parseInt(request.getPpSort()));
        entity.setPpRemark(request.ppRemark);
        entity.setPpName(request.getPpName());
        entity.setPpMdmNo(request.getPpMdmNo());
        if (CheckNullUtil.checkString(request.getPpId())) {
            entity.setPpId(GenerateUUID.getUUID());
            entity.setPpCreateuName(request.getPpCreateuName());
            entity.setPpCreateuId(request.getPpCreateuId());
            entity.setPpCreatetime(new Date());
            baseMapper.insert(entity);
            sysPositionPRoleService.CreatePositionPRole(entity.getPpId(), request.getRoleIds());
        } else {
            sysPositionPRoleService.CreatePositionPRole(entity.getPpId(), request.getRoleIds());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getPpId());
    }

    @Override
    public BaseResponse DeletePosition_P(PositionPDto request) {
        SysPositionP entity = baseMapper.selectById(request.getPpId());
        if (entity != null) {
            baseMapper.deleteById(request.getPpId());
            return BaseResponse.ok();
        }
        return BaseResponse.fail(BaseMsg.infoNotExist);
    }

    @Override
    public BaseResponse GetP_PSelectByDID(PositionPDto request) {
        QueryWrapper<SysPositionP> wrapper = new QueryWrapper<>();
        wrapper.eq("PP_Status", "1");
        if (!ValidateUtil.isEmpty(request.getPdId()))
            wrapper.eq("PD_ID", request.getPdId());
        List<SysPositionP> models = baseMapper.selectList(wrapper);
        List<Map<String, String>> data = new ArrayList<>();
        models.forEach(model -> {
            Map<String, String> map = new HashMap<>();
            map.put("id", model.getPpId());
            map.put("name", model.getPpName());
            data.add(map);
        });

        return BaseResponse.ok(data);
    }

    @Override
    public void insert(SysPositionP sysPositionP) {
        baseMapper.insert(sysPositionP);
    }

    @Override
    public List<SysPositionP> getPositionPByPdId(String pdId) {
        QueryWrapper<SysPositionP> wrapper = new QueryWrapper<>();
        wrapper.eq("PD_ID", pdId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BaseResponse getPositionPByRole(String roleId) {
        return BaseResponse.ok(baseMapper.getPositionPByRole(roleId));
    }
}
