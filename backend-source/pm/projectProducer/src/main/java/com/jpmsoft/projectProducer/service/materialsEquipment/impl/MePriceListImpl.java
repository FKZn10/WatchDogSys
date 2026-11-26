package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MePriceListMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MePriceListDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MePriceList;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MePriceListVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMePriceListDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMePriceListService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:34
 */
@Service
public class MePriceListImpl extends BaseFileImpl<MePriceListMapper, MePriceList, MePriceListDto> implements IMePriceListService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IMePriceListDtlService mePriceListDtlService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "plCode", method = "setPlCode", codeRule = "MePriceList", beanClass = MePriceListDto.class)
    public BaseResponse createOrUpdateMePriceList(MePriceListDto request) {
        MePriceList mpl = new MePriceList();
        mpl = createOrUpdateEntity(mpl, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), mpl.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), mpl.getId());
        }
        mePriceListDtlService.createOrUpdateMePriceListDtl(request.getMePriceListDtls(), mpl.getId());
        return BaseResponse.ok(mpl.getId());
    }

    @Override
    public BaseResponse getMePriceListList(MePriceListDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Price_List", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MePriceListDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId")
                .put("plPm").put("state").put("plCode")
                .put("createUname").put("conName");
        IPage<MePriceListVo> page = new Page<>(request.getPage(), request.getSize());
        List<MePriceListVo> datas = baseMapper.getMePriceListList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMePriceListById(String id) {
        MePriceListVo data = baseMapper.getMePriceListById(id);
        return BaseResponse.ok(data);
    }
}
