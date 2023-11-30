package com.my.stuff.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.FindException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.repository.StuffRepository;
import com.my.stuff.util.StuffMapper;


@Service
public class StuffService {
	@Autowired
	private StuffRepository stuffRepository;
	
	/**
	 * 비품 테이블의 행 전체를 조회하여 반환한다
	 * @return List<StuffDTO>
	 * @throws FindException
	 */
	public List<StuffDTO> findAll() throws FindException{
		List<StuffEntity> stuffEntityList = stuffRepository.findAll();
		List<StuffDTO> stuffDTOList = new ArrayList<>();
		for (StuffEntity stuffEntity : stuffEntityList) {
			StuffDTO stuffDTO = StuffMapper.entityToDto(stuffEntity);
			stuffDTOList.add(stuffDTO);
		}
		return stuffDTOList;
	}
}
