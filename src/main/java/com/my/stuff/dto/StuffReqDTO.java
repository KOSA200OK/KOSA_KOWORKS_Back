package com.my.stuff.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
	
	@NotBlank(message="요청 사유를 입력하세요")
	@Size(max=50, message="최대 50자까지 작성할 수 있습니다")
	private String purpose;
	
	@NotBlank(message="반려 사유를 입력하세요")
	@Size(max=50, message="최대 50자까지 작성할 수 있습니다")
	private String reject;
}
