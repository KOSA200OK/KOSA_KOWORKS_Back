package com.my.stuff.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class StuffReqDTO {
	private Long Id;
	private StuffDTO stuffId;
	private  memberId;
	private Date reqDate;
	private Long quantityReq;
	private Long status;
	private String purpose;
	private String reject;
}
