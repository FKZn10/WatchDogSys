package com.jpmsoft.projectProducer.pojo.vo.bim;

import lombok.Data;

import java.io.Serializable;

@Data
public class QsRecordCountVO implements Serializable {

    private static final long serialVersionUID = -1618366521884819206L;

    private String problemDesc;

    private long problemNum;

}
