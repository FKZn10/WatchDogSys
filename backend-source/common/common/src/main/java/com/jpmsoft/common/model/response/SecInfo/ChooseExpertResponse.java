package com.jpmsoft.common.model.response.SecInfo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChooseExpertResponse {
    private Object externalExpertList;
    private Object insideExpertList;

}
