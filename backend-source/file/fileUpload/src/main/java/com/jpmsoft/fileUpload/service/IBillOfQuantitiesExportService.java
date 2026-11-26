package com.jpmsoft.fileUpload.service;

import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesContext;
import com.jpmsoft.fileUpload.pojo.dto.billofquantities.BillOfQuantitiesExportDTO;

import java.util.List;

public interface IBillOfQuantitiesExportService {

    List<BillOfQuantitiesExportDTO> export(BillOfQuantitiesContext billOfQuantitiesContext);

}
