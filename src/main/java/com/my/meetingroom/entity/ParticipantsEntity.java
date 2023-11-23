package com.my.meetingroom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="participants")
@DynamicInsert
public class ParticipantsEntity {
	@Column(nullable=false)
	private Integer meetingId;
	
	@Column(nullable=false)
	private Integer memberId;
}
