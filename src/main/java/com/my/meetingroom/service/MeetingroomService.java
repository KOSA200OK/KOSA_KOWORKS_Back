package com.my.meetingroom.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.meetingroom.dto.MeetingReservationDTO;
import com.my.meetingroom.dto.MeetingRoomDTO;
import com.my.meetingroom.dto.ParticipantsDTO;

public interface MeetingroomService {
	
	/**
	 * 회의실과 예약현황 전체 리스트를 조회한다. 해당하는 일자 기준으로 보여준다.
	 * @param String date 일자
	 * @return Meeting객체 리스트
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
//	public List<MeetingReservationDTO> findByMeetingRoomAll(String meetingDate) throws FindException;
	public List<MeetingRoomDTO> findByMeetingRoom(String meetingDate) throws FindException;

	
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
	
	
	/**
	 * 내 회의실 예약내역 리스트를 조회한다.
	 * @param memberId 멤버아이디(사원번호)
	 * @return MeetingReservation 객체
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public Page<MeetingReservationDTO> findAllByMemberId(Pageable pageable, String memberId) throws FindException;
	
	/**
	 * 내 회의실 내역에서 회의 참여자를 추가한다.
	 * @param pdto Participants객체
	 * @throws AddException DB에 저장하지 못한 경우 AddException이 발생한다
	 */
	public void createParticipants(ParticipantsDTO pdto) throws AddException;
	
	/**
	 * 내 회의실 내역에서 회의 참여자를 제거한다.
	 * @param id Participants id
	 * @throws RemoveException
	 */
	public void removeParticipants(Long id) throws RemoveException;
	
	/**
	 * 내 회의실 내역에서 회의를 취소한다.
	 * @param id Meeting id
	 * @throws RemoveException
	 */
	public void removeMeeting(Long id) throws RemoveException;
	
}
