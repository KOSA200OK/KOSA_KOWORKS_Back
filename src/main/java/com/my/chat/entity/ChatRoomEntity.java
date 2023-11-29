package com.my.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chatroom")
@SequenceGenerator(name = "CHATROOM_SEQ_GENERATOR", sequenceName = "chatroom_seq", initialValue = 1, allocationSize = 1)
public class ChatRoomEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHATROOM_SEQ_GENERATOR")
	private Long id;
	

	@ManyToOne
	@JoinColumn(name = "memberId")
	private MemberEntity member;
}
