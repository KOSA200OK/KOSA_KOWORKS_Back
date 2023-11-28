package com.my.notice.entity;

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
@Table(name="notice")
@DynamicInsert
@SequenceGenerator(
		name = "NOTICE_SEQ_GENERATOR",
		sequenceName = "notice_seq",
		initialValue=1,
		allocationSize=1
		)
public class NoticeEntity {
	
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "NOTICE_SEQ_GENERATOR"
	)
	private Long id; //공지사항번호
	
	@ManyToOne
	@JoinColumn(name="memberId", nullable=false)
	private MemberEntity member;
	
	@Column
	private String title;
	
	@Column
	private String content;
	
	@Column
	private Date regdate;
	
}
