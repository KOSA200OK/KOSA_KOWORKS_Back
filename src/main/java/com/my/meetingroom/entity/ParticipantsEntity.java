package com.my.meetingroom.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name="participants")
@SequenceGenerator(
		name = "PARTICIPANTS_SEQ_GENERATOR",
		sequenceName = "participants_seq",
		initialValue = 1,
		allocationSize = 1
		)
//@Embeddable implements Serializable
public class ParticipantsEntity {
	
//	@EmbeddedId
//	private ParticipantEmbedded id = new ParticipantEmbedded();
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "PARTICIPANTS_SEQ_GENERATOR"
	)
	private Long participantsId;
	
	@ManyToOne
	@JoinColumn(name="meetingId", nullable=false)
	@MapsId("meetingId")
	private MeetingReservationEntity meeting;
	
	@ManyToOne
	@JoinColumn(name="memberId", nullable=false)
	@MapsId("memberId")
	private MemberEntity member;
	
}
