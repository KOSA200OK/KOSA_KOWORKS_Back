package com.my.stuff.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class StuffReqDTO {
	private Long id;
	private StuffDTO stuff;
	private MemberDTO member;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Date reqDate;
	private Long quantity;
	private Long status;
	private String purpose;
	private String reject;
}
