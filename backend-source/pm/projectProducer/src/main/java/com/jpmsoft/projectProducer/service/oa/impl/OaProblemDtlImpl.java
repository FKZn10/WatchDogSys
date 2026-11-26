package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemDtlModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaProblemDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemDtl;
import com.jpmsoft.projectProducer.service.oa.IOaProblemDtlService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目问题详情（清单）
 */
@Service
public class OaProblemDtlImpl extends ServiceImpl<OaProblemDtlMapper, OaProblemDtl> implements IOaProblemDtlService {


    @Resource
    private ISysUserService sysUserService;

    @Override
    public void createOrUpdateOaProblemDtl(List<OaProblemDtlModel> datas, String pmId) {
        deleteOaProblemDtlByPmId(pmId);
        if (datas != null && datas.size() > 0) {
            List<OaProblemDtl> addEntitys = new ArrayList<>();
            datas.forEach(data -> {
                OaProblemDtl entity = new OaProblemDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setPmId(pmId);
                addEntitys.add(entity);
            });
            if (addEntitys.size() > 0)
                saveBatch(addEntitys);
        }
    }

    @Override
    public void deleteOaProblemDtlByPmId(String pmId) {
        QueryWrapper<OaProblemDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pm_Id", pmId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<OaProblemDtl> getOaProblemDtlByPmId(String pmId) {
        QueryWrapper<OaProblemDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pm_Id", pmId);
        List<OaProblemDtl> oldDatas = baseMapper.selectList(wrapper);
        return oldDatas;
    }

    @Override
    public BaseResponse getOaProblemDtlList(OaProblemDtlModel request, String uId) {
        String authSql = sysUserService.getAuthSql("OA_Problem", uId, "Pm_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<OaProblemDtlModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("secName").put("pName").put("conName")
                .put("pmTitle").put("pmCode").put("pmState").put("pmdTitle").put("pId")
                .put("pmdType").put("pmdStatus").put("pmdCode").put("pmdDutyUname");
        IPage<OaProblemDtlModel> page = new Page<>(request.getPage(), request.getSize());
        List<OaProblemDtlModel> datas = baseMapper.getOaProblemDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public void refreshProblemDtlStatus(String pmdId, int pmdStatus) {
        baseMapper.refreshProblemDtlStatus(pmdId,pmdStatus);
    }

}
