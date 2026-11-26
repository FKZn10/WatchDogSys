package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.SysTableMapper;
import com.jpmsoft.fileUpload.pojo.dto.jaxbxml.SysTableColumnXmlDTO;
import com.jpmsoft.fileUpload.pojo.dto.jaxbxml.SysTableXmlDTO;
import com.jpmsoft.fileUpload.pojo.dto.jaxbxml.TableRootXmlDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysTable;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import com.jpmsoft.fileUpload.service.IFileUploadService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysTableColumnService;
import com.jpmsoft.fileUpload.service.ISysTableService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/22 11:27
 */
@Service
public class SysTableImpl extends ServiceImpl<SysTableMapper, SysTable> implements ISysTableService {

    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private ISysConfigService sysConfigService;
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Override
    public void exportTableXml(HttpServletResponse response, List<String> tableCodes) {
        String root = sysConfigService.getRootDirectory();
        if (root == null) throw new JPMException(FileMsg.missSysConfig);
        List<SysTableXmlDTO> tables = getTable(tableCodes);
        try {
            // 创建 JAXBContext 实例来处理主表数据
            JAXBContext jaxbContext = JAXBContext.newInstance(TableRootXmlDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 将对象序列化为 XML 并保存到文件
            String temFileDir = root + "/" + dirPath + "/JPBPMTemporaryFolder/" + GenerateUUID.getUUID() + ".xml";
            File outputFile = new File(temFileDir); // 指定输出文件路径
            TableRootXmlDTO data = new TableRootXmlDTO();
            data.setTables(tables);
            marshaller.marshal(data, outputFile); // 将单个订单对象写入文件
            FileUtil.downFileAndDelFile(response, temFileDir, "sysTableAndColumn.xml");
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
        }
    }

    @Override
    public BaseResponse importTableXml(MultipartFile file) throws JAXBException {
        //临时存储
        File xml = fileUploadService.temporaryStorageXml(file);
        if (!xml.exists() || !xml.isFile()) return new BaseResponse(FileMsg.fileParsingFail);
        JAXBContext jaxbContext = JAXBContext.newInstance(TableRootXmlDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TableRootXmlDTO data = (TableRootXmlDTO) unmarshaller.unmarshal(xml);
        if (data != null) {
            List<SysTableXmlDTO> tables = data.getTables();
            if (!CollectionUtils.isEmpty(tables)) {
                List<String> tableCodes = tables.stream().map(SysTableXmlDTO::getTableCode).collect(Collectors.toList());
                List<SysTable> newTbs = new ArrayList<>();
                List<SysTableColumn> columns = new ArrayList<>();
                for (SysTableXmlDTO table : tables) {
                    SysTable tb = new SysTable();
                    BeanUtils.copyProperties(table, tb);
                    tb.setTableId(GenerateUUID.getUUID());
                    newTbs.add(tb);
                    if (!CollectionUtils.isEmpty(table.getSysTableColumns()))
                        table.getSysTableColumns().forEach(c -> {
                            SysTableColumn co = new SysTableColumn();
                            BeanUtils.copyProperties(c, co);
                            co.setTableId(tb.getTableId());
                            co.setTableCode(tb.getTableCode());
                            columns.add(co);
                        });
                }
                sysTableColumnService.deleteByTbCodes(tableCodes);
                deleteByTbCodes(tableCodes);
                if (!CollectionUtils.isEmpty(newTbs)) saveBatch(newTbs);
                if (!CollectionUtils.isEmpty(columns)) sysTableColumnService.insertBatchSomeColumn(columns);
            }
        }
        xml.delete();
        return BaseResponse.ok();
    }


    private List<SysTableXmlDTO> getTable(List<String> tableCodes) {
        QueryWrapper<SysTable> wrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(tableCodes))
            wrapper.in("Table_Code", tableCodes);
        List<SysTable> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<SysTableColumnXmlDTO> columns = sysTableColumnService.getColumns(CollectionUtils.isEmpty(tableCodes) ? null : data.stream().map(SysTable::getTableId).collect(Collectors.toList()));
            List<SysTableXmlDTO> res = new ArrayList<>();
            data.forEach(d -> {
                SysTableXmlDTO xmlDTO = new SysTableXmlDTO();
                BeanUtils.copyProperties(d, xmlDTO);
                xmlDTO.setSysTableColumns(columns.stream().filter(f -> f.getTableId().equals(xmlDTO.getTableId())).collect(Collectors.toList()));
                res.add(xmlDTO);
            });
            return res;
        } else return null;
    }


    private void deleteByTbCodes(List<String> tbCodes) {
        QueryWrapper<SysTable> wrapper = new QueryWrapper<>();
        wrapper.in("Table_Code", tbCodes);
        baseMapper.delete(wrapper);
    }
}
