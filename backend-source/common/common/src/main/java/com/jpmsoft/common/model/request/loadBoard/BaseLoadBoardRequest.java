package com.jpmsoft.common.model.request.loadBoard;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BaseLoadBoardRequest extends BasePageEntity implements Serializable {
    private static final long serialVersionUID = 580023771641130128L;
    private Integer year;
    private Integer month;
    private String projectName;
    private String contractName;
    private List<Integer> yearRange;
    private List<Integer> yearRange2;
    private List<Integer> monthYearRange;
    private Integer isStateFinished;
    private Integer contractYear;
    private Integer invoiceInformationYear;
    private Integer receiptYear;
    private Date contractDateStart;
    private Date contractDateEnd;
    private Date invoiceInformationDateStart;
    private Date invoiceInformationDateEnd;
    private Date receiptDateStart;
    private Date receiptDateEnd;
    private String deptId;
    private String sortFiled;
    private String sortType;
    private String listType;
}