package com.my.meetingroom.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.my.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ParticipantsDTO {
	private Long id;

	private MeetingReservationDTO meetingId;
	private MemberDTO member;	
}
