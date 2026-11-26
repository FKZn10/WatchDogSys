package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AreaAndValueResponse {
	private String totalCode="0";
	private BigDecimal totalArea=BigDecimal.ZERO;
	private BigDecimal totalValue=BigDecimal.ZERO;

	//一、办公及业务用房屋
	private String workCode="1";
	private BigDecimal workArea=BigDecimal.ZERO;
	private BigDecimal workValue=BigDecimal.ZERO;

	//党政机关用房
	private String govCode="1-11";
	private BigDecimal govArea=BigDecimal.ZERO;
	private BigDecimal govValue=BigDecimal.ZERO;

	//事业单位用房
	private String instCode="1-12";
	private BigDecimal instArea=BigDecimal.ZERO;
	private BigDecimal instValue=BigDecimal.ZERO;

	//社会团体用房
	private String socCode="1-13";
	private BigDecimal socArea=BigDecimal.ZERO;
	private BigDecimal socValue=BigDecimal.ZERO;

	//其他相关设施
	private String otherCode="1-14";
	private BigDecimal otherArea=BigDecimal.ZERO;
	private BigDecimal otherValue=BigDecimal.ZERO;

	//二、科教、文、体、卫、民政等社会事业工程
	private String socBusCode="2";
	private BigDecimal socBusArea=BigDecimal.ZERO;
	private BigDecimal socBusValue=BigDecimal.ZERO;

	//科学研究用房屋
	private String sciCode="2-21";
	private BigDecimal sciArea=BigDecimal.ZERO;
	private BigDecimal sciValue=BigDecimal.ZERO;

	//教育用房屋
	private String eduCode="2-22";
	private BigDecimal eduArea=BigDecimal.ZERO;
	private BigDecimal eduValue=BigDecimal.ZERO;

	//文化用房屋
	private String cultureCode="2-23";
	private BigDecimal cultureArea=BigDecimal.ZERO;
	private BigDecimal cultureValue=BigDecimal.ZERO;

	//体育用房屋
	private String sportCode="2-24";
	private BigDecimal sportArea=BigDecimal.ZERO;
	private BigDecimal sportValue=BigDecimal.ZERO;

	//卫生用房屋
	private String healthCode="2-25";
	private BigDecimal healthArea=BigDecimal.ZERO;
	private BigDecimal healthValue=BigDecimal.ZERO;

	//民政用房屋
	private String socialCode="2-26";
	private BigDecimal socialArea=BigDecimal.ZERO;
	private BigDecimal socialValue=BigDecimal.ZERO;

	//三、公共基础设施工程
	private String pubCode="3-30";
	private BigDecimal pubArea=BigDecimal.ZERO;
	private BigDecimal pubValue=BigDecimal.ZERO;

	//四、政府指定实行集中建设的其他工程
	private String otherSpecCode="4-40";
	private BigDecimal otherSpecArea=BigDecimal.ZERO;
	private BigDecimal otherSpecValue=BigDecimal.ZERO;
}
