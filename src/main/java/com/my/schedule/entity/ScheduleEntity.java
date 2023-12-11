package com.my.schedule.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.my.member.entity.MemberEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

@Entity
@DynamicInsert
@Table(name="schedule")
@SequenceGenerator(name="schedule_seq_generator", sequenceName="schedule_seq", initialValue=1, allocationSize=1)
public class ScheduleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq_generator")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="memberId", nullable=false)
	private MemberEntity member;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Column(length=200)
	private String content;
	
	private Date startTime;
	
	private Date endTime;
}
