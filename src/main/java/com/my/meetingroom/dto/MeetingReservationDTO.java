package com.my.meetingroom.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.my.member.dto.MemberDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class MeetingReservationDTO {
	private Long id;
//	private Long memberId;
//	private Long meetingroomId;
	@NotNull(message="시작 시간을 입력해주세요")
	private String startTime;
	@NotNull(message="종료 시간을 입력해주세요")
	private String endTime;
	private String meetingDate;
	@NotNull(message="사용 목적을 입력해주세요")
	@Size(max=100, message="내용은 최대 100자 이내로 작성해주세요")
	private String purpose;
	
	private MemberDTO member;
	private MeetingRoomDTO meetingroom;
	private ParticipantsDTO participants;
	
}
