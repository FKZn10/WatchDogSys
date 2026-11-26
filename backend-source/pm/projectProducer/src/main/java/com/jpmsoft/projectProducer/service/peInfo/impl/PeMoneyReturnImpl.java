package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeMoneyReturnMapper;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeMoneyReturnDTO;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMoneyReturn;
import com.jpmsoft.projectProducer.pojo.vo.peInfo.PeMoneyReturnVO;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeMoneyReturnService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/25 18:45
 */
@Service
public class PeMoneyReturnImpl extends FileImpl<PeMoneyReturnMapper, PeMoneyReturn, PeMoneyReturnDTO> implements IPeMoneyReturnService {
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdatePeMoneyReturn(PeMoneyReturnDTO request) {
        PeMoneyReturn entity = new PeMoneyReturn();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getPeMoneyReturnList(PeMoneyReturnDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Pe_Money_Return", uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "state");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<PeMoneyReturnDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conId")
                .put("conName").put("pCode").put("conCode").put("mrType")
                .put("state").put("mrConstruction").put("createUname");
        IPage<PeMoneyReturnDTO> page = new Page<>(request.getPage(), request.getSize());
        List<PeMoneyReturnDTO> datas = baseMapper.getPeMoneyReturnList(page, params.getQuery());
        PeMoneyReturnVO res = baseMapper.getListAmtSum(params.getQuery());
        if (res == null) res = new PeMoneyReturnVO();
        res.setData(datas);
        return BaseResponse.ok(res, (int) page.getTotal());
    }

    @Override
    public BaseResponse getPeMoneyReturnById(String id) {
        return BaseResponse.ok(baseMapper.getPeMoneyReturnById(id));
    }

    @Override
    public BaseResponse getPeMoneySumByConId(String conId) {
        return BaseResponse.ok(baseMapper.getPeMoneySumByConId(conId));
    }
}
