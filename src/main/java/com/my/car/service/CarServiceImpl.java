package com.my.car.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.car.dto.CarDTO;
import com.my.car.dto.CarRentDTO;
import com.my.car.entity.CarEntity;
import com.my.car.entity.CarRentEntity;
import com.my.car.repository.CarRentRepository;
import com.my.car.repository.CarRepository;
import com.my.member.entity.MemberEntity;
import com.my.notification.entity.NotificationEntity;
import com.my.notification.service.NotificationServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
	private CarRepository cr;
	private CarRentRepository crr;
	
	// 찬석
	private NotificationServiceImpl notify;
	
	@Autowired
	public CarServiceImpl(CarRepository cr, CarRentRepository crr) {
		this.cr = cr;
		this.crr = crr;
	}
	
	// 찬석
	@Autowired
	public void NotificationServiceImpl(NotificationServiceImpl notify) {
	    this.notify = notify; // NotificationServiceImpl 주입
	}
	
	@Transactional
	public void modifyCarStatus() {
		LocalDate today = LocalDate.now();
		
//		String todaystring = "2023-12-01";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		LocalDate today = LocalDate.parse(todaystring, formatter);
//		System.out.println("*************service: "+today);
		
		cr.saveEndCarStatus(today);
	}
	
	//*************** 메인 신청내역 수 ***********************
	

	@Override
	public Map countMainCarRentCount() {
		Map map = new HashMap<>();
		map.put("waitingCnt", crr.countByStatus((long)0));
		map.put("rentCnt", crr.countRentList());
		map.put("noReturnCnt", crr.countNoReturnList());
		
		return map;
	}
	
	@Override
	public Map countMainMyCarRentCount(String memberId) {
		Map map = new HashMap<>();
		System.out.println("************************* 내 대기중 개수 *****************************************");
		map.put("myWaitingCnt", crr.countByMemberIdAndStatus(memberId,(long)0));
		System.out.println("************************* 내 대여중 개수 *****************************************");
		map.put("myRentCnt", crr.countMyRentList(memberId));
		System.out.println("************************* 내 미반납 개수 *****************************************");
		map.put("myNoReturnCnt", crr.countMyNoReturnList(memberId));
		
		return map;
	}
	
	//*************** 차량 목록 **************************
	
	@Override
	public Page<CarDTO> findAllCarList(Pageable pageable, String startDate, String endDate) {
		Page<CarEntity> entityList = cr.findAllCarList(pageable, startDate, endDate);
//		Page<CarEntity> entityList = cr.findAllCarList(pageable);

		CarMapper cm = new CarMapper();
		return entityList.map(cm::entityToDto);
	}
	
