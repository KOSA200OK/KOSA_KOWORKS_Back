package com.my.meetingroom.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="meetingroom_detail")
@DynamicInsert
@SequenceGenerator(
		name = "MEETINGROOM_SEQ_GENERATOR",
		sequenceName = "meetingroom_seq",
		initialValue = 1,
		allocationSize = 1
		)
public class MeetingroomDetailEntity {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "MEETINGROOM_SEQ_GENERATOR"
	)
	private Long meetingroomId;
	
	private Long maxNum;
	
	private Long monitor;
	
	private Long socket;
	
	private Long projector;
	
	private Long marker;
	
}