package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class TripleMultiLoop {
    private String dateTime;
    private List<IntegratedWorkOrderDtlDTO> dataList;
    private boolean last;
}
