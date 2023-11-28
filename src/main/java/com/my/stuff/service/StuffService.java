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


@Service
public class StuffService {
	@Autowired
	private StuffRepository stuffRepository;
	
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
