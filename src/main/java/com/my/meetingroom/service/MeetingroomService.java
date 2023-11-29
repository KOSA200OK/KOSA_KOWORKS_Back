package com.my.meetingroom.service;

import java.util.Date;
import java.util.List;

import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;

public interface MeetingroomService {
	
	/**
	 * 회의실과 예약현황 전체 리스트를 조회한다. 해당하는 일자 기준으로 보여준다.
	 * @param String date 일자
	 * @return Meeting객체 리스트
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public List<MeetingReservationDTO> findAllMeetingRoom(String date) throws FindException;
}
