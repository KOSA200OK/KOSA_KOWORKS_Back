package com.my.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
@SequenceGenerator(name = "MESSAGE_SEQ_GENERATOR", sequenceName = "message_seq", initialValue = 1, allocationSize = 1)
public class MessageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_SEQ_GENERATOR")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "roomId")
	private ChatRoomEntity chatroom;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private MemberEntity member;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;

	private String content;
}
