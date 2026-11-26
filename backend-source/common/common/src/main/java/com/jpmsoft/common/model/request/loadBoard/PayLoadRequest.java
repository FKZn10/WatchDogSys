package com.jpmsoft.common.model.request.loadBoard;

import lombok.Data;

@Data
public class PayLoadRequest extends BaseLoadBoardRequest{
    private static final long serialVersionUID = -6460107722647324052L;
    private String type;
    private String contractType;
    private String contractState;
}