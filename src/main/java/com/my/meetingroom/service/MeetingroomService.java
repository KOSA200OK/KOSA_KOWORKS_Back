package com.my.meetingroom.service;

import java.util.List;
import java.util.Optional;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.ParticipantsDTO;
import com.my.meetingroom.entity.MeetingReservationEntity;

public interface MeetingroomService {
	
	/**
	 * 회의실과 예약현황 전체 리스트를 조회한다. 해당하는 일자 기준으로 보여준다.
	 * @param String date 일자
	 * @return Meeting객체 리스트
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public List<MeetingReservationDTO> findAllMeetingRoom(String date) throws FindException;
	
	/**
	 * 회의실 예약 번호로 예약 현황을 조회한다.
	 * @param id MeetingReservation아이디
	 * @return MeetingReservation 객체
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public Optional<MeetingReservationDTO> findById(Long id) throws FindException;
	
	/**
	 * 회의실 예약내역을 저장한다.
	 * @param msdto MeetingReservation객체
	 * @throws AddException DB에 저장하지 못한 경우 AddException이 발생한다
	 */
	public void createMeetingReservation(MeetingReservationDTO msdto) throws AddException;
//	public void createMeetingReservation(MeetingReservationDTO meetingReservationDTO, List<String> participantsMemberId) throws AddException;
}
