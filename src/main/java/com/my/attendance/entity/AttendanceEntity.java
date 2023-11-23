package com.my.attendance.entity;

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
	private Integer attendanceId;
	
//	@ManyToOne
//	@Column(name="MemberId")
//	private Integer MemberId;
	
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name="memberId")
//    private Member memberId;
	
	@Column(name="attendaceDate")
	private Date attendanceDate;
	
	@Column(name="startTime")
	private Date startTime;
	
	@Column(name="endTime")
	private Date endTime;
	
	@Column(name="status")
	private Integer status;
	
} // end class