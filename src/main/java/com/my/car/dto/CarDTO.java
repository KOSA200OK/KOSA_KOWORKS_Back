package com.my.car.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class CarDTO {
	private String id;
	private String carNo;
	private String carType;
	private BigDecimal latitude;
	private BigDecimal longitude;
}
