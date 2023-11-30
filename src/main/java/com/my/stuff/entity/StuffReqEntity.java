package com.my.stuff.entity;


import java.sql.Date;

import javax.persistence.CascadeType;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Builder
@Entity
@DynamicInsert
@Table(name = "stuff_req")
@SequenceGenerator(name="stuff_req_seq_generator", sequenceName="stuff_req_seq", initialValue=1, allocationSize=1)
public class StuffReqEntity {
	@Id
	@Column(length = 20)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stuff_req_seq_generator")
	private Long id;
    
	@ManyToOne	
	@JoinColumn(name = "stuffId")
	private StuffEntity stuff;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	private MemberEntity member;
	
	@Column(nullable=false)
	@ColumnDefault(value = " SYSDATE")
	private Date reqDate;
	
	@Column(nullable = false, length = 2)
	@ColumnDefault("0")
	private Long quantity;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private Long status;
	
	@Column(length=200)
	private String purpose;
	
	@Column(length=200)
	private String reject;
	
//	public void modifyReject(String reject) {
//		this.reject = reject;
//	}
	
	

}
