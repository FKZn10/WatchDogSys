package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.SqlSecurityValidatorUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.mapper.OutlineDatasourceMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.OutlineDatasourceDTO;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasource;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasourceTemp;
import com.jpmsoft.fileUpload.service.IOutlineDatasourceService;
import com.jpmsoft.fileUpload.service.IOutlineDatasourceTempService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OutlineDatasourceServiceImpl extends ServiceImpl<OutlineDatasourceMapper, OutlineDatasource> implements IOutlineDatasourceService {
    @Value("${file.upload.dirPath}")
    private String dirPath;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineDatasourceTempService outlineDatasourceTempService;

    /**
     * 导出大纲数据源配置
     *
     * @param response
     * @param ids
     */
    @Override
    public void exportOutlineDatasourceByIds(HttpServletResponse response, List<String> ids) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) {
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        }
        boolean exists = lambdaQuery().in(OutlineDatasource::getId, ids).exists();
        if (exists) {
            List<OutlineDatasourceDTO> models = baseMapper.getOutlineDatasourceByIds(ids);
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, OutlineDatasourceDTO.class, filePath, "数据源配置");
            // 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "数据源配置.xlsx");
        } else {
            throw new JPMException(FileMsg.notData2Export);
        }
    }

    /**
     * 导入大纲数据源配置
     *
     * @param file
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse importOutlineDatasource(MultipartFile file, String token) {
        List<OutlineDatasourceDTO> data = new EasyExcelData<OutlineDatasourceDTO>().getExcelData(file, OutlineDatasourceDTO.class, 1);
        if (ValidateUtil.isEmpty(data)) {
            return BaseResponse.fail(FileMsg.excelNotData);
        }
        Date now = new Date();
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        Map<String, String> tCodes = new HashMap<>();
        List<OutlineDatasource> entities = data.stream().map(dto -> {
            OutlineDatasource model = new OutlineDatasource();
            BeanUtils.copyProperties(dto, model);
            if (ValidateUtil.isEmpty(model.getId())) {
                model.setId(GenerateUUID.getUUID());
                model.setCreateUid(user.getId());
                model.setCreateUname(user.getName());
                model.setCreateTime(now);
            } else {
                model.setUpdateTime(now);
            }
            tCodes.put(model.getId(), dto.getTCodes());
            return model;
        }).collect(Collectors.toList());
        if (ValidateUtil.isNotEmpty(entities)) {
            // 别名唯一判断
            if (datasourceAliasExist(entities)) {
                return BaseResponse.fail("数据源别名存在重复，请修改后重试");
            }
            // SQL注入校验
            if (!sqlDatasourceCalibration(entities)) {
                return BaseResponse.fail("SQL语句存在注入风险，请修改后重试");
            }
            saveOrUpdateBatch(entities);
        }
        // 处理文档模板
        List<OutlineDatasourceTemp> tempList = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (OutlineDatasource entity : entities) {
            ids.add(entity.getId());
            String codes = tCodes.get(entity.getId());
            if (ValidateUtil.isEmpty(codes)) {
                continue;
            }
            Arrays.stream(codes.split(","))
                    .filter(ValidateUtil::isNotEmpty)
                    .forEach(code -> {
                        OutlineDatasourceTemp temp = new OutlineDatasourceTemp();
                        temp.setId(GenerateUUID.getUUID());
                        temp.setOdId(entity.getId());
                        temp.setTCode(code);
                        tempList.add(temp);
                    });
        }
        if (ValidateUtil.isNotEmpty(tempList)) {
            outlineDatasourceTempService.insertBatch(ids, tempList);
        }
        return BaseResponse.ok();
    }

    /**
     * 别名唯一判断
     *
     * @param alias
     * @param id
     * @return
     */
    private Boolean datasourceAliasExist(String alias, String id) {
        return lambdaQuery()
                .eq(OutlineDatasource::getOdAlias, alias)
                .ne(ValidateUtil.isNotEmpty(id), OutlineDatasource::getId, id)
                .exists();
    }

    /**
     * 别名唯一判断
     *
     * @param list
     * @return
     */
    private Boolean datasourceAliasExist(List<OutlineDatasource> list) {
        Set<String> aliasSet = new HashSet<>();
        for (OutlineDatasource item : list) {
            if (!aliasSet.add(item.getOdAlias())) {
                return true;
            }
        }
        return false;
    }

    /**
     * SQL语句校验
     *
     * @param list
     * @return
     */
    private Boolean sqlDatasourceCalibration(List<OutlineDatasource> list) {
        for (OutlineDatasource item : list) {
            if ("01".equals(item.getOdType()) && ValidateUtil.isNotEmpty(item.getOdSource())) {
                String validated = SqlSecurityValidatorUtil.validateSelectOnly(item.getOdSource());
                if (!"OK".equalsIgnoreCase(validated)) {
                    log.info("SQL语句校验未通过，数据源名：{}，原因：{}", item.getOdName(), validated);
                    return false;
                }
            }
        }
        return true;
    }
}
