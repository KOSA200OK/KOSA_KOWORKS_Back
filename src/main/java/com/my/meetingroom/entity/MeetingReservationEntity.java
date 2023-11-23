package com.my.meetingroom.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder

@Entity
@Table(name="meeting_reservation")
@DynamicInsert
@SequenceGenerator(
		name = "MEETING_SEQ_GENERATOR",
		sequenceName = "meeting_seq",
		initialValue=1,
		allocationSize=1
		)
public class MeetingReservationEntity {

	@Id
	@Column(nullable=false)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "MEETING_SEQ_GENERATOR"
	)
	private Integer meetingId;
	
	@Column(nullable=false)
	private Integer meetingroomId;
	
	@Column(nullable=false)
	private Integer memberId;
	
	private String startTime;
	
	private String endTime;
	
	private Date meetingDate;
	
	private String purpose;
	
}
