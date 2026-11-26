package com.jpmsoft.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Data // 自动生成getter/setter/toString，需引入lombok依赖
@NoArgsConstructor // 无参构造方法，Jackson必需
@Root(name = "JpmLicenseDTO", strict = false)
@Namespace(reference = "jpmLicense")
public class JpmLicenseDTO {
    @Element(name = "company")
    private String company;
    @Element(name = "expiredDate")
    private String expiredDate;
    @Element(name = "code")
    private String code;
}
