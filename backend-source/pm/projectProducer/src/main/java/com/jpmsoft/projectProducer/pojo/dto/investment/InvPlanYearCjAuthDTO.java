package com.jpmsoft.projectProducer.pojo.dto.investment;

import lombok.Data;

import java.util.List;

@Data
public class InvPlanYearCjAuthDTO {

    private List<String> authRoles;
    private List<String> noAuthRoles;
}
