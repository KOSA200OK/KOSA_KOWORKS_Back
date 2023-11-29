package com.my.meetingroom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class ParticipantsDTO {
	private Long id;
	private Long meetingId;
	private Long memberId;
}