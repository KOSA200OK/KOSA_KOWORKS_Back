package com.my.car.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.car.entity.CarEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
public class CarRentDTO {
	private Long rentId;
	private Long memberId;
	private String carId;
	
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date reqDate;
	
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date startDate;
	
	@JsonFormat(pattern = "yy/MM/dd", timezone = "Asia/Seoul")
	private Date endDate;
	
	@NotEmpty(message="대여 목적을 입력해야 합니다")
	@Size(max=50, message="최대 50자까지 작성할 수 있습니다")
	private String purpose;
	
	private Long status;
	
	@NotEmpty(message="반려 사유를 입력해야 합니다")
	@Size(max=50, message="최대 50자까지 작성할 수 있습니다")
	private String reject;
	
	private BigDecimal latitude;
	private BigDecimal longitude;
}
