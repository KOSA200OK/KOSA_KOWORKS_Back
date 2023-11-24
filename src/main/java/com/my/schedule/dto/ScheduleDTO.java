package com.my.schedule.dto;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.car.dto.CarRentDTO;
import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class ScheduleDTO {
	private Long scheduleId;
	private Long memberId;
	
	@NotEmpty(message="일정 제목을 입력해야 합니다")
	@Size(max=20, message="최대 20자까지 작성할 수 있습니다")
	private String title;
	
	@Size(max=50, message="최대 50자까지 작성할 수 있습니다")
	private String content;
	
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date startDate;
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date endDate;
}