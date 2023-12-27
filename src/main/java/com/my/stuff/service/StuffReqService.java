package com.my.stuff.service;


import java.util.Date;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.stuff.dto.StuffReqDTO;

public interface StuffReqService {
	
	/**
	 * StuffReqDTO타입의 비품 요청 데이터를 DB에 추가한다
	 * 
	 * @param StuffReqDTO dto
	 * @throws AddException
	 */
	public void createStuffReq(StuffReqDTO dto) throws AddException;
	
    /**
     * 컨트롤 레이어에서 인계받은 멤버변수의 경우에 따라 변수를 가공한다.
     * 변수의 경우에 따라 레퍼지토리에서 적절한 메서드를 호출한다.
     * 비품요청목록을 반환한다.
     * @param memberId
     * @param status
     * @param stuffId
     * @param startDate
     * @param endDate
     * @return List<StuffReqDTO>
     * @throws FindException
     */
	public List<StuffReqDTO> findByCase(String memberId, Long status, String stuffId, Date startDate, Date endDate)
			throws FindException;

	/**
	 * 인계된 id에 해당하는 비품요청 행을 삭제한다.
	 * 
	 * @param id
	 */
	public void removeById(Long id) throws RemoveException;
	
	/**
	 * 비품요청테이블에서 해당 멤버의 승인 대기중인 요청을 찾아 갯수를 반환한다
	 * @return
	 * @throws FindException
	 */
	public Long findWaitProccessCnt(String memberId) throws FindException;
	
	//===================================관리자용==========================================
	
	/**
     * 컨트롤 레이어에서 인계받은 멤버변수의 경우에 따라 변수를 가공한다.
     * 변수의 경우에 따라 레퍼지토리에서 적절한 메서드를 호출한다.
     * 비품요청목록을 반환한다.
	 * @param departmentId
	 * @param status
	 * @param stuffId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws FindException
	 */
	public List<StuffReqDTO> findByManageCase(Long departmentId, Long status, String stuffId, Date startDate, Date endDate)
			throws FindException;
	
	/**
	 * 비품요청 상세내역을 반환한다
	 * @param id
	 * @return
	 */
	public StuffReqDTO findById(Long id) throws FindException;
	
	/**
	 * 비품요청 행의 요청상태를 승인상태로 수정한다
	 * @param id
	 * @param dto
	 * @throws ModifyException
	 */
	public void modifyReqApprove(StuffReqDTO dto) throws ModifyException;
	
	/**
	 * 비품요청 행의 요청상태를 반려상태로 수정하고 반려사유를 수정한다.
	 * @param id
	 * @param dto
	 * @throws ModifyException
	 */
	public void modifyReqReject(StuffReqDTO dto) throws ModifyException;
	
	/**
	 * 비품요청 테이블에서 승인 대기중인 행의 갯수를 반환한다 
	 * @return Long
	 * @throws FindException
	 */
	public Long findUnprocessedReqCnt() throws FindException;
}
