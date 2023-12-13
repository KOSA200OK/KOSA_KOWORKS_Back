package com.my.schedule.entity;

import java.sql.Date;
import java.sql.Timestamp;

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
	private String scheduleTitle;
	
	@Column(length=200)
	private String content;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	public void modifyScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}
	
	public void modifyContent(String content) {
		this.content = content;
	}
	
	public void modifyStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public void modifyEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
}
