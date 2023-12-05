package com.my.notice.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.my.exception.FindException;
import com.my.notice.dto.NoticeDTO;

public interface NoticeService {
	
	/**
	 * 공지사항 전체 리스트를 페이징처리하여 조회한다. 날짜 최신순으로 조회.
	 * @param pageable 페이징처리
	 * @return Notice객체 Page타입 변환
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public Page<NoticeDTO> findAll(Pageable pageable) throws FindException;
	
	/**
	 * 공지사항 항목 1개를 상세 조회한다.
	 * @param id 공지사항 번호
	 * @return Notice객체
	 * @throws FindException DB 연결을 실패하거나 조회되는 공지사항이 없는 경우 FindException이 발생한다
	 */
	public Optional<NoticeDTO> findById(Long id) throws FindException;
	
}
