package com.my.stuff.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.my.attendance.entity.AttendanceEntity;
import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@DynamicInsert
@Table(name = "stuff_test")
public class StuffEntity {
	@Id
	@Column(length = 20)
	private String id;

	@Column
	private String name;

	@Column(nullable = false, length = 2)
	@ColumnDefault("0")
	private Long quantity;

}
