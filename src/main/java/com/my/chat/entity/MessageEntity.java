package com.my.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "roomId")
	private ChatRoomEntity chatroom;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private MemberEntity member;

	@Column(nullable = false)
	private Date sendTime;
}
