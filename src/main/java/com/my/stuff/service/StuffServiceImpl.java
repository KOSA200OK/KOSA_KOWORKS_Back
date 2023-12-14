package com.my.stuff.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.stuff.dto.StuffDTO;
import com.my.stuff.dto.StuffReqDTO;
import com.my.stuff.entity.StuffEntity;
import com.my.stuff.entity.StuffReqEntity;
import com.my.stuff.repository.StuffRepository;
import com.my.stuff.util.StuffMapper;


@Service
public class StuffServiceImpl implements StuffService {
	@Autowired
	private StuffRepository stuffRepository;
	
	@Override
	public List<StuffDTO> findAll() throws FindException{
		List<StuffEntity> stuffEntityList = stuffRepository.findAll();
		List<StuffDTO> stuffDTOList = new ArrayList<>();
		for (StuffEntity stuffEntity : stuffEntityList) {
			StuffDTO stuffDTO = StuffMapper.entityToDto(stuffEntity);
			stuffDTOList.add(stuffDTO);
		}
		return stuffDTOList;
	}
	
	@Override
	public void modifyStock(StuffReqDTO dto) throws ModifyException{
		Optional<StuffEntity> optS = stuffRepository.findById(dto.getStuff().getId());
		StuffEntity se = optS.get();
		Long origStock = se.getStock();
		Long quantity = dto.getQuantity();
		Long newStock = (origStock - quantity);
		se.modifyStock(newStock);
		stuffRepository.save(se);
	}
}
