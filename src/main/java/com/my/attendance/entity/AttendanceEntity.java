package com.my.attendance.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert

@Entity
@Table(name="attendance")
@SequenceGenerator(name = "attendance_seq_generator",
				   sequenceName = "attendance_seq",
				   initialValue = 1,
				   allocationSize = 1)
public class AttendanceEntity {

	@Id
	@Column(name="attendanceId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "attendance_seq_generator")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "memberId",
				nullable = false)
	private MemberEntity memberEntity;
	
	@Column(name="attendaceDate")
	private LocalTime  attendanceDate;
	
	@Column(name="startTime")
	private LocalTime  startTime;
	
	@Column(name="endTime")
	private LocalTime  endTime;
	
	@Column(name="status")
	private Integer status;
	
} // end class
