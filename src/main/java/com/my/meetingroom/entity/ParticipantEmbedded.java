package com.my.meetingroom.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ParticipantEmbedded implements Serializable{
	@Column(name="meetingId", nullable=false)
	private Long meetingId;
	
	@Column(name="memberId", nullable=false)
	private Long memberId;
}