//	@Override
//	public Page<CarDTO> findAllCarByDateSelect(Pageable pageable){
//		Page<CarEntity> entityList = cr.findAllCarList(pageable);
//		CarMapper cm = new CarMapper();
//		return entityList.map(cm::entityToDto);
//	}
	
	@Override
	public void createCarRent(CarRentDTO carRent) {
		CarRentMapper crm = new CarRentMapper();
		CarRentEntity entity = crm.dtoToEntity(carRent);
		System.out.println("entity carId   "+entity.getCar().getId());
		crr.save(entity);
		
		// 찬석
		MemberEntity memberEntity = entity.getMember();
		log.warn("차량예약 id : {}", memberEntity.getId());
		log.warn("여기까진 오나..?");
		
	    // 찬석
	    notify.send(memberEntity, NotificationEntity.NotificationType.CAR, "차량이 예약 되었습니다.");

	}
	
	//****************** 차량 대여 목록 **************************
	
	
	@Override
	public Page<CarRentDTO> findAllMyCarRent(Pageable pageable, String memberId) {
		Page<CarRentEntity> entityList = crr.findAllByMemberIdOrderByReqDateDesc(pageable, memberId);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	@Override
	public void removeByIdCarRent(Long id) {
		crr.deleteById(id);
	}
	
	
	//***************** 차량 관리 메인 ************************
	
	@Override
	public Map findAllCarManage(){
		Map map = new HashMap<>();
		Map locationMap = saveLocationInfo();
		
		System.out.println("findAllCarManage: "+locationMap.get("latitude")+", "+locationMap.get("longitude"));
		
		List<CarEntity> carEntityList = cr.findAll();
		List<CarDTO> carDtoList = new ArrayList<>();
		for(CarEntity entity : carEntityList) {			
			CarMapper cm = new CarMapper();
			CarDTO dto = cm.entityToDto(entity);
			carDtoList.add(dto);
		}
		
		List<CarRentEntity> waitingEntityList = crr.findAllByStatusOrderByReqDate((long)0);
		List<CarRentDTO> waitingDtoList = new ArrayList<>();
		for(CarRentEntity entity : waitingEntityList) {			
			CarRentMapper crm = new CarRentMapper();
			CarRentDTO dto = crm.entityToDto(entity);
			waitingDtoList.add(dto);
		}
		
		List<CarRentEntity> rentEntityList = crr.findAllRentList();
		List<CarRentDTO> rentDtoList = new ArrayList<>();
		for(CarRentEntity entity : rentEntityList) {			
			CarRentMapper crm = new CarRentMapper();
			CarRentDTO dto = crm.entityToDto(entity);
			rentDtoList.add(dto);
		}
		
		List<CarRentEntity> noReturnEntityList = crr.findAllNoReturnList();
		List<CarRentDTO> noReturnDtoList = new ArrayList<>();
		for(CarRentEntity entity : noReturnEntityList) {			
			CarRentMapper crm = new CarRentMapper();
			CarRentDTO dto = crm.entityToDto(entity);
			noReturnDtoList.add(dto);
		}
		
		for(int i=0;i<carDtoList.size();i++) {
			if(carDtoList.get(i).getId().equals("740293")) {
				System.out.println("찾았따");
				carDtoList.get(i).setLatitude((BigDecimal)locationMap.get("latitude"));
				carDtoList.get(i).setLongitude((BigDecimal)locationMap.get("longitude"));
				System.out.println("carDtoList: "+carDtoList.get(i).getLatitude()+", "+carDtoList.get(i).getLongitude());
			}
		}
		
		map.put("carlist", carDtoList);
		map.put("waitinglist", waitingDtoList);
		map.put("rentlist", rentDtoList);
		map.put("noreturnlist", noReturnDtoList);
		
		return map;
	}
	
	public CarDTO findByIdLive(){
		Map locationMap = saveLocationInfo();
		
		Optional<CarEntity> entity = cr.findById("740293");
		CarMapper cm = new CarMapper();
		CarDTO dto = cm.entityToDtoOptional(entity);
		
		dto.setLatitude((BigDecimal)locationMap.get("latitude"));
		dto.setLongitude((BigDecimal)locationMap.get("longitude"));
		
		return dto;
	}
	
	public Page<CarDTO> findAllCarManageList(Pageable pageable){
		Page<CarEntity> entityList = cr.findAll(pageable);
		CarMapper cm = new CarMapper();
		return entityList.map(cm::entityToDto);
	}
	
	//***************** 차량 	 승인 **************************
	
	@Override
	public Page<CarRentDTO> findAllWaiting(Pageable pageable){
		Page<CarRentEntity> entityList = crr.findAllByStatusOrderByReqDate(pageable, (long)0);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	@Override
	public void modifyCarRentStatus(Long id, Long status) {
		Optional<CarRentEntity> optC = crr.findById(id);
		CarRentEntity carRentEntity = optC.get();
		carRentEntity.modifyCarRentStatus(status);
		crr.save(carRentEntity);
		
		// 반납, 승인
		MemberEntity memberEntity = carRentEntity.getMember();
		log.warn("차량반려 id : {}", memberEntity.getId());
		
		Long approveStatus = carRentEntity.getStatus();
		
		if(approveStatus == 2) {
			notify.send(memberEntity, NotificationEntity.NotificationType.CAR, "차량요청이 승인되었습니다");
		}
		
	}
	
	@Override
	public void saveCarRentReject(CarRentDTO carRent, Long status) {
		Optional<CarRentEntity> optC = crr.findById(carRent.getId());
		CarRentEntity carRentEntity = optC.get();
		carRentEntity.modifyCarRentStatus(status);
		carRentEntity.modifyCarRentReject(carRent.getReject());
		crr.save(carRentEntity);
		
		// 반려 알림
		// 찬석
		MemberEntity memberEntity = carRentEntity.getMember();
		log.warn("차량반려 id : {}", memberEntity.getId());
		
	    notify.send(memberEntity, NotificationEntity.NotificationType.CAR, "차량이 반려되었습니다.");
		
	}
	
	//***************** 차량 관리 대여 목록 *********************
	
	@Override
	public Page<CarRentDTO> findAllRentList(Pageable pageable) {
		Page<CarRentEntity> entityList = crr.findAllRentList(pageable);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	//***************** 차량 관리 대여 미반납 *********************
	
	@Override
	public Page<CarRentDTO> findAllNoReturnList(Pageable pageable){
		Page<CarRentEntity> entityList = crr.findAllNoReturnList(pageable);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	//***************** 차량 관리 모든 예약 내역 *********************
	
	@Override
	public Page<CarRentDTO> findAllRentListAll(Pageable pageable){
		Page<CarRentEntity> entityList = crr.findAllByOrderByReqDateDesc(pageable);
		CarRentMapper crm = new CarRentMapper();
		return entityList.map(crm::entityToDto);
	}
	
	@Override
	public Map saveLocationInfo() {
		Map map = new HashMap<>();
		String id = "635591";
		String apiUrl= "https://demo.traccar.org/api/positions";
		
		
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("98dnjsgml@gmail.com", "kosa"); // Basic Auth

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("deviceId", id);

        ResponseEntity<String> response = new RestTemplate().exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                String.class,
                queryParams
        );
        
        

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("Response Body: " + response.getBody());

            // Parse and process the response JSON as needed
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCode());

        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			for (JsonNode element : jsonNode) {
	            BigDecimal latitude = element.get("latitude").decimalValue();
	            BigDecimal longitude = element.get("longitude").decimalValue();
	            
	            map.put("latitude", latitude);
	            map.put("longitude", longitude);
	            

	            // 필요한 필드만 선택적으로 가져와서 사용
	            System.out.println("Latitude: " + map.get("latitude") + ", Longitude: " + map.get("longitude"));
	        }
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
}
