package com.my.meetingroom.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class MeetingReservationRequest {
	private MeetingReservationDTO meetingReservationDTO;
	private List<String> participantsMemberId;
}
