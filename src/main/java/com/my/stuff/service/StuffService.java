package com.my.stuff.service;


import java.util.List;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;


public interface StuffService {

	/**
	 * 비품 테이블의 행 전체를 조회하여 반환한다.
	 * @return List<StuffDTO>
	 * @throws FindException
	 */
	public List<StuffDTO> findAll() throws FindException;
	
	/**
	 * 비품 테이블의 수량을 수정한다.
	 * @param dto
	 * @throws ModifyException
	 */
	public void modifyStock(StuffReqDTO dto) throws ModifyException;
}
