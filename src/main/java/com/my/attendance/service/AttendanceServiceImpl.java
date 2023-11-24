package com.my.attendance.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

import com.my.attendance.dao.AttendanceRepository;
import com.my.attendance.dto.AttendanceDTO;
import com.my.attendance.entity.AttendanceEntity;
import com.my.exception.FindException;

public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceRepository repository;

	@Override
	public List<AttendanceEntity> findAll() throws FindException {
		
		return repository.findAll();
		
	} // findAll

	@Override
	public AttendanceDTO findByMemberId(int memberId) throws FindException {
		
		Optional<AttendanceEntity> att = repository.findById(memberId);
		
		return VoToDTO(att);
		
	} // findByMemberId
	
	
	// =================  VoToDTO  ======================
	
	private AttendanceDTO VoToDTO(Optional<AttendanceEntity> att) {
		
		ModelMapper mapper = new ModelMapper();
		
		mapper.getConfiguration()
			  .setMatchingStrategy(MatchingStrategies.STANDARD)
			  .setFieldAccessLevel(AccessLevel.PRIVATE)
			  .setFieldMatchingEnabled(true);
		
		Object source = att;
		Class<AttendanceDTO> destinationType = AttendanceDTO.class;
		
		AttendanceDTO dto = mapper.map(source, destinationType);
		dto.setMemberId(dto.getMemberId());
		
		return dto;

	} // VoToDTO

	
	
} // end class
