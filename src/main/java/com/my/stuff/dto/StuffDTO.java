package com.my.stuff.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class StuffDTO {
	private String Id;
	private int stuffId;
	private int memberId;
	private Date reqDate;
	private int quantityReq;
	private Integer status;
	private String purpose;
	private String reject;
}
