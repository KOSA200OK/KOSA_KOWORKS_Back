package com.my.attendance.entity;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "attendance_seq_generator")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "memberId",
				nullable = false)
	private MemberEntity memberId;
	
	@Column(name="attendaceDate")
	private String  attendanceDate;
	
	@Column(name="startTime")
	private String  startTime;
	
	@Column(name="endTime")
	private String  endTime;
	
	@Column(name="status")
	private Integer status;

	public void modifyAttendance(String endTime, Integer status) {
		this.endTime = endTime;
		this.status = status;
	}
	
} // end class
