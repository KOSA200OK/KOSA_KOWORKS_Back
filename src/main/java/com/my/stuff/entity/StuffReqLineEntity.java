package com.my.stuff.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stuff_req_line")
public class StuffReqLineEntity {
//	@EmbeddedId
//	private StuffReqLineId stuffReqLineId;
//	
//	@ManyToOne
//	@JoinColumn(name = "reqId")
//	@MapsId("reqId")
//	private int reqId;
//	
//	@Column(name = "reqQuantity")
//	private int reqQuantity;
//	
//	@ManyToOne
//	@JoinColumn(name = "stuffId")
//	@MapsId("stuffId")
//	private int stuffId;
	
	@Id
	@Column(name="stuffReqLineId")
	private Long stuffReqLineId; //비품신청상세번호 -PK
	
	@Column(name="reqLineId")
	private Long reqLineId; //비품신청번호 -FK
	
	@Column
	private Long reqLineStuffId;//비품번호 - FK
	
	@Column(name = "reqLineQuantity")
	private int reqLineQuantity;
}
