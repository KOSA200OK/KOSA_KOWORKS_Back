package com.my.meetingroom.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.my.member.entity.MemberEntity;

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
		initialValue = 1,
		allocationSize = 1
		)
public class MeetingReservationEntity {

	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "MEETING_SEQ_GENERATOR"
	)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="meetingroomId", nullable=false)
	private MeetingroomDetailEntity meetingroom;
	
	@ManyToOne
	@JoinColumn(name="memberId", nullable=false)
	private MemberEntity member;
	
	private String startTime;
	
	private String endTime;
	
	private String meetingDate;
	
	private String purpose;

	//양방향 설정
	@OneToMany
	(
			fetch = FetchType.LAZY
			,
			orphanRemoval = true
			,
			cascade = {CascadeType.ALL, CascadeType.REMOVE}
//			,
//			cascade = CascadeType.REMOVE //단방향일 때는 remove만
//			cascade = {CascadeType.REMOVE, CascadeType.PERSIST}
			,
			mappedBy="meeting" //양방향
	)
//	@JoinColumn(name="meetingId") //단방향
	private List<ParticipantsEntity> participants; //회의 참여자 목록
	
}
